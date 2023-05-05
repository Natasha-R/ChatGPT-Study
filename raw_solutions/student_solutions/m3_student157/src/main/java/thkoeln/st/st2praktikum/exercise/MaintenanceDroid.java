package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Setter
@Getter
@Entity
public class MaintenanceDroid {

    private final String name;
    @Id
    private final UUID uuid;
    private Vector2D coordinates;
    private UUID spaceShipDeckId;

    public MaintenanceDroid(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public MaintenanceDroid() {
        name = null;
        uuid = UUID.randomUUID();
    }

    public Boolean isAtZeroZero() {
        return this.coordinates.getX() == 0 && this.coordinates.getY() == 0;
    }

    public void moveOne(OrderType direction) {
        switch (direction) {
            case EAST:
                this.coordinates = new Vector2D(this.coordinates.getX() + 1, this.coordinates.getY());
                break;
            case WEST:
                this.coordinates = new Vector2D(this.coordinates.getX() - 1, this.coordinates.getY());
                break;
            case NORTH:
                this.coordinates = new Vector2D(this.coordinates.getX(), this.coordinates.getY() + 1);
                break;
            case SOUTH:
                this.coordinates = new Vector2D(this.coordinates.getX(), this.coordinates.getY() - 1);
                break;
        }
    }

    public Vector2D getVector2D() {
        return coordinates;
    }
}
