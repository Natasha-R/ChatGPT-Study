package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;


@EqualsAndHashCode
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        
        this.x = x;
        this.y = y;
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

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        throw new UnsupportedOperationException();
    }
    
}
