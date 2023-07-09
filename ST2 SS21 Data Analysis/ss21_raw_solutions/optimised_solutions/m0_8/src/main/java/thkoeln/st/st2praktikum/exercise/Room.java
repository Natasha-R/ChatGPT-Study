package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Room {
    private int width;
    private int height;
    private List<Wall> walls;

    public Room(int width, int height, List<Wall> walls) {
        this.width = width;
        this.height = height;
        this.walls = walls;
    }

    public boolean isInBounds(Point p) {
        return p.x >= 0 && p.y >= 0 && p.x < width && p.y < height;
    }

    public boolean isWall(Point p) {
        for (Wall wall : walls) {
            if (wall.isAt(p)) {
                return true;
            }
        }
        return false;
    }
}
