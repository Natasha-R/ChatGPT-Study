package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Coordinate {

    private Integer x;
    private Integer y;

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.assertCoordinateNotNegative();
    }

    public Coordinate(Vector vector) {
        this.x = (int) vector.getX();
        this.y = (int) vector.getY();
        this.assertCoordinateNotNegative();
    }

    private void assertCoordinateNotNegative() {
        if (this.x < 0) {
            throw new IllegalArgumentException("x is not allowed to be negative");
        }
        if (this.y < 0) {
            throw new IllegalArgumentException("y is not allowed to be negative");
        }
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        var pattern = Pattern.compile("^\\(\\d,\\d\\)$");
        var matcher = pattern.matcher(coordinateString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(coordinateString + " is malformed");
        }
        var stringTokenizer = new StringTokenizer(coordinateString, "(),");
        this.x = Integer.parseInt(stringTokenizer.nextToken());
        this.y = Integer.parseInt(stringTokenizer.nextToken());
    }

    public double distance(Coordinate other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
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
