package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.customexceptions.InvalidStringFormatException;
import thkoeln.st.st2praktikum.exercise.customexceptions.NegativePointException;

import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Point {
    private final Integer x;
    private final Integer y;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point that = (Point) o;
        return Objects.equals(this.x, that.x) &&
                Objects.equals(this.y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")"; // Maybe the whitespace needs to be deleted.
    }

    public Boolean isGreaterThan(Point that) {
        return this.getX().equals(that.getX()) && this.getY() > that.getY()
                || this.getY().equals(that.getY()) && this.getX() > that.getX();
    }

    private void checkIfPointStringIsValid(String pointString) {
        Pattern pattern = Pattern.compile("^\\([0-9]+,[0-9]+\\)$");
        Matcher matcher = pattern.matcher(pointString);
        if (!matcher.matches()) {
            throw new InvalidStringFormatException("\"" + pointString + "\" is not a valid pointString in Point().");
        }
    }

    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        checkIfPointStringIsValid(pointString);
        StringTokenizer st = new StringTokenizer(pointString, "(),");
        this.x = Integer.parseInt(st.nextToken());
        this.y = Integer.parseInt(st.nextToken());
    }
    public Point(Integer _x, Integer _y) {
        if (_x < 0 || _y < 0) {
            throw new NegativePointException("A Point cannot have negative coordinates.");
        }

        this.x = _x;
        this.y = _y;
    }
}
