package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Coordinate {

    private Integer x;
    private Integer y;

    public Coordinate(Integer x, Integer y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be positive");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if (!coordinateString.matches("\\(\\d+,\\d+\\)")) {
            throw new InvalidCoordinateException("Invalid coordinate string: " + coordinateString);
        }
        String[] parts = coordinateString.replaceAll("[() ]", "").split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be positive");
        }
        this.x = x;
        this.y = y;
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

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public static class InvalidCoordinateException extends RuntimeException {
        public InvalidCoordinateException(String message) {
            super(message);
        }
    }
}