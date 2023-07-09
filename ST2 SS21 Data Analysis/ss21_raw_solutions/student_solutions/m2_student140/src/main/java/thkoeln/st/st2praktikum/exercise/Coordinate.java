package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if(x < 0 || y < 0)
            throw new RuntimeException("no negative numbers");

        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        String[] coordinatesSplit = coordinateString.split(",");

        Integer locationX = Integer.parseInt(coordinatesSplit[0].substring(1));

        Integer locationY = Integer.parseInt(coordinatesSplit[1].substring(0,coordinatesSplit[1].length() - 1));

        if(locationX < 0 || locationY < 0)
            throw new RuntimeException("no negative numbers");

        this.x = locationX;
        this.y = locationY;
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
        return "(" + x + "," + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Integer x) { this.x = x; }

    public void setY(Integer y) { this.y = y; }
}
