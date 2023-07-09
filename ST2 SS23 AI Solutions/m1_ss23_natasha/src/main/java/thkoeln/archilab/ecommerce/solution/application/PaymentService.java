package thkoeln.archilab.ecommerce.solution.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.archilab.ecommerce.solution.domain.Customer;
import thkoeln.archilab.ecommerce.solution.shoppingbasket.domain.ShoppingBasket;
import thkoeln.archilab.ecommerce.solution.domain.CustomerRepository;
import thkoeln.archilab.ecommerce.solution.domain.ShoppingBasketRepository;
import thkoeln.archilab.ecommerce.usecases.PaymentUseCases;
import thkoeln.archilab.ecommerce.usecases.ShopException;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService implements PaymentUseCases {

    private CustomerRepository customerRepository;
    private ShoppingBasketRepository shoppingBasketRepository;
    private static final float MAX_PAYMENT = 500.00f;

    @Autowired
    public PaymentService(CustomerRepository customerRepository, ShoppingBasketRepository shoppingBasketRepository) {
        this.customerRepository = customerRepository;
        this.shoppingBasketRepository = shoppingBasketRepository;
    }

    @Override
    public UUID authorizePayment(String customerMailAddress, Float amount) {
        if (customerMailAddress == null || customerMailAddress.isEmpty()) {
            throw new ShopException("Customer mail address cannot be null or empty");
        }
        if (amount == null) {
            throw new ShopException("Amount cannot be null");
        }
        if (amount <= 0) {
            throw new ShopException("Amount cannot be less than or equal to zero");
        }
        if (amount > MAX_PAYMENT) {
            throw new ShopException("Amount cannot be more than " + MAX_PAYMENT);
        }

        // Retrieve customer
        Optional<Customer> customerOptional = customerRepository.findByMailAddress(customerMailAddress);
        if (!customerOptional.isPresent()) {
            throw new ShopException("Customer with mail address " + customerMailAddress + " does not exist");
        }
        Customer customer = customerOptional.get();

        // Retrieve customer's shopping basket
        Optional<ShoppingBasket> shoppingBasketOptional = shoppingBasketRepository.findByCustomer(customer);
        if (!shoppingBasketOptional.isPresent()) {
            throw new ShopException("No shopping basket found for customer with mail address " + customerMailAddress);
        }
        ShoppingBasket shoppingBasket = shoppingBasketOptional.get();

        shoppingBasket.authorizePayment(UUID.randomUUID());
        // Save the changes made to the shopping basket
        shoppingBasketRepository.save(shoppingBasket);
        
        customer.updatePaymentTotal(amount);
        customerRepository.save(customer);
        return shoppingBasket.getId();
    }

    @Override
    public Float getPaymentTotal(String customerMailAddress) {
        if (customerMailAddress == null || customerMailAddress.isEmpty()) {
            throw new ShopException("Customer mail address cannot be null or empty");
        }
        Optional<Customer> customerOptional = customerRepository.findByMailAddress(customerMailAddress);
        if (!customerOptional.isPresent()) {
            throw new ShopException("Customer with mail address " + customerMailAddress + " does not exist");
        }

        // Retrieve customer's shopping basket
        Optional<ShoppingBasket> shoppingBasketOptional = shoppingBasketRepository.findByCustomer(customerOptional.get());
        if (!shoppingBasketOptional.isPresent()) {
            throw new ShopException("No shopping basket found for customer with mail address " + customerMailAddress);
        }
        ShoppingBasket shoppingBasket = shoppingBasketOptional.get();

        // Return the total amount of the shopping basket
        return customerOptional.get().getPaymentTotal();

    }

    public Float getShoppingBasketTotal(String customerMailAddress) {
        if (customerMailAddress == null || customerMailAddress.isEmpty()) {
            throw new ShopException("Customer mail address cannot be null or empty");
        }
        Optional<Customer> customerOptional = customerRepository.findByMailAddress(customerMailAddress);
        if (!customerOptional.isPresent()) {
            throw new ShopException("Customer with mail address " + customerMailAddress + " does not exist");
        }

        // Retrieve customer's shopping basket
        Optional<ShoppingBasket> shoppingBasketOptional = shoppingBasketRepository.findByCustomer(customerOptional.get());
        if (!shoppingBasketOptional.isPresent()) {
            throw new ShopException("No shopping basket found for customer with mail address " + customerMailAddress);
        }
        ShoppingBasket shoppingBasket = shoppingBasketOptional.get();

        // Return the total amount of the shoppilng basket
        return shoppingBasket.calculateTotal();

    }

    @Override
    public void deletePaymentHistory() {
        // No more payment history to delete as we are not storing it anymore.
    }
}

