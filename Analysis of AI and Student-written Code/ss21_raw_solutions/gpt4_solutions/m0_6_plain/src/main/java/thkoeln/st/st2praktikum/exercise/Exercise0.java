package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Exercise0 implements Walkable {
    private int width = 12;
    private int height = 9;
    private Point currentPosition = new Point(0, 2);
    private boolean[][] walls;

    public Exercise0() {
        // Initialize walls
        walls = new boolean[width + 1][height + 1];
        for (int i = 0; i <= width; i++) {
            walls[i][0] = true;
            walls[i][height] = true;
        }
        for (int i = 0; i <= height; i++) {
            walls[0][i] = true;
            walls[width][i] = true;
        }
        // Add inner walls
        for (int i = 0; i <= 3; i++) {
            walls[3][i] = true;
            walls[5][i] = true;
        }
        walls[5][4] = true;

        for (int i = 5; i <= 7; i++) {
            walls[i][5] = true;
        }

        for (int i = 5; i <= 9; i++) {
            walls[7][i] = true;
        }
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        for (int i = 0; i < steps; i++) {
            Point newPosition = new Point(currentPosition);
            switch (direction) {
                case "no":
                    newPosition.y++;
                    if (!walls[newPosition.x][newPosition.y] && !walls[newPosition.x + 1][newPosition.y]) {
                        currentPosition = newPosition;
                    } else {
                        i = steps;
                    }
                    break;
                case "ea":
                    newPosition.x++;
                    if (!walls[newPosition.x][newPosition.y] && !walls[newPosition.x][newPosition.y + 1]) {
                        currentPosition = newPosition;
                    } else {
                        i = steps;
                    }
                    break;
                case "so":
                    newPosition.y--;
                    if (!walls[newPosition.x][newPosition.y] && !walls[newPosition.x + 1][newPosition.y]) {
                        currentPosition = newPosition;
                    } else {
                        i = steps;
                    }
                    break;
                case "we":
                    newPosition.x--;
                    if (!walls[newPosition.x][newPosition.y] && !walls[newPosition.x][newPosition.y + 1]) {
                        currentPosition = newPosition;
                    } else {
                        i = steps;
                    }
                    break;
            }
        }

        return String.format("(%d,%d)", currentPosition.x, currentPosition.y);
    }
}