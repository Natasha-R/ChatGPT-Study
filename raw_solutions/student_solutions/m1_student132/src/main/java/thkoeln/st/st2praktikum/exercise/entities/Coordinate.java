package thkoeln.st.st2praktikum.exercise.entities;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(String coordinateString) {
        if (coordinateString.contains("(") && coordinateString.contains(",") && coordinateString.contains(")")) {
            String[] parts = coordinateString.split("\\(");
            parts = parts[1].split("\\)");
            parts = parts[0].split(",");

            this.x = Integer.parseInt(parts[0]);
            this.y = Integer.parseInt(parts[1]);
        } else {
            throw new IllegalArgumentException("String " + coordinateString + " isn't formatted correctly.");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public Boolean equals(Coordinate coordinate) {
        return this.getX() == coordinate.getX() && this.getY() == coordinate.getY();
    }
}
