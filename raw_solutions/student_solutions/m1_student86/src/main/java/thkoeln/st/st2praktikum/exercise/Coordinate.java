package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

public class Coordinate {

    @Getter
    @Setter
    private int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int addToX (int value) {
        return this.x += value;
    }
    public int addToY (int value) {
        return this.y += value;
    }

    public String toString ()
    {
        return "(" + x + "," + y +")";
    }

    @Override
    public boolean equals (Object object) {
        return x == ((Coordinate)object).x && y == ((Coordinate)object).y;
    }

    @Override
    public int hashCode () {
        return x + y;
    }
}