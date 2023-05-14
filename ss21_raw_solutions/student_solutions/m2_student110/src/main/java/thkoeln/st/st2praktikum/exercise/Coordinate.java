package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate extends TidyUpRobotService {

    private Integer x;
    private Integer y;
    private String coordinateString;
    private Boolean isValidCoordinate;


    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.isValidCoordinate = isValidCoordinate(this);
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
         this.coordinateString=coordinateString;
         this.x=Integer.parseInt(coordinateString.substring(1,2));
         this.y=Integer.parseInt(coordinateString.substring(3,coordinateString.length()-1));
         this.isValidCoordinate = isValidCoordinate(this);


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
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

    public Boolean isValidCoordinate(Coordinate coordinate){
        Integer coordinateLength = coordinate.toString().length();

        if (coordinateLength == 5 ){
            return true;
        }
        else throw new IllegalArgumentException("Invalid Coordinate");
    }


}
