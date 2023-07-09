package thkoeln.st.st2praktikum.exercise;

import org.modelmapper.internal.Pair;

import java.security.InvalidParameterException;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    public static String[] coordinateReplace(String replaceString, char splitSymbol){
        String returnString =  replaceString.replace("(", "").replace(")","");
        return returnString.split(""+splitSymbol);
    }

    public static Coordinate getCoordinate(String coordinate){
        try{
            String[] coordinatesString = coordinateReplace(coordinate,',');
            return  new Coordinate(Integer.parseInt(coordinatesString[0]), Integer.parseInt(coordinatesString[1]));
        }catch (Exception e1){
            throw new InvalidParameterException("\"coordinate\" is not a correct parameter.");
        }
    }
    public static Pair<Coordinate, Coordinate> getCoordinatePair(String coordinate) {
        try {
            String[] coordinatesString = coordinateReplace(coordinate,'-');
            String[] firstCoordinateString = coordinatesString[0].split(",");
            String[] secondCoordinateString = coordinatesString[1].split(",");
            Coordinate first = new Coordinate(Integer.parseInt(firstCoordinateString[0]), Integer.parseInt(firstCoordinateString[1]));
            Coordinate second = new Coordinate(Integer.parseInt(secondCoordinateString[0]), Integer.parseInt(secondCoordinateString[1]));
            return Pair.of(first, second);
        } catch (Exception e2) {
            throw new InvalidParameterException("\"coordinate\" is not a correct parameter.");
        }
    }

    public static int flipYCoordinate(int y, int height) {
        return height - 1 - y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
