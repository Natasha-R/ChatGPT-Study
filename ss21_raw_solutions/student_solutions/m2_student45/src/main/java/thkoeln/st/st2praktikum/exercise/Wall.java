package thkoeln.st.st2praktikum.exercise;

import org.modelmapper.internal.Pair;

import java.util.Objects;

public class Wall {

    private Coordinate start;
    private Coordinate end;


    public Wall(Coordinate pos1, Coordinate pos2) {
        this(String.format("%s-%s", pos1, pos2));
    }

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public Wall(String wallString) {
        handleParsingExceptions(wallString);

        Pair<Coordinate, Coordinate> coordinatePair = convertToCoordinatePair(wallString);

        Coordinate pos1 = coordinatePair.getLeft();
        Coordinate pos2 = coordinatePair.getRight();

        if(isWallDiagonal(pos1, pos2))
            throw new InvalidCoordinateException("The wall should not be diagonal.");

        initOrderedStartAndEnd(pos1, pos2);
    }

    private boolean isWallDiagonal(Coordinate pos1, Coordinate pos2){
        return !isWallVertical(pos1, pos2) && !isWallHorizontal(pos1, pos2);
    }

    private void handleParsingExceptions(String wallString){
        String errorMessage = getWallStringErrorMessage(wallString);
        if(!errorMessage.equals(""))
            throw new InvalidCoordinateException(errorMessage);
    }

    private String getWallStringErrorMessage(String wallString){
        if (wallString.equals(""))
            return "The parameter should not be empty.\n";

        if(wallString.contains("-")){
            String[] parts = wallString.split("-");

            String errorMessage = getComposedErrorMessage(parts);

            if(!errorMessage.equals(""))
                return "One or both coordinates are wrong:\n" + errorMessage;
        }

        return "";
    }

    private String getComposedErrorMessage(String[] parts){
        String errorMessage;

        if(parts.length == 2)
            errorMessage = Coordinate.getCoordinateStringErrorMessage(parts[0]) +
                    Coordinate.getCoordinateStringErrorMessage(parts[1]);
        else
            errorMessage = "The format is not valid.\n";

        return errorMessage;
    }

    private void initOrderedStartAndEnd(Coordinate pos1, Coordinate pos2){
        Pair<Coordinate, Coordinate> orderedCoordinatePair;

        if(isWallVertical(pos1, pos2))
            orderedCoordinatePair = getOrderedVerticalCoordinates(pos1, pos2);
        else
            orderedCoordinatePair = getOrderedHorizontalCoordinates(pos1, pos2);


        start = orderedCoordinatePair.getLeft();
        end = orderedCoordinatePair.getRight();
    }

    private boolean isWallVertical(Coordinate pos1, Coordinate pos2){
        return pos1.getX().equals(pos2.getX());
    }

    private boolean isWallHorizontal(Coordinate pos1, Coordinate pos2){
        return pos1.getY().equals(pos2.getY());
    }

    private Pair<Coordinate, Coordinate> getOrderedVerticalCoordinates(Coordinate pos1, Coordinate pos2){
        Pair<Coordinate, Coordinate> orderedVerticalCoordinates;

        if(pos1.getY() < pos2.getY())
            orderedVerticalCoordinates = Pair.of(pos1, pos2);
        else
            orderedVerticalCoordinates = Pair.of(pos2, pos1);

        return orderedVerticalCoordinates;
    }

    private Pair<Coordinate, Coordinate> getOrderedHorizontalCoordinates(Coordinate pos1, Coordinate pos2) {
        Pair<Coordinate, Coordinate> orderedHorizontalCoordinates;

        if(pos1.getX() < pos2.getX())
            orderedHorizontalCoordinates = Pair.of(pos1, pos2);
        else
            orderedHorizontalCoordinates = Pair.of(pos2, pos1);

        return orderedHorizontalCoordinates;
    }

    private Pair<Coordinate, Coordinate> convertToCoordinatePair(String wallString) {
        try {
            String str = wallString
                    .replace("(", "")
                    .replace(")", "")
                    .replaceAll("\\s+", "");
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
            throw new InvalidCoordinateException("The parameter \"coordinate\" is not a coordinate.");
        }
    }

    public boolean isOutOfBound(int width, int height) {
        return isOutOfBound(start, width, height) || isOutOfBound(end, width, height);
    }

    private boolean isOutOfBound(Coordinate coordinate, int width, int height){
        boolean checkedX = coordinate.getX() < 0 || coordinate.getX() > width;
        boolean checkedY = coordinate.getY() < 0 || coordinate.getY() > height;

        return checkedX || checkedY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(start, that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return String.format("%s-%s", start, end);
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
