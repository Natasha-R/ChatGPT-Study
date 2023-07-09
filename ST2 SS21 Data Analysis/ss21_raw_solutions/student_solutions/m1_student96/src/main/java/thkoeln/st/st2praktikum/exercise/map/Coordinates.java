package thkoeln.st.st2praktikum.exercise.map;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.stringConversion.Converter;

public @Data
class Coordinates {
    private Converter convert = new Converter();
    private int x;
    private int y;

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Coordinates(String coordinateString){
        int[] coordinates = convert.toIntArray(coordinateString);
        this.x = coordinates[0];
        this.y = coordinates[1];
    }

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int[] convertCoordinateString(String coordinateString) {
        int[] coordinates = {-1,-1};
        coordinates[0] = Integer.parseInt(coordinateString.replaceAll(",.*", ""));         //https://stackoverflow.com/questions/27095087/coordinates-string-to-int
        coordinates[1] = Integer.parseInt(coordinateString.replaceAll(".*,", ""));
        return coordinates;
    }
}
