package thkoeln.archilab.ecommerce.solution.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.archilab.ecommerce.solution.domain.CustomerRepository;
import thkoeln.archilab.ecommerce.solution.domain.Customer;
import thkoeln.archilab.ecommerce.solution.domain.*;
import thkoeln.archilab.ecommerce.solution.domain.OrderRepository;
import thkoeln.archilab.ecommerce.solution.order.domain.Order;
import thkoeln.archilab.ecommerce.solution.domain.ShoppingBasketRepository;
import thkoeln.archilab.ecommerce.solution.shoppingbasket.domain.ShoppingBasket;
import thkoeln.archilab.ecommerce.solution.domain.ThingRepository;
import thkoeln.archilab.ecommerce.solution.thing.domain.Thing;
import thkoeln.archilab.ecommerce.solution.thing.domain.Catalog;
import thkoeln.archilab.ecommerce.usecases.ShopException;
import thkoeln.archilab.ecommerce.usecases.ShoppingBasketUseCases;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class ShoppingBasketService implements ShoppingBasketUseCases {

    private final CustomerRepository customerRepository;
    private final ThingRepository thingRepository;
    private final OrderRepository orderRepository;
    private final CatalogRepository catalogRepository;
    private final ShoppingBasketRepository shoppingBasketRepository;
    private final DeliveryRepository deliveryRepository;
    private PaymentService paymentService;
    private DeliveryService deliveryService;

    @Autowired
    public ShoppingBasketService(CustomerRepository customerRepository, ThingRepository thingRepository, OrderRepository orderRepository, CatalogRepository catalogRepository, ShoppingBasketRepository shoppingBasketRepository, DeliveryRepository deliveryRepository, PaymentService paymentService, DeliveryService deliveryService) {
        this.customerRepository = customerRepository;
        this.thingRepository = thingRepository;
        this.orderRepository = orderRepository;
        this.catalogRepository = catalogRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
        this.deliveryRepository = deliveryRepository;
        this.paymentService = paymentService;
        this.deliveryService = deliveryService;
    }

    @Override
    public void addThingToShoppingBasket(String customerMailAddress, UUID thingId, int quantity) throws ShopException {
        Optional<Customer> customerOpt = customerRepository.findByMailAddress(customerMailAddress);
        if (!customerOpt.isPresent()) {
            throw new ShopException("The customer with the given mailAddress does not exist");
        }
        Customer customer = customerOpt.get();

        Optional<Thing> thingOpt = thingRepository.findById(thingId);
        if (!thingOpt.isPresent()) {
            throw new ShopException("The thing does not exist");
        }
        Thing thing = thingOpt.get();

        if (quantity < 0) {
            throw new ShopException("The quantity cannot be negative");
        }

        Optional<Catalog> catalogOpt = catalogRepository.findById(thingId);
        if (!catalogOpt.isPresent() || catalogOpt.get().getStockQuantity() < quantity) {
            throw new ShopException("The thing is not available in the requested quantity");
        }

        customer.getShoppingBasket().addThing(thing, quantity);
        customerRepository.save(customer);
        Catalog catalog = catalogOpt.get();
        catalog.removeFromStock(quantity);
        catalogRepository.save(catalog);
    }



    @Override
    public void removeThingFromShoppingBasket(String customerMailAddress, UUID thingId, int quantity) throws ShopException {
        Optional<Customer> customerOpt = customerRepository.findByMailAddress(customerMailAddress);
        if (!customerOpt.isPresent()) {
            throw new ShopException("The customer with the given mailAddress does not exist");
        }
        Customer customer = customerOpt.get();

        Optional<Thing> thingOpt = thingRepository.findById(thingId);
        if (!thingOpt.isPresent()) {
            throw new ShopException("The thing does not exist");
        }
        Thing thing = thingOpt.get();

        if (quantity < 0) {
            throw new ShopException("The quantity cannot be negative");
        }

        if (customer.getShoppingBasket().getQuantityOfThing(thing) < quantity) {
            throw new ShopException("The thing is not in the cart in the requested quantity");
        }

        customer.getShoppingBasket().removeThing(thing, quantity);
        customerRepository.save(customer);
    }


    @Override
    public Map<UUID, Integer> getShoppingBasketAsMap(String customerMailAddress) {
        Customer customer = findCustomerByMail(customerMailAddress);
        return customer.getShoppingBasket().getThings();
    }

    @Override
    public float getShoppingBasketAsMoneyValue(String customerMailAddress) {
        Customer customer = findCustomerByMail(customerMailAddress);
        return customer.getShoppingBasket().calculateTotal();
    }

    @Override
    public int getReservedStockInShoppingBaskets(UUID thingId) {
        Thing thing = findThingById(thingId);
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .mapToInt(customer -> customer.getShoppingBasket().getQuantityOfThing(thing))
                .sum();
    }

    @Override
    public void checkout(String customerMailAddress) {


        // Retrieve the customer by the provided mail address
        Optional<Customer> customerOptional = customerRepository.findByMailAddress(customerMailAddress);
        if (!customerOptional.isPresent()) {
            throw new ShopException("Customer with mail address " + customerMailAddress + " does not exist");
        }
        Customer customer = customerOptional.get();

        // Retrieve the shopping basket for the customer
        Optional<ShoppingBasket> shoppingBasketOptional = shoppingBasketRepository.findByCustomer(customer);
        if (!shoppingBasketOptional.isPresent() || shoppingBasketOptional.get().getBasketPositions().isEmpty()) {
            throw new ShopException("Shopping basket is empty for the customer with mail address " + customerMailAddress);
        }

        // Calculate the total payment for the shopping basket
        // Authorize the payment for the shopping basket and change its state
        // If paymentAmount is enough, authorize the payment and transition shopping basket state to PAYMENT_AUTHORIZED
        Float paymentAmount = paymentService.getShoppingBasketTotal(customer.getMailAddress());
        paymentService.authorizePayment(customer.getMailAddress(), paymentAmount);
        ShoppingBasket shoppingBasket = shoppingBasketOptional.get();

        // Check if payment has been authorized
        if (!isPaymentAuthorized(customerMailAddress)) {
            throw new ShopException("Payment not authorized for the shopping basket");
        }

        try {
            // Trigger the delivery
            UUID deliveryId = deliveryService.triggerDelivery(customer, shoppingBasket.getThings());

            // If delivery triggering is successful, transition shopping basket state to DELIVERY_TRIGGERED
            shoppingBasket.triggerDelivery(deliveryId);
            shoppingBasketRepository.save(shoppingBasket);
            // Only create the order and clear the shopping basket if the delivery triggering is successful
            Order order = customer.checkout(thingRepository);
            orderRepository.save(order);

            // Clear the shopping basket and transition its state back to EMPTY
            shoppingBasket.clearBasket();

        } catch (ShopException e) {
            // Check if the exception was due to the delivery quantity limit
            if (e.getMessage().contains("Total number of things in the delivery cannot be more than")) {
                // If delivery triggering fails due to quantity limits, allow the shopping basket to be modified
                shoppingBasket.setState(ShoppingBasket.ShoppingBasketState.FILLED);
            }

            // If the exception was due to another reason, rethrow it
            throw e;
        } finally {
            // Save the shopping basket whether or not an exception was thrown
            shoppingBasketRepository.save(shoppingBasket);
        }

        // Save the customer
        customerRepository.save(customer);
    }

    @Override
    public Map<UUID, Integer> getOrderHistory(String customerMailAddress) {
        Customer customer = findCustomerByMail(customerMailAddress);
        return customer.getPastOrders();
    }

    public boolean isEmpty(String customerMailAddress) {
        if (customerMailAddress == null || customerMailAddress.isEmpty()) {
            throw new ShopException("Customer mail address cannot be null or empty");
        }

        Optional<Customer> customerOptional = customerRepository.findByMailAddress(customerMailAddress);
        if (!customerOptional.isPresent()) {
            throw new ShopException("Customer with mail address " + customerMailAddress + " does not exist");
        }

        ShoppingBasket shoppingBasket = customerOptional.get().getShoppingBasket();
        return shoppingBasket == null || shoppingBasket.getThings().isEmpty();
    }

    @Override
    public boolean isPaymentAuthorized(String customerMailAddress) {
        if (customerMailAddress == null || customerMailAddress.isEmpty()) {
            throw new ShopException("Customer mail address cannot be null or empty");
        }
        Optional<Customer> customer = customerRepository.findByMailAddress(customerMailAddress);
        if (!customer.isPresent()) {
            throw new ShopException("Customer with mail address " + customerMailAddress + " does not exist");
        }

        Optional<ShoppingBasket> shoppingBasketOptional = shoppingBasketRepository.findByCustomer(customer.get());
        if (!shoppingBasketOptional.isPresent()) {
            throw new ShopException("No shopping basket exists for customer: " + customerMailAddress);
        }

        ShoppingBasket shoppingBasket = shoppingBasketOptional.get();
        return shoppingBasket.getState().equals(ShoppingBasket.ShoppingBasketState.PAYMENT_AUTHORIZED);
    }


    @Override
    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    private Customer findCustomerByMail(String mailAddress) {
        return customerRepository.findByMailAddress(mailAddress)
                .orElseThrow(() -> new ShopException("The customer with the given mailAddress does not exist"));
    }

    private Thing findThingById(UUID id) {
        return thingRepository.findById(id)
                .orElseThrow(() -> new ShopException("The thing id does not exist"));
    }
}
