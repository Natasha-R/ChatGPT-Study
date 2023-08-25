package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.WrongCoordinateException;

import java.util.Objects;

public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) throws WrongCoordinateException{
       if(x>= 0 && y >= 0) {
           this.x = x;
           this.y = y;
       }else{
           throw new WrongCoordinateException(x.toString() + y.toString());
       }
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) throws WrongCoordinateException {

            String newCoordinateRaw = coordinateString.substring(1, coordinateString.length()-1);
            String[] newCoordinate = newCoordinateRaw.split(",");

            if (newCoordinate.length == 2 && Integer.parseInt(newCoordinate[0]) >= 0 && Integer.parseInt(newCoordinate[1]) >= 0) {
                this.x = Integer.parseInt(newCoordinate[0]);
                this.y = Integer.parseInt(newCoordinate[1]);
            }else {
                throw new WrongCoordinateException(coordinateString);
            }

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
        return "(" + x + ", " + y + ")";
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Coordinate getCoordinate() {
        return this;
    }

}
