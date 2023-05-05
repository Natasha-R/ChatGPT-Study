package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {

        if( x < 0 || y < 0 ){
            throw new RuntimeException("Coordinates can not be negative");
        }

        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if( !coordinateString.matches("^\\(\\d,\\d\\)$")) {
            throw new RuntimeException("Invalid Coordinate String");
        }
        try {
            coordinateString = coordinateString.replace("(", "");
            coordinateString = coordinateString.replace(")", "");

            String[] explodedPosition = coordinateString.split(",");

            x = Integer.parseInt(explodedPosition[0]);
            y = Integer.parseInt(explodedPosition[1]);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to parse Coordinate string");
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
