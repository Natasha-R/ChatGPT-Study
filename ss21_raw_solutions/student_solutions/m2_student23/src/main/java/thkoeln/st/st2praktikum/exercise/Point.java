package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        this.setPointValues(x, y);
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        Pattern pointStringPattern = Pattern.compile("^\\((\\d*),(\\d*)\\)$");
        Matcher pointStringMatcher = pointStringPattern.matcher(pointString);

        Integer x = null, y = null;

        while (pointStringMatcher.find()) {
            x = Integer.parseInt(pointStringMatcher.group(1));
            y = Integer.parseInt(pointStringMatcher.group(2));
        }

        this.setPointValues(x, y);
    }

    private void setPointValues(Integer x, Integer y) {
        if (this.coordinatesAreNegativeOrNull(x, y))
            throw new InvalidParameterException("Point Coordinates must be positive");

        this.x = x;
        this.y = y;
    }

    private Boolean coordinatesAreNegativeOrNull(Integer x, Integer y) {
        return x == null || y == null || x < 0 || y < 0;
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

    public void setX(int x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incrementX(int amount) {
        this.x += amount;
    }

    public void decrementX(int amount) {
        this.x -= amount;
    }

    public void incrementY(int amount) {
        this.y += amount;
    }

    public void decrementY(int amount) {
        this.y -= amount;
    }
}
