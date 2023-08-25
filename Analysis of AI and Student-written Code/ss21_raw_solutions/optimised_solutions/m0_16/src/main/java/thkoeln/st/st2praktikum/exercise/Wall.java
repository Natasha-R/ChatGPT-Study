package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

class Wall {

    private static List<Wall> walls = new ArrayList<>();

    private int x1, y1, x2, y2;

    public Wall(int x1, int y1, int x2, int y2) {
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
    }

    public static void setupBoundaryWalls(int width, int height) {
        addWall(new Wall(0, 0, width-1, 0));
        addWall(new Wall(0, 0, 0, height-1));
        addWall(new Wall(0, height-1, width-1, height-1));
        addWall(new Wall(width-1, 0, width-1, height-1));
    }

    public static void addWall(Wall wall) {
        walls.add(wall);
    }

    public static boolean isWall(int x, int y) {
        for (Wall wall : walls) {
            if ((wall.x1 == x && y >= wall.y1 && y <= wall.y2) ||
                    (wall.y1 == y && x >= wall.x1 && x <= wall.x2)) {
                return true;
            }
        }
        return false;
    }
}
