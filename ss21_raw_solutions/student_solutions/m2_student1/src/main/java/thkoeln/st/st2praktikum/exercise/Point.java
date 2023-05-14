package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Point {

    private final CheckCorrectString checkCorrectString = new CheckCorrectString();
    private final Integer x;
    private final Integer y;


    public Point(Integer x, Integer y) {
        if(x < 0 || y < 0)
            throw new RuntimeException("Keine ´negativen Punkte");
        this.x = x;
        this.y = y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        Point point = new Point(Integer.parseInt(pointString.substring(1,pointString.indexOf(","))), Integer.parseInt(pointString.substring(pointString.indexOf(",")+1, pointString.indexOf(")"))));
        x = point.getX();
        y = point.getY();
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
