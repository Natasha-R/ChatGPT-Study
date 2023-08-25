package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Room {
    private final int width;
    private final int height;
    private boolean[][] walls;

    public Room(int width, int height) {
        this.width = width;
        this.height = height;
        walls = new boolean[width][height];
    }

    public void addWall(Wall wall) {
        for (int i = wall.getX1(); i <= wall.getX2(); i++) {
            for (int j = wall.getY1(); j <= wall.getY2(); j++) {
                walls[i][j] = true;
            }
        }
    }

    public boolean canMoveTo(Point point) {
        if (point.x < 0 || point.x >= width || point.y < 0 || point.y >= height) {
            return false;
        }

        return !walls[point.x][point.y];
    }
}

