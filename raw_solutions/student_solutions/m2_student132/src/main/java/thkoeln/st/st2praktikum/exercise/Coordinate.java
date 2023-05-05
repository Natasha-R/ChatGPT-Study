package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if (x < 0) throw new IllegalArgumentException("No negative Coordinates allowed.");
        if (y < 0) throw new IllegalArgumentException("No negative Coordinates allowed.");

        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if (coordinateString.indexOf("(") == 0 && coordinateString.indexOf(",") == 2 && coordinateString.indexOf(")") == 4) {
            String[] parts = coordinateString.split("\\(");
            parts = parts[1].split("\\)");
            parts = parts[0].split(",");

            if (Integer.parseInt(parts[0]) < 0) throw new IllegalArgumentException("No negative Coordinates allowed.");
            if (Integer.parseInt(parts[1]) < 0) throw new IllegalArgumentException("No negative Coordinates allowed.");

            this.x = Integer.parseInt(parts[0]);
            this.y = Integer.parseInt(parts[1]);
        } else {
            throw new IllegalArgumentException("String " + coordinateString + " isn't formatted correctly.");
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

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
