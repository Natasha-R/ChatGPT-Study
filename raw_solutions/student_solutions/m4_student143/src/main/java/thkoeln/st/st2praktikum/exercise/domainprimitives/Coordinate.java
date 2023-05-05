package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;


@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Access(AccessType.FIELD)
public class Coordinate {
    int x;
    int y;


    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        if (!coordinateString.matches("\\(\\d+,\\d+\\)")) {
            throw new IllegalArgumentException("The coordinate string is not properly formatted");
        }
        String[] xy = coordinateString
                .replaceAll("\\(|\\)", "")
                .split(",");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        return new Coordinate(x,y);
    }

    public Coordinate(int x, int y) {
        ensureValidCoordinateInput(x,y);
        this.x = x;
        this.y = y;
    }

    static void ensureValidCoordinateInput(int x, int y){
        if(x < 0 || y < 0){
            throw new IllegalArgumentException("The coordinate shall be positive");
        }
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}





/*
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
     *//*
    public static Coordinate fromString(String coordinateString) {
        throw new UnsupportedOperationException();
    }
    
}*/
