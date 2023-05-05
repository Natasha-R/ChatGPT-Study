package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Points have to be positive");
        this.x = x;
        this.y = y;
    }

    public int distToOrigin(){
        return (int)Math.sqrt(Math.pow(this.x,2) + Math.pow(this.y,2));
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        String[] coordinates = pointString.replaceAll("[()]","").split(",");
        if (coordinates.length != 2)
            throw new IllegalArgumentException("Illegal amount of parameters!");
        try{
            this.x = Integer.parseInt(coordinates[0]);
            this.y = Integer.parseInt(coordinates[1]);
            if (x < 0 || y < 0)
                throw new IllegalArgumentException("Points have to be positive!");
        } catch (Exception exp){
            throw exp;
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
}
