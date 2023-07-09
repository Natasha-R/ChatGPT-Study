package thkoeln.st.st2praktikum.exercise;

public class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
