package thkoeln.st.st2praktikum.exercise;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public void setNewCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setNewCoordinates(Coordinate newCoordinates) {
        this.x = newCoordinates.getX();
        this.y = newCoordinates.getY();
    }

    public void increaseX() { this.x++; }
    public void increaseY() { this.y++; }
    public void decreaseX() { this.x--; }
    public void decreaseY() { this.y--; }

    public static Coordinate fromString(String coordinateString) {
        String[] coordinates = coordinateString.split(",");
        int x = Integer.parseInt(coordinates[0].substring(1));
        int y = Integer.parseInt(coordinates[1].substring(0, coordinates[1].length() - 1));
        return new Coordinate(x, y);
    }
}
