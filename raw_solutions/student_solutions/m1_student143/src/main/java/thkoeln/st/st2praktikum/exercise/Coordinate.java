package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate coordinate){
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    @Override
    public String toString() { return "(" + x + "," + y + ')'; }

    static Coordinate fromString(String coordinateString) {
        String [] xy = coordinateString
                .replaceAll("\\(|\\)","")
                .split(",");
        Coordinate coordinate = new Coordinate(
                Integer.parseInt(xy[0]),
                Integer.parseInt(xy[1])
        );
        return coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
