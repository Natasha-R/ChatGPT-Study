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
    private Coordinates coordinates;

    public Droid(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public Boolean isAtZeroZero() {
        return this.coordinates.getX() == 0 && this.coordinates.getY() == 0;
    }

    public void moveOne(String direction) {
        switch (direction) {
            case "ea":
                this.coordinates = new Coordinates(this.coordinates.getX() + 1, this.coordinates.getY());
                break;
            case "we":
                this.coordinates = new Coordinates(this.coordinates.getX() - 1, this.coordinates.getY());
                break;
            case "no":
                this.coordinates = new Coordinates(this.coordinates.getX(), this.coordinates.getY() + 1);
                break;
            case "so":
                this.coordinates = new Coordinates(this.coordinates.getX(), this.coordinates.getY() - 1);
                break;
        }
    }
}
