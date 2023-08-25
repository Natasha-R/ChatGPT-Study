package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;


@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        if (x < 0 || y < 0) {
            throw new RuntimeException("x and y must be greater or equal than 0");
        }
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
        if (!coordinateString.matches("\\p{Punct}\\p{Digit}\\p{Punct}\\p{Digit}\\p{Punct}")){
            throw new RuntimeException("Invalid coordinateString");
        }
        String cleanedCoordinateString = coordinateString.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] sourceCoordinateArray = cleanedCoordinateString.split(",");
        int x = Integer.parseInt(sourceCoordinateArray[0]);
        int y = Integer.parseInt(sourceCoordinateArray[1]);
        if (x < 0 || y < 0) {
            throw new RuntimeException("x and y must be greater or equal than 0");
        }
        return new Coordinate(x,y);
    }

}
