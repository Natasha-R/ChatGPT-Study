package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;


@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class Coordinate {


    private Integer x;
    private Integer y;



    public Coordinate(Integer x, Integer y) {

        if (x < 0 || y < 0 ){
            throw new RuntimeException("Coordinate cant be negative.");
        }
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
        int a = coordinateString.length();
        if (coordinateString.length() != 5){
            throw new RuntimeException("The coordinate has not the requiered lenght.");
        }
        if (Character.getNumericValue(coordinateString.charAt(1)) < 0 || Character.getNumericValue(coordinateString.charAt(3)) < 0){
            throw new RuntimeException("Coordinates has to be positive.");
        }

        return new Coordinate(Character.getNumericValue(coordinateString.charAt(1)),Character.getNumericValue(coordinateString.charAt(3)));
    }

}
