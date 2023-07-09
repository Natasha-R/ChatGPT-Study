package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) {
        this.checkIntegerNotNegativ(x,y);
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) throws IllegalArgumentException {

        String newPointRaw = pointString.substring(1, pointString.length() - 1);
        String[] newPoint = newPointRaw.split(",");

        if(newPoint.length < 3){
            this.checkIntegerNotNegativ(Integer.parseInt(newPoint[0]),Integer.parseInt(newPoint[1]));
        }else{
            throw new IllegalArgumentException("Point zu großs");
        }
    }

    private void checkIntegerNotNegativ(Integer x, Integer y) throws IllegalArgumentException{
        if(x>= 0){
            this.x = x;
        }else{
            throw new IllegalArgumentException("X kleiner 0");
        }
        if(y >= 0){
            this.y = y;
        }else{
            throw new IllegalArgumentException("Y kleiner 0");
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
