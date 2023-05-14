package thkoeln.archilab.ecommerce.solution.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.archilab.ecommerce.solution.domain.*;
import thkoeln.archilab.ecommerce.solution.repositories.*;
import thkoeln.archilab.ecommerce.usecases.ShopException;
import thkoeln.archilab.ecommerce.usecases.ShoppingBasketUseCases;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class ShoppingBasketService implements ShoppingBasketUseCases {

    private final CustomerRepository customerRepository;
    private final ThingRepository thingRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final CatalogRepository catalogRepository;

    @Autowired
    public ShoppingBasketService(CustomerRepository customerRepository, ThingRepository thingRepository, CustomerOrderRepository customerOrderRepository, CatalogRepository catalogRepository) {
        this.customerRepository = customerRepository;
        this.thingRepository = thingRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.catalogRepository = catalogRepository;
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
        Customer customer = findCustomerByMail(customerMailAddress);
        CustomerOrder customerOrder = customer.checkout(thingRepository);
        customerOrderRepository.save(customerOrder);
        customerRepository.save(customer);
    }

    @Override
    public Map<UUID, Integer> getOrderHistory(String customerMailAddress) {
        Customer customer = findCustomerByMail(customerMailAddress);
        return customer.getPastOrders();
    }



    @Override
    public void deleteAllOrders() {
        customerOrderRepository.deleteAll();
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
