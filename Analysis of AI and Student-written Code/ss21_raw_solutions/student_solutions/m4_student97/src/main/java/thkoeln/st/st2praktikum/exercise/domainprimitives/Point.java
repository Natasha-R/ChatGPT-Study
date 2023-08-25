package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Iterator;


@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Point {

    private Integer x;
    private Integer y;


    public Point(Integer x, Integer y) throws InvalidInputException {
        if (!validatePositiveCoordinateInput(x, y)) {
            throw new InvalidInputException("Coordinates are below 0. Negative Coordinates are not permitted.");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * @param vector2DString the vector2D in form of a string e.g. (1,2)
     */
    public static Point fromString(String vector2DString) throws InvalidInputException {
        if (!validateCoordinateStringFormatting(vector2DString)) {
            throw new InvalidInputException("String formatting is invalid");
        }
        Pair<Integer, Integer> coordinatePair = parseStringToCoordinates(vector2DString);
        return new Point(coordinatePair.getLeft(), coordinatePair.getRight());
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

    private Boolean validatePositiveCoordinateInput(Integer x, Integer y) {
        return (x >= 0 && y >= 0);
    }

    private static Boolean validateCoordinateStringFormatting(String vector2DString) {
        return vector2DString.matches("\\(\\d+,\\d\\)");
    }

    private static Pair<Integer, Integer> parseStringToCoordinates(String vector2DString) {
        vector2DString = vector2DString.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] coordinateSplit = vector2DString.split(",");
        return new Pair(Integer.parseInt(coordinateSplit[0]), Integer.parseInt(coordinateSplit[1]));
    }
}
