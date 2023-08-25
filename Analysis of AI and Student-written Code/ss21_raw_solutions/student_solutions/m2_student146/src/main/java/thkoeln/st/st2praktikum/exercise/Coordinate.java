package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;
import java.util.regex.Pattern;

public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if(!Pattern.matches("\\d+", x.toString()) || !Pattern.matches("\\d+", y.toString()))
            throw new RuntimeException("The coordinates has to be positive!");
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if(!Pattern.matches("\\(\\d+,\\d+\\)", coordinateString))
            throw new RuntimeException("Wrong format or the coordinates are not positive!");
        String[] coordinateSplit = coordinateString.split(",");
        String tmpX = coordinateSplit[0].replace("(", "");
        String tmpY = coordinateSplit[1].replace(")", "");
        this.x = Integer.parseInt(tmpX);
        this.y = Integer.parseInt(tmpY);
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
