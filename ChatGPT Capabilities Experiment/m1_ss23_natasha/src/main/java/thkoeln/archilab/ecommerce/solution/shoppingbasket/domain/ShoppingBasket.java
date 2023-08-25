package thkoeln.archilab.ecommerce.solution.shoppingbasket.domain;

import thkoeln.archilab.ecommerce.solution.domain.Customer;
import thkoeln.archilab.ecommerce.solution.thing.domain.Thing;

import javax.persistence.*;
import java.util.*;

@Entity
public class ShoppingBasket {
    public enum ShoppingBasketState {
        UNDEFINED, EMPTY, FILLED, PAYMENT_AUTHORIZED, DELIVERY_TRIGGERED
    }

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketPosition> basketPositions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ShoppingBasketState state = ShoppingBasketState.UNDEFINED;

    protected ShoppingBasket() {}

    public ShoppingBasket(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.customer = customer;
    }

    // Getters and setters

    public UUID getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<BasketPosition> getBasketPositions() {
        return basketPositions;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void replaceBasketPositions(List<BasketPosition> newBasketPositions) {
        this.basketPositions.clear();
        if (newBasketPositions != null) {
            this.basketPositions.addAll(newBasketPositions);
        }
    }


    public void addThing(Thing thing, int quantity) {
        if (basketPositions.isEmpty()) {
            this.state = ShoppingBasketState.EMPTY;
        } else {
            this.state = ShoppingBasketState.FILLED;
        }
        if (quantity <= 0) {
            return;
        }

        Optional<BasketPosition> existingPosition = basketPositions.stream()
                .filter(bp -> bp.getThing().equals(thing))
                .findFirst();

        if (existingPosition.isPresent()) {
            BasketPosition bp = existingPosition.get();
            bp.setQuantity(bp.getQuantity() + quantity);
        } else {
            basketPositions.add(new BasketPosition(thing, quantity));
        }
    }

    public void removeThing(Thing thing, int quantity) {

        if (basketPositions.isEmpty()) {
            this.state = ShoppingBasketState.EMPTY;
        }
        Optional<BasketPosition> position = basketPositions.stream()
                .filter(bp -> bp.getThing().equals(thing))
                .findFirst();

        if (position.isPresent()) {
            BasketPosition bp = position.get();
            int newQuantity = bp.getQuantity() - quantity;
            if (newQuantity <= 0) {
                basketPositions.remove(bp);
            } else {
                bp.setQuantity(newQuantity);
            }
        }
    }



    public Map<UUID, Integer> getThings() {
        Map<UUID, Integer> things = new HashMap<>();
        for (BasketPosition bp : basketPositions) {
            things.put(bp.getThing().getId(), bp.getQuantity());
        }
        return things;
    }

    public float calculateTotal() {
        return (float) basketPositions.stream()
                .mapToDouble(bp -> bp.getThing().getSalesPrice() * bp.getQuantity())
                .sum();
    }

    public int getQuantityOfThing(Thing thing) {
        for (BasketPosition basketPosition : basketPositions) {
            if (basketPosition.getThing().getId().equals(thing.getId())) {
                return basketPosition.getQuantity();
            }
        }
        return 0;
    }

    public void authorizePayment(UUID paymentId) {
        if (paymentId != null) {
            this.state = ShoppingBasketState.PAYMENT_AUTHORIZED;
        }
    }


    public void triggerDelivery(UUID deliveryId) {
        if (deliveryId != null) {
            this.state = ShoppingBasketState.DELIVERY_TRIGGERED;
        }
    }
    public void clearBasket() {
        // Clear all the items from the basket
        this.basketPositions.clear();

        // Set the state of the shopping basket back to EMPTY
        this.state = ShoppingBasketState.EMPTY;
    }

    public ShoppingBasketState getState() {
        return this.state;
    }

    public void setState(ShoppingBasketState state) {
        this.state = state;
    }
}
