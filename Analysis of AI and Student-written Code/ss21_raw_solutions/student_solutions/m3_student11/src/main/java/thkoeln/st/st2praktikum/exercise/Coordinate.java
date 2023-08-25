package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exceptions.CoordinateFormatException;
import thkoeln.st.st2praktikum.exercise.exceptions.CoordinateOutOfBoundsException;

import javax.persistence.Embeddable;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Objects;

@Embeddable
public class Coordinate {

    @Setter
    @Getter
    private Integer x ,y ;

    protected Coordinate(){ }

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if(x < 0 || y < 0 ) throw new CoordinateOutOfBoundsException();
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
        if(x < 0 || y < 0 ) throw new CoordinateOutOfBoundsException();
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


    //returns true if the format is right f.e. (2,2)
    private boolean coordinateFormatTest(String Input){
        return Input.charAt(0) == '(' && Input.charAt(2) == ',' && Input.charAt(4) == ')';
    }

}
