package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate {

    private Integer x;
    private Integer y;

    public Coordinate(Integer x, Integer y) {
        validCoordinatePoints(x, y);
        this.x = x;
        this.y = y;
    }

    /**
     * @param coordinateString the coordinate in form of a string e.g. (1,2)
     */
    public Coordinate(String coordinateString) {
        Integer[] array = getIntegersFromString(coordinateString);
        validCoordinatePoints(array[0], array[1]);

        this.x = array[0];
        this.y = array[1];
    }

    private void validCoordinatePoints(Integer x, Integer y){
        if( x < 0 || y < 0) {
            throw new RuntimeException("Coordinates have to be positive! Your coordinates: ("+x+","+y+")");
        }
    }

    private Integer[] getIntegersFromString(String coordinateString){
        Integer[] array = new Integer[2];
        if(coordinateString.matches("\\(\\d+\\,\\d+\\)")) {
            String[] coordinateStringSplit = coordinateString.replaceAll("[()]","").split(",");
            try{
                array[0] = Integer.parseInt(coordinateStringSplit[0]);
                array[1] = Integer.parseInt(coordinateStringSplit[1]);
                return array;
            }catch(Exception e){
                throw new RuntimeException("Failed to parse Integer from Coordinate String. Your Coordinate String: "+coordinateString);
            }
        }else{
            throw new RuntimeException("This Coordinate is wrongly formatted! Your Coordinate string: " + coordinateString);
        }
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

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}
