package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.core.InvalidInputException;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;


@Entity
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Vector2D {

    @Id
    @Getter
    protected UUID id;
    protected Integer x;
    protected Integer y;
    @Setter
    @OneToOne
    protected Room room;


    public Vector2D(Integer x, Integer y) throws InvalidInputException {
        if (!validatePositiveCoordinateInput(x, y)) {
            throw new InvalidInputException("Coordinates are below 0. Negative Coordinates are not permitted.");
        }
        this.id = UUID.randomUUID();
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public Vector2D(String vector2DString) throws InvalidInputException {
        if (!validateCoordinateStringFormatting(vector2DString)) {
            throw new InvalidInputException("String formatting is invalid");
        }
        Pair<Integer, Integer> coordinatePair = parseStringToCoordinates(vector2DString);
        this.id = UUID.randomUUID();
        this.x = coordinatePair.getLeft();
        this.y = coordinatePair.getRight();
    }

    public Vector2D(Integer x, Integer y, Room room) throws InvalidInputException {
        if (!validatePositiveCoordinateInput(x, y)) {
            throw new InvalidInputException("Coordinates are below 0. Negative Coordinates are not permitted.");
        }
        this.id = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D that = (Vector2D) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Room getRoom() {
        return room;
    }

    public UUID getRoomID() {
        return room.getId();
    }

    private Boolean validatePositiveCoordinateInput(Integer x, Integer y) {
        return (x >= 0 && y >= 0);
    }

    private Boolean validateCoordinateStringFormatting(String vector2DString) {
        return vector2DString.matches("\\(\\d+,\\d\\)");
    }

    private Pair<Integer, Integer> parseStringToCoordinates(String vector2DString) {
        vector2DString = vector2DString.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] coordinateSplit = vector2DString.split(",");
        return new Pair(Integer.parseInt(coordinateSplit[0]), Integer.parseInt(coordinateSplit[1]));
    }
}
