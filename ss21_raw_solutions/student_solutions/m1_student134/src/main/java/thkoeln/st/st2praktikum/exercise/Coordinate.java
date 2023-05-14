package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.internal.Pair;

import java.security.InvalidParameterException;

@Getter
@Setter
public class Coordinate{
    private int x, y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Coordinate getCoordinate(String coordinate) {
        try {
            String str = coordinate
                    .replace("(", "")
                    .replace(")", "");
            String[] coordinatesString = str.split(",");

            return new Coordinate(
                    Integer.parseInt(coordinatesString[0]),
                    Integer.parseInt(coordinatesString[1])
            );
        }
        catch(Exception e) {
            throw new InvalidParameterException("The parameter \"coordinate\" is not a coordinate.");
        }
    }

    public static Pair<Coordinate, Coordinate> getCoordinatePair(String coordinate) {
        try {
            String str = coordinate
                    .replace("(", "")
                    .replace(")", "");
            String[] coordinatesString = str.split("-");
            String[] firstCoordinateString = coordinatesString[0].split(",");
            String[] secondCoordinateString = coordinatesString[1].split(",");

            Coordinate first = new Coordinate(
                    Integer.parseInt(firstCoordinateString[0]),
                    Integer.parseInt(firstCoordinateString[1])
            );

            Coordinate second = new Coordinate(
                    Integer.parseInt(secondCoordinateString[0]),
                    Integer.parseInt(secondCoordinateString[1])
            );

            return Pair.of(first, second);
        }
        catch (Exception e){
            throw new InvalidParameterException("The parameter \"coordinate\" is not a coordinate.");
        }
    }

    public static int flipYCoordinate(int y, int height){
        return (height - 1) - y;
    }
}
