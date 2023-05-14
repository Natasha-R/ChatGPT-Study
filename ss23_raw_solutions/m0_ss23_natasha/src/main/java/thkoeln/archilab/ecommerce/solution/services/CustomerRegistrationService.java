package thkoeln.archilab.ecommerce.solution.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.archilab.ecommerce.solution.domain.Customer;
import thkoeln.archilab.ecommerce.solution.repositories.CustomerRepository;
import thkoeln.archilab.ecommerce.usecases.CustomerRegistrationUseCases;
import thkoeln.archilab.ecommerce.usecases.ShopException;

@Service
public class CustomerRegistrationService implements CustomerRegistrationUseCases {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void register(String name, String mailAddress, String street, String city, String postalCode) {
        if (name == null || name.isEmpty() || mailAddress == null || mailAddress.isEmpty() ||
                street == null || street.isEmpty() || city == null || city.isEmpty() || postalCode == null || postalCode.isEmpty()) {
            throw new ShopException("Customer data are invalid");
        }

        if (customerRepository.findByMailAddress(mailAddress).isPresent()) {
            throw new ShopException("Customer with the given mailAddress already exists");
        }

        Customer customer = new Customer(name, mailAddress, street, city, postalCode);
        customerRepository.save(customer);
    }

    @Override
    public void changeAddress(String customerMailAddress, String street, String city, String postalCode) {
        Customer customer = customerRepository.findByMailAddress(customerMailAddress)
                .orElseThrow(() -> new ShopException("The customer with the given mailAddress does not exist"));

        if (street == null || street.isEmpty() || city == null || city.isEmpty() || postalCode == null || postalCode.isEmpty()) {
            throw new ShopException("Address data are invalid");
        }

        customer.setStreet(street);
        customer.setCity(city);
        customer.setPostalCode(postalCode);
        customerRepository.save(customer);
    }

    @Override
    public String[] getCustomerData(String customerMailAddress) {
        Customer customer = customerRepository.findByMailAddress(customerMailAddress)
                .orElseThrow(() -> new ShopException("The customer with the given mailAddress does not exist"));

        return new String[]{customer.getName(), customer.getMailAddress(), customer.getStreet(), customer.getCity(), customer.getPostalCode()};
    }

    @Override
    public void deleteAllCustomers() {
        customerRepository.deleteAll();
    }
}
