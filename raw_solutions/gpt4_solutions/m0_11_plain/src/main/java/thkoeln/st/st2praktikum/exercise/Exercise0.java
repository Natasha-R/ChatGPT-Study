package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Exercise0 implements Walkable {
    private int width = 12;
    private int height = 9;
    private Point currentLocation = new Point(0, 2);

    private static final String NORTH = "no";
    private static final String EAST = "ea";
    private static final String SOUTH = "so";
    private static final String WEST = "we";

    private boolean[][] horizontalWalls = new boolean[width + 1][height];
    private boolean[][] verticalWalls = new boolean[width][height + 1];

    public Exercise0() {
        initializeWalls();
    }

    private void initializeWalls() {
        for (int i = 0; i <= width; i++) {
            verticalWalls[i][0] = true;
            verticalWalls[i][height] = true;
        }

        for (int i = 0; i <= height; i++) {
            horizontalWalls[0][i] = true;
            horizontalWalls[width][i] = true;
        }

        addWall(3, 0, 3, 3);
        addWall(5, 0, 5, 4);
        addWall(4, 5, 7, 5);
        addWall(7, 5, 7, 9);
    }

    private void addWall(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            for (int i = y1; i <= y2; i++) {
                verticalWalls[x1][i] = true;
            }
        } else if (y1 == y2) {
            for (int i = x1; i <= x2; i++) {
                horizontalWalls[i][y1] = true;
            }
        }
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");

        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        for (int i = 0; i < steps; i++) {
            if (!move(direction)) {
                break;
            }
        }

        return currentLocation.toString();
    }

    private boolean move(String direction) {
        int x = currentLocation.x;
        int y = currentLocation.y;

        switch (direction) {
            case NORTH:
                if (!horizontalWalls[x][y + 1]) {
                    currentLocation.y++;
                    return true;
                }
                break;
            case EAST:
                if (!verticalWalls[x + 1][y]) {
                    currentLocation.x++;
                    return true;
                }
                break;
            case SOUTH:
                if (!horizontalWalls[x][y]) {
                    currentLocation.y--;
                    return true;
                }
                break;
            case WEST:
                if (!verticalWalls[x][y]) {
                    currentLocation.x--;
                    return true;
                }
                break;
        }
        return false;
    }
}
