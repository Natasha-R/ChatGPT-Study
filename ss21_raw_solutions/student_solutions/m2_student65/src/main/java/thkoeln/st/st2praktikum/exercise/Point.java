package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        checkForNegative();
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        Point p = string2Point(pointString);
        x = p.getX();
        y = p.getY();
        checkForNegative();
        /*
        if(x < 0 || y < 0)
        {
            throw new IllegalActionException("A point has to be positive values!");
        }

         */
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }
    static Point string2Point(String string)
    {
        int index = string.indexOf("(");
        if(index != 0)
        {
            throw new IllegalActionException("Coordinate has wrong format!");
        }
        int x = Obstacle.getNumberAt(string, index + 1);
        int checkForInvalidChars = index + String.valueOf(x).length() + 1;
        index = string.indexOf(",");
        if(index != checkForInvalidChars)
        {
            throw new IllegalActionException("Coordinate has wrong format!");
        }
        int y = Obstacle.getNumberAt(string, index + 1);
        checkForInvalidChars = index + String.valueOf(y).length() + 1;
        index = string.indexOf(")");
        if(index != checkForInvalidChars)
        {
            throw new IllegalActionException("Coordinate has wrong format!");
        }
        if(x < 0 || y < 0)
        {
            throw new IllegalActionException("Coordinate has wrong format!");
        }
        return new Point(x,y);
    }
    private void checkForNegative()
    {
        if(x < 0 || y < 0)
        {
            throw new IllegalActionException("Point has to be positive values!");
        }
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
