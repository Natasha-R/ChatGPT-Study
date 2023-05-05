package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate {

    private final Integer x;
    private final Integer y;


    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if (x < 0 || y < 0) throw new RuntimeException("No negative Numbers allowed");
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) throws RuntimeException {
        coordinateString = coordinateString.replace("(", "");
        coordinateString = coordinateString.replace(")", "");
        String[] resultString = coordinateString.split(",");
        if (resultString.length > 2) throw new RuntimeException("CoordinateString faulty");
        this.x = Integer.parseInt(resultString[0]);
        this.y = Integer.parseInt(resultString[1]);

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
