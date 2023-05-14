package thkoeln.archilab.ecommerce.solution.domain;

import javax.persistence.*;
import java.util.*;

@Entity
public class ShoppingBasket {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketPosition> basketPositions = new ArrayList<>();

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

}
