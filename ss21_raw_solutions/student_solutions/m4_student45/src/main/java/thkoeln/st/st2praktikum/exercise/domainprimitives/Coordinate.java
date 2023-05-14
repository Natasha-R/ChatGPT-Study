package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;
import org.modelmapper.internal.Pair;

import javax.persistence.Embeddable;


@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
@Getter
@Setter (AccessLevel.PROTECTED)
public class Coordinate {

    private Integer x;
    private Integer y;


    public Coordinate(Integer x, Integer y) {
        Pair<Integer, Integer> xYPair = getValidXYPair(String.format("(%d,%d)", x, y));

        this.x = xYPair.getLeft();
        this.y = xYPair.getRight();
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public static Coordinate fromString(String coordinateString) {
        Pair<Integer, Integer> xYPair = getValidXYPair(coordinateString);

        return new Coordinate(xYPair.getLeft(), xYPair.getRight());
    }

    private static Pair<Integer, Integer> getValidXYPair(String coordinateString){
        handleExceptions(coordinateString);
        Pair<Integer, Integer> xYPair = getXYPair(coordinateString);

        return Pair.of(xYPair.getLeft(), xYPair.getRight());
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

    private static void handleExceptions(String coordinateString){
        String errorMessage = getCoordinateStringErrorMessage(coordinateString);

        if(!errorMessage.equals(""))
            throw new InvalidCoordinateException(errorMessage);
    }


    public static String getCoordinateStringErrorMessage(String coordinateString){
        String str;

        if (coordinateString.equals(""))
            return "The parameter should not be empty.\n";

        if(coordinateString.charAt(0) != '(' ||
                coordinateString.charAt(coordinateString.length() - 1) != ')')
            return "Braces are missing or at the wrong position.\n";

        str = coordinateString.replaceAll("\\d", "")
                .replaceAll("\\s+", "");
        if(!str.equals("(,)"))
            return "The format is not valid.\n";

        str = coordinateString.replaceAll("\\s+", "");
        if(str.length() < 5)
            return "A number is missing.\n";

        if(coordinateString.contains("-"))
            return "The coordinate should not have negative values.";

        return "";
    }

    private static Pair<Integer, Integer> getXYPair(String coordinateString){
        String str = coordinateString
                .replace("(", "")
                .replace(")", "");
        String[] coordinatesString = str.split(",");

        return Pair.of(Integer.parseInt(coordinatesString[0]), Integer.parseInt(coordinatesString[1]));
    }

    public static int getFlippedYCoordinate(int y, int height){
        return (height - 1) - y;
    }

    public boolean isOutOfBound(int width, int height){
        return (x >= width || x < 0) && (y >= height || y < 0);
    }
    
}
