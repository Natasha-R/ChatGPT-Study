package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Exercise0 implements Walkable {
    private Point location;
    private final int width = 12;
    private final int height = 8;

    private boolean[][] walls;

    public Exercise0() {
        location = new Point(
                3,
                0
        );
        initializeWalls();
    }

    @Override
    public String walkTo(String walkCommandString) {
        String direction = extractDirection(walkCommandString);
        int steps = extractSteps(walkCommandString);

        if (direction.equals("no")) {
            walkNorth(steps);
        } else if (direction.equals("ea")) {
            walkEast(steps);
        } else if (direction.equals("so")) {
            walkSouth(steps);
        } else if (direction.equals("we")) {
            walkWest(steps);
        }

        return formatLocation();
    }

    private void walkNorth(int steps) {
        for (int i = 0; i < steps; i++) {
            if (location.y < height - 1 && !walls[location.x][location.y + 1]) {
                location.y++;
            } else break;
        }
    }

    private void walkEast(int steps) {
        for (int i = 0; i < steps; i++) {
            if (location.x < width - 1 && !walls[location.x + 1][location.y]) {
                location.x++;
            } else break;
        }
    }

    private void walkSouth(int steps) {
        for (int i = 0; i < steps; i++) {
            if (location.y > 0 && !walls[location.x][location.y - 1]) {
                location.y--;
            } else break;
        }
    }

    private void walkWest(int steps) {
        for (int i = 0; i < steps; i++) {
            if (location.x > 0 && !walls[location.x - 1][location.y]) {
                location.x--;
            } else break;
        }
    }

    private String extractDirection(String walkCommandString) {
        try {
            return walkCommandString.substring(
                    1,
                    3
            );
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(
                    "The provided walk command is not valid.",
                    e
            );
        }
    }

    private int extractSteps(String walkCommandString) {
        try {
            return Integer.parseInt(walkCommandString.substring(
                    4,
                    walkCommandString.length() - 1
            ));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(
                    "The number of steps provided is not valid.",
                    e
            );
        }
    }

    private String formatLocation() {
        return "(" +
                location.x +
                "," +
                location.y +
                ")";
    }

    private void initializeWalls() {
        walls = new boolean[width][height];
        setWalls(
                3,
                3,
                0,
                3
        );
        setWalls(
                7,
                7,
                0,
                2
        );
        setWalls(
                4,
                7,
                3,
                3
        );
        setWalls(
                1,
                8,
                4,
                4
        );
    }

    private void setWalls(int startI, int endI, int startJ, int endJ) {
        for (int i = startI; i <= endI; i++) {
            for (int j = startJ; j <= endJ; j++) {
                walls[i][j] = true;
            }
        }
    }
}
