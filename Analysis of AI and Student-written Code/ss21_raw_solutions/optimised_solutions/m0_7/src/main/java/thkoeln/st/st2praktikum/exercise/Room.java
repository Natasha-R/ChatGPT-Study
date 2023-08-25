package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.List;

public class Room {
    private final int width;
    private final int height;
    private final List<Point> walls;

    Room(int width, int height, List<Point> walls) {
        this.width = width;
        this.height = height;
        this.walls = walls;
    }

    boolean isValidPosition(Point position) {
        if (position.x < 0 || position.x >= width || position.y < 0 || position.y >= height) {
            return false;
        }
        return !walls.contains(position);
    }
}