package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@Setter
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if(x < 0 || y < 0) throw new IllegalArgumentException();
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
        int[] coordinates = parseCoordinateStringToIntArray(coordinateString);
        if(coordinates[0] < 0 || coordinates[1] < 0) throw new IllegalArgumentException();
        return new Coordinate(coordinates[0], coordinates[1]);
    }

    private static int[] parseCoordinateStringToIntArray(String coordinateString) {
        String[] splitString = coordinateString.substring(1, coordinateString.length() - 1).split(",");
        if (splitString.length > 2)
            throw new IllegalArgumentException();
        else
            return Arrays.stream(splitString).mapToInt(s -> {
                int coordinate = Integer.parseInt(s.strip());
                if (coordinate < 0)
                    throw new IllegalArgumentException();
                else
                    return coordinate;
            }).toArray();
    }

}
