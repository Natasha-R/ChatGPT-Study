package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.CoordinateFormatException;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.Objects;

public class Coordinate {

    private Integer x;
    private Integer y;
    DirectionsType border;

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if(x < 0 || y < 0 ) throw new CoordinateFormatException();
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        if(!coordinateFormatTest(coordinateString)) throw new CoordinateFormatException();
        coordinateString = coordinateString.replace("(","").replace(")","");
        String[] separatedCords = coordinateString.split(",");
        try {
            this.x = Integer.parseInt(separatedCords[0]);
            this.y = Integer.parseInt(separatedCords[1]);
        }
        catch (Exception e){
            throw new CoordinateFormatException();
        }
        if(x < 0 || y < 0 ) throw new CoordinateFormatException();
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

    //returns true if format (2,2)
    private boolean coordinateFormatTest(String Input){
        if(Input.charAt(0) == '(' && Input.charAt(2) == ',' && Input.charAt(4) == ')'){
            return true;
        }
        return false;
    }

}
