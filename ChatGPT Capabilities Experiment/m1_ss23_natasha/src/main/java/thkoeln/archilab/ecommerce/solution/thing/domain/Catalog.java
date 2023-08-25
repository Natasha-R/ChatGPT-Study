package thkoeln.archilab.ecommerce.solution.thing.domain;

import thkoeln.archilab.ecommerce.solution.thing.domain.Thing;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class Catalog {

    @Id
    private UUID id; // this id should be the same as the Thing id

    @OneToOne
    private Thing thing;

    private int stockQuantity;

    protected Catalog() {}

    public Catalog(Thing thing, int stockQuantity) {
        if (thing == null) {
            throw new IllegalArgumentException("Thing cannot be null");
        }

        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        this.id = thing.getId();
        this.thing = thing;
        this.stockQuantity = stockQuantity;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public Thing getThing() {
        return thing;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    // Additional methods
    public void addToStock(int addedQuantity) {
        if (addedQuantity < 0) {
            throw new IllegalArgumentException("Added quantity cannot be negative");
        }
        this.stockQuantity += addedQuantity;
    }

    public void removeFromStock(int removedQuantity) {
        if (removedQuantity < 0 || removedQuantity > stockQuantity) {
            throw new IllegalArgumentException("Invalid removed quantity");
        }
        this.stockQuantity -= removedQuantity;
    }

    public void changeStockTo(int newTotalQuantity) {
        if (newTotalQuantity < 0) {
            throw new IllegalArgumentException("New total quantity cannot be negative");
        }
        this.stockQuantity = newTotalQuantity;
    }
}
