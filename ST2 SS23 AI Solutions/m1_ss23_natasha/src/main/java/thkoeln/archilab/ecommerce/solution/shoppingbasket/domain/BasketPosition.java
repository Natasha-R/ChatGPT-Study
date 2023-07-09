package thkoeln.archilab.ecommerce.solution.shoppingbasket.domain;

import thkoeln.archilab.ecommerce.solution.thing.domain.Thing;
import thkoeln.archilab.ecommerce.usecases.ShopException;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class BasketPosition {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Thing thing;

    private int quantity;

    protected BasketPosition() {}

    public BasketPosition(Thing thing, int quantity) {
        if (thing == null) {
            throw new ShopException("Thing cannot be null");
        }
        if (quantity < 0) {
            throw new ShopException("Quantity must be greater than 0");
        }
        this.thing = thing;
        this.quantity = quantity;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public Thing getThing() {
        return thing;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
