package thkoeln.archilab.ecommerce.solution.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.archilab.ecommerce.solution.domain.Customer;
import thkoeln.archilab.ecommerce.solution.shoppingbasket.domain.Delivery;
import thkoeln.archilab.ecommerce.solution.domain.CustomerRepository;
import thkoeln.archilab.ecommerce.solution.domain.DeliveryRepository;
import thkoeln.archilab.ecommerce.usecases.DeliveryRecipient;
import thkoeln.archilab.ecommerce.usecases.DeliveryUseCases;
import thkoeln.archilab.ecommerce.usecases.ShopException;

import java.util.*;

@Service
public class DeliveryService implements DeliveryUseCases {
    private DeliveryRepository deliveryRepository;
    private CustomerRepository customerRepository;
    private static final int MAX_DELIVERY_QUANTITY = 20;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, CustomerRepository customerRepository) {
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UUID triggerDelivery(DeliveryRecipient deliveryRecipient, Map<UUID, Integer> deliveryContent) {
        // Check the validity of the parameters
        if (deliveryRecipient == null
                || deliveryRecipient.getName() == null || deliveryRecipient.getName().isEmpty()
                || deliveryRecipient.getStreet() == null || deliveryRecipient.getStreet().isEmpty()
                || deliveryRecipient.getCity() == null || deliveryRecipient.getCity().isEmpty()
                || deliveryRecipient.getPostalCode() == null || deliveryRecipient.getPostalCode().isEmpty()
                || deliveryRecipient.getMailAddress() == null || deliveryRecipient.getMailAddress().isEmpty()
                || deliveryContent == null || deliveryContent.isEmpty()) {
            throw new ShopException("Invalid delivery details");
        }

        int totalQuantity = deliveryContent.values().stream().reduce(0, Integer::sum);
        if (totalQuantity > MAX_DELIVERY_QUANTITY) {
            throw new ShopException("Total number of things in the delivery cannot be more than " + MAX_DELIVERY_QUANTITY);
        }

        // Find the customer by name, street, city, and postal code
        Optional<Customer> customerOptional = customerRepository.findByNameAndStreetAndCityAndPostalCode(
                deliveryRecipient.getName(),
                deliveryRecipient.getStreet(),
                deliveryRecipient.getCity(),
                deliveryRecipient.getPostalCode()
        );

        if (!customerOptional.isPresent()) {
            throw new ShopException("Customer does not exist");
        }

        Delivery delivery = new Delivery(customerOptional.get(), deliveryContent);
        deliveryRepository.save(delivery);

        return delivery.getId();
    }

    @Override
    public Map<UUID, Integer> getDeliveryHistory(String customerMailAddress) {
        if (customerMailAddress == null || customerMailAddress.isEmpty()) {
            throw new ShopException("Invalid customer mail address");
        }

        Optional<Customer> customerOptional = customerRepository.findByMailAddress(customerMailAddress);
        if (!customerOptional.isPresent()) {
            throw new ShopException("Customer does not exist");
        }

        List<Delivery> deliveries = deliveryRepository.findAllByCustomerId(customerOptional.get().getId());

        Map<UUID, Integer> deliveryHistory = new HashMap<>();
        for (Delivery delivery : deliveries) {
            for (Map.Entry<UUID, Integer> entry : delivery.getDeliveryContent().entrySet()) {
                deliveryHistory.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }

        return deliveryHistory;
    }

    @Override
    public void deleteDeliveryHistory() {
        deliveryRepository.deleteAll();
    }
}
