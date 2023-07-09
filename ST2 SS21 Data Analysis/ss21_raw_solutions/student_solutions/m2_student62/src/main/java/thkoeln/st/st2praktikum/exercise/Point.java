package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer px, Integer py) {
        if(px<0||py<0){
            throw new InvalidParameterException();
        }
        x = px;
        y = py;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        String[] positions = (pointString.substring(1,pointString.length()-1)).split(",");
        if(positions.length>2){
            throw new InvalidParameterException();
        }
        x = Integer.parseInt(positions[0]);
        y = Integer.parseInt(positions[1]);
    }

    public void updateX(int steps){
        x+=steps;
    }

    public void updateY(int steps){
        y+=steps;
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
