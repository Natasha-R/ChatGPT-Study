package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Coordinate {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    private Integer x;
    private Integer y;

    public Coordinate(){}

    public Coordinate(Integer x, Integer y) {
        if (x < 0 || y < 0 ){
            throw new RuntimeException("Coordinate cant be negative.");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        int a = coordinateString.length();
        if (coordinateString.length() != 5){
            throw new RuntimeException("The coordinate has not the requiered lenght.");
        }
        if (Character.getNumericValue(coordinateString.charAt(1)) < 0 || Character.getNumericValue(coordinateString.charAt(3)) < 0){
            throw new RuntimeException("Coordinates has to be positive.");
        }
        this.x = Character.getNumericValue(coordinateString.charAt(1));
        this.y = Character.getNumericValue(coordinateString.charAt(3));
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
}
