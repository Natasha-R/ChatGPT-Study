package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class Coordinate implements Serializable {

    @Getter @Setter
    @Column(insertable = false,updatable = false)
    private Integer x;
    @Getter @Setter
    @Column(insertable = false,updatable = false)
    private Integer y;

    public Coordinate(Integer x, Integer y) {
        if(x < 0 || y < 0 )
            throw new InvalidArgumentRuntimeException("NegativeCoordinate");

        this.x = x;
        this.y = y;
    }

    public Coordinate(String coordinateString) {
        val cord = fromString(coordinateString);
        this.x = cord.getX();
        this.y = cord.getY();
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

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        if(coordinateString.toCharArray()[0] != '(' || coordinateString.toCharArray()[coordinateString.length()-1] != ')')
            throw new RuntimeException("Invalid Argument Exception");

        var rawString = coordinateString.substring(1 , coordinateString.length()-1);

        if(rawString.split(",").length != 2)
            throw new RuntimeException("Invalid Argument Exception");

        val x = Integer.parseInt(rawString.split(",")[0]);
        val y = Integer.parseInt(rawString.split(",")[1]);

        return new Coordinate(x,y);
    }
    
}
