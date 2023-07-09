package thkoeln.archilab.ecommerce.solution.shoppingbasket.domain;

import thkoeln.archilab.ecommerce.solution.domain.Customer;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Delivery {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Customer customer;

    @ElementCollection
    private Map<UUID, Integer> deliveryContent = new HashMap<>();

    // For JPA
    protected Delivery() {}

    public Delivery(Customer customer, Map<UUID, Integer> deliveryContent) {
        this.customer = customer;
        this.deliveryContent = deliveryContent;
    }

    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<UUID, Integer> getDeliveryContent() {
        return deliveryContent;
    }
}
