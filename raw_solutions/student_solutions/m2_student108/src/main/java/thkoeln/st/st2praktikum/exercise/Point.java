package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Point {
    private final Integer x;
    private final Integer y;

    public Point(Integer x, Integer y) {
        if (x < 0 || y < 0) throw new RuntimeException("Negative Coordinates aren't allowed for Points!");
        this.x = x;
        this.y = y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        try {
            int[] coordinates = transformCoordinates(pointString);
            this.x = coordinates[0];
            this.y = coordinates[1];
            if (x < 0 || y < 0) throw new RuntimeException("Negative Coordinates aren't allowed for Points!");
        } catch (RuntimeException exception) {
            throw exception;
        }
    }

    private int[] transformCoordinates(String coordinates) {
        if (coordinates.length() < 5) throw new RuntimeException("Coordinates should have the form \"(x,y)\"!");
        String s = coordinates.substring(1, coordinates.length()-1);
        String[] arr = s.split(",");
        if (arr.length != 2) throw new RuntimeException("You need exact 2 Coordinates (x & y) to create a Point!");
        int[] coordinatesAsInt = new int[]{
                Integer.parseInt(arr[0]),
                Integer.parseInt(arr[1])
        };
        if (coordinatesAsInt[0] < 0 || coordinatesAsInt[1] < 0) throw new RuntimeException("Coordinates have to be positive!");
        return coordinatesAsInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
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
