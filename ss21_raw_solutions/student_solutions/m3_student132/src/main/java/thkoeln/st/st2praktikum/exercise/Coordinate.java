package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;


@Getter
@Embeddable
@EqualsAndHashCode
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        this.checkIntegerNotNegativ(x, y);
    }

    /**
     * @param coordinateString the point in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) throws IllegalArgumentException {

        String newCoordinateRaw = coordinateString.substring(1, coordinateString.length() - 1);
        String[] newCoordinate = newCoordinateRaw.split(",");

        if (newCoordinate.length < 3) {
            this.checkIntegerNotNegativ(Integer.parseInt(newCoordinate[0]), Integer.parseInt(newCoordinate[1]));
        } else {
            throw new IllegalArgumentException("Coordinate zu groß");
        }
    }

    protected Coordinate() {
    }

    private void checkIntegerNotNegativ(Integer x, Integer y) throws IllegalArgumentException {
        if (x >= 0) {
            this.x = x;
        } else {
            throw new IllegalArgumentException("X kleiner 0");
        }
        if (y >= 0) {
            this.y = y;
        } else {
            throw new IllegalArgumentException("Y kleiner 0");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
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
}
