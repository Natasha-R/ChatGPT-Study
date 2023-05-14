package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.internal.Pair;
import thkoeln.st.st2praktikum.exercise.Exception.InvalidCoordinateException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
public class Coordinate {

    private Integer x;
    private Integer y;

    public Coordinate(Integer x, Integer y) {
        this(String.format("(%d,%d)", x, y));
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        handleExceptions(coordinateString);

        Pair<Integer, Integer> xYPair = getXYPair(coordinateString);

        x = xYPair.getLeft();
        y = xYPair.getRight();
    }

    private void handleExceptions(String coordinateString){
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
        if(str.length() != 5)
            return "A number is missing.\n";

        if(coordinateString.contains("-"))
            return "The coordinate should not have negative values.";

        return "";
    }

    Pair<Integer, Integer> getXYPair(String coordinateString){
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
}
