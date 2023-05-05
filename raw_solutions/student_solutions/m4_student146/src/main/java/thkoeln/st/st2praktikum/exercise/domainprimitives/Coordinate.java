package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        if (!Pattern.matches("\\d+", x.toString()) || !Pattern.matches("\\d+", y.toString()))
            throw new RuntimeException("The coordinates has to be positive!");
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        if (!Pattern.matches("\\(\\d+,\\d+\\)", coordinateString))
            throw new RuntimeException("Wrong format or the coordinates are not positive!");
        String[] coordinateSplit = coordinateString.split(",");
        String tmpX = coordinateSplit[0].replace("(", "");
        String tmpY = coordinateSplit[1].replace(")", "");
        Coordinate coordinate = new Coordinate();
        coordinate.x = Integer.parseInt(tmpX);
        coordinate.y = Integer.parseInt(tmpY);
        return coordinate;
    }

}
