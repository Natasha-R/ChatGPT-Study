package thkoeln.st.st2praktikum.exercise;

import lombok.Setter;

import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        this.validateCoordinates(x, y);

        this.x = x;
        this.y = y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        this.validateStringPattern(pointString);

        String[] splittedPointString = pointString.replace("(", "").replace(")", "").split(",");
        this.validateSplittedPointString(splittedPointString);

        Integer x = Integer.parseInt(splittedPointString[0]);
        Integer y = Integer.parseInt(splittedPointString[1]);
        this.validateCoordinates(x, y);

        this.x = x;
        this.y = y;
    }

    private void validateStringPattern(String pointString) {
        String pattern = "^\\(\\d+,\\d+\\)$";

        if(!pointString.matches(pattern)) {
            throw new RuntimeException("Can't create point from that string: " + pointString);
        }
    }

    private void validateSplittedPointString(String[] splittedPointString) {
        if(splittedPointString.length != 2) {
            throw new RuntimeException("Invalid pointString format");
        }
    }

    private void validateCoordinates(Integer x, Integer y) {
        if(x < 0 || y < 0) {
            throw new RuntimeException("Coordinates must be positive");
        }
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

    public void increaseX(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException();
        }

        x += steps;
    }

    public void decreaseX(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException();
        }

        x -= steps;
    }

    public void increaseY(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException();
        }

        y += steps;
    }

    public void decreaseY(int steps) {
        if(steps < 0) {
            throw new IllegalArgumentException();
        }

        y -= steps;
    }
}
