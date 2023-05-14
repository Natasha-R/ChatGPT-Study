package thkoeln.archilab.ecommerce.solution.domain;

import thkoeln.archilab.ecommerce.solution.repositories.ThingRepository;
import thkoeln.archilab.ecommerce.usecases.ShopException;

import javax.persistence.*;
import java.util.*;
@Entity
public class CustomerOrder {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderPosition> orderPositions = new ArrayList<>();

    protected CustomerOrder() {}

    public CustomerOrder(Customer customer, Map<UUID, Integer> things, ThingRepository thingRepository) {
        if (customer == null) {
            throw new ShopException("Customer cannot be null");
        }
        if (things == null || things.isEmpty()) {
            throw new ShopException("Order cannot be empty");
        }
        this.customer = customer;
        for (Map.Entry<UUID, Integer> entry : things.entrySet()) {
            Thing thing = thingRepository.findById(entry.getKey())
                    .orElseThrow(() -> new ShopException("The thing id does not exist"));
            this.orderPositions.add(new OrderPosition(thing, entry.getValue()));
        }
    }

    public Map<UUID, Integer> getThings() {
        Map<UUID, Integer> things = new HashMap<>();
        for (OrderPosition orderPosition : orderPositions) {
            things.put(orderPosition.getThing().getId(), orderPosition.getQuantity());
        }
        return things;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<OrderPosition> getOrderPositions() {
        return orderPositions;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setOrderPositions(List<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
    }

    // Additional methods
    public void addOrderPosition(OrderPosition orderPosition) {
        if (orderPosition == null) {
            throw new ShopException("OrderPosition cannot be null");
        }
        this.orderPositions.add(orderPosition);
    }
}
