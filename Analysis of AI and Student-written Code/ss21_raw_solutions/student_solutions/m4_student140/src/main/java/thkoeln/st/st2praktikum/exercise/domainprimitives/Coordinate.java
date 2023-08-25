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
        if(x < 0 || y < 0)
            throw new IllegalArgumentException("coordinates cant be negative");

        this.x = x;
        this.y = y;
    }

    //maybe remove later
    public Coordinate(String coordinateString) {

        String[] coordinatesSplit = coordinateString.split(",");

        Integer locationX = Integer.parseInt( coordinatesSplit[ 0 ].substring( 1 ) );

        Integer locationY = Integer.parseInt( coordinatesSplit[ 1 ].substring( 0 , coordinatesSplit[ 1 ].length() - 1));

        if( locationX < 0 || locationY < 0 )
            throw new IllegalArgumentException("no negative numbers");

        this.x = locationX;
        this.y = locationY;
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

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        String[] coordinatesSplit = coordinateString.split(",");

        Integer locationX = Integer.parseInt(coordinatesSplit[ 0 ].substring( 1 ) );

        Integer locationY = Integer.parseInt(coordinatesSplit[ 1 ].substring(0,coordinatesSplit[1].length() - 1 ) );

        if(locationX < 0 || locationY < 0)
            throw new RuntimeException("no negative numbers");

        return new Coordinate( locationX , locationY );
    }
}
