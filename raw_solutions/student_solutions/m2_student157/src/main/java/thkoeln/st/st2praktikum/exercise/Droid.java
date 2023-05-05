package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class Droid {

    private final String name;
    private final UUID uuid;
    @Getter
    @Setter
    private Vector2D coordinates;

    public Droid(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
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
}
