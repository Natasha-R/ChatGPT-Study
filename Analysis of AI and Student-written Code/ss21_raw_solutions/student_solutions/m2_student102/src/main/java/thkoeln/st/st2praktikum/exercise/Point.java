package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;

    public Point(){}
    public Point(Integer x, Integer y) {
        if(x<0 || y<0)
            throw new RuntimeException();
        this.x = x;
        this.y = y;
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */
    public Point(String pointString) {
        int tmpX,tmpY;
        String[] divideString;
        divideString=pointString.split(",");
        tmpX= Character.getNumericValue( pointString.charAt(1) );
        tmpY= Character.getNumericValue( pointString.charAt(3) );

        if(divideString.length==2 && 0<=tmpX && tmpX<=20 && 0<=tmpY && tmpY<=20) {
            this.x = tmpX;
            this.y = tmpY;
        }else
            throw new RuntimeException();

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
    public void setX(Integer x) {
        this.x = x;
    }
    public void setY(Integer y) {
        this.y = y;
    }
}
