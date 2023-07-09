package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Wall {
    private int x1, y1, x2, y2;

    public Wall(int x1, int y1, int x2, int y2) {
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
    }

    public boolean isAt(Point p) {
        if (x1 == x2) { // This is a vertical wall
            return p.x == x1 && p.y >= y1 && p.y < y2;
        } else if (y1 == y2) { // This is a horizontal wall
            return p.y == y1 && p.x >= x1 && p.x < x2;
        } else {
            return false; // This should not happen for this exercise
        }
    }
}

