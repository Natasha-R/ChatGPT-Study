package thkoeln.st.st2praktikum.exercise;

import java.util.StringTokenizer;

public class Coordinate {
    private final Integer x;
    private final Integer y;

    @Override
    public String toString() {
        return ("(" + this.x + "," + this.y + ")");
    }

    public Boolean equals (Coordinate otherCoordinate) {
        return this.getX().equals(otherCoordinate.getX())
                && this.getY().equals(otherCoordinate.getY());
    }

    //TODO: Switch to proper compareTo-method.
    public Boolean isGreaterThan(Coordinate otherCoordinate) {
        return this.getX().equals(otherCoordinate.getX()) && this.getY() > otherCoordinate.getY()
                || this.getY().equals(otherCoordinate.getY()) && this.getX() > otherCoordinate.getX();
    }

    public Integer getX() { return x; }
    public Integer getY() { return y; }

    public Coordinate(Integer _x, Integer _y) {
        this.x = _x;
        this.y = _y;
    }
    public Coordinate(String coordinateString) {
        StringTokenizer st = new StringTokenizer(coordinateString, "(),");

        this.x = Integer.parseInt(st.nextToken());
        this.y = Integer.parseInt(st.nextToken());
    }
}
