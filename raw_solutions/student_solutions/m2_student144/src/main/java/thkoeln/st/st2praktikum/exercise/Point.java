package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Point {

    private Integer x;
    private Integer y;
    private String currentPosition;


    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.currentPosition = parsePointToString();
        checkFornegativPoint(this);
    }

    /**
     * @param pointString the point in form of a string e.g. (1,2)
     */

    public Point(String pointString){
        if(isStringValid(pointString)) {
            this.x = Integer.parseInt(pointString.substring(1, pointString.lastIndexOf(",")));
            this.y = Integer.parseInt(pointString.substring(pointString.lastIndexOf(",") + 1, pointString.indexOf(")")));
            this.currentPosition = parsePointToString();
        }

    }

    private String parsePointToString(){
        String currentPosition = "("+x+","+y+")";
        return currentPosition;
    }
    private boolean isStringValid(String pointString){
        Integer firstSubstring, secondSubstring;
        firstSubstring = Integer.parseInt(pointString.substring(1,pointString.lastIndexOf(",")));
        secondSubstring = Integer.parseInt(pointString.substring(pointString.lastIndexOf(",")+1,pointString.lastIndexOf(")")));
        if(firstSubstring<0 || secondSubstring<0){
            throw new RuntimeException("Point has to be positive");
        }
        else return true;
    }
    private void checkFornegativPoint(Point point){
        if(point.getX()<0 || point.getY()<0){
            throw new RuntimeException("Point has to be positive");
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

    public String getCurrentPosition(){
        return currentPosition;
    }

    public Integer getY() {
        return y;
    }
}
