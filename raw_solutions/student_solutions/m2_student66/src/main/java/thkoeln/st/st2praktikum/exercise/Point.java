package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exceptions.PointException;

import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if(x<0 || y<0)
            throw new PointException("A point should not be negative");
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        try {
            String[] xAndYArray = pointString.split(",");
            x = Integer.parseInt(xAndYArray[0].substring(1));
            y = Integer.parseInt(xAndYArray[1].substring(0, xAndYArray[1].length() - 1));
        } catch(Exception e){
            throw new PointException("Pointstring should be in this form: (x,y), but get: " + pointString);
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
        return "(" + x + "," + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
