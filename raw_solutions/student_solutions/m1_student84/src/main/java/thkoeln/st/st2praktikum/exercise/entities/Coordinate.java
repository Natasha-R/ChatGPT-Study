package thkoeln.st.st2praktikum.exercise.entities;

public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate fromString(String coordinateString) {
        String[] coordinates = coordinateString.replace("(", "").replace(")", "").split(",");

        return new Coordinate(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
