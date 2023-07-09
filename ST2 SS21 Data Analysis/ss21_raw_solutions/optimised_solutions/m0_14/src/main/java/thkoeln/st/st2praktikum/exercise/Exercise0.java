package thkoeln.st.st2praktikum.exercise;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {
    private int x, y;  // current position
    private int MAX_X = 12, MAX_Y = 8;  // room size
    private static final Pattern COMMAND_PATTERN = Pattern.compile("\\[(\\w+),(\\d+)\\]");
    private Set<String> walls;

    public Exercise0() {
        // Initialize starting position
        x = 3;
        y = 0;

        // Initialize walls
        walls = new HashSet<>();
        addWalls();
    }

    // Implement the walkTo method from the Walkable interface
    @Override
    public String walkTo(String walkCommandString) {
        Matcher matcher = COMMAND_PATTERN.matcher(walkCommandString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid walk command: " + walkCommandString);
        }

        String direction = matcher.group(1);
        int steps = Integer.parseInt(matcher.group(2));

        switch (direction) {
            case "no":
                walkNorth(steps);
                break;
            case "ea":
                walkEast(steps);
                break;
            case "so":
                walkSouth(steps);
                break;
            case "we":
                walkWest(steps);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        return "(" + x + "," + y + ")";
    }

    // New method to change room size
    public void changeRoomSize(int maxX, int maxY) {
        if (maxX < 1 || maxY < 1) {
            throw new IllegalArgumentException("Room size must be at least 1x1");
        }
        MAX_X = maxX;
        MAX_Y = maxY;
    }

    // New method to add a wall at a given position
    public void addWall(int x, int y) {
        if (x < 0 || x >= MAX_X || y < 0 || y >= MAX_Y) {
            throw new IllegalArgumentException("Wall position out of bounds");
        }
        walls.add("(" + x + "," + y + ")");
    }

    // New methods for complex movements

    public void moveInSquare(int size) {
        walkEast(size);
        walkNorth(size);
        walkWest(size);
        walkSouth(size);
    }

    public void moveInZigZag(int size) {
        for (int i = 0; i < size; i++) {
            walkEast(1);
            walkNorth(1);
            walkEast(1);
            walkSouth(1);
        }
    }

    // Helper methods to walk in different directions
    private void walkNorth(int steps) {
        while (steps-- > 0 && y < MAX_Y - 1 && !isWall(x, y + 1)) {
            y++;
        }
    }

    private void walkEast(int steps) {
        while (steps-- > 0 && x < MAX_X - 1 && !isWall(x + 1, y)) {
            x++;
        }
    }

    private void walkSouth(int steps) {
        while (steps-- > 0 && y > 0 && !isWall(x, y - 1)) {
            y--;
        }
    }

    private void walkWest(int steps) {
        while (steps-- > 0 && x > 0 && !isWall(x - 1, y)) {
            x--;
        }
    }

    // Method to check if a cell is a wall
    private boolean isWall(int x, int y) {
        return walls.contains("(" + x + "," + y + ")");
    }

    // Method to add walls
    private void addWalls() {
        addWall(3, 0, 3, 3);
        addWall(7, 0, 7, 2);
        addWall(4, 3, 7, 3);
        addWall(1, 4, 8, 4);
        // Add boundary walls
        addWall(0, 0, 0, MAX_Y - 1);
        addWall(0, 0, MAX_X - 1, 0);
        addWall(MAX_X - 1, 0, MAX_X - 1, MAX_Y - 1);
        addWall(0, MAX_Y - 1, MAX_X - 1, MAX_Y - 1);
    }

    // Helper method to add a wall
    private void addWall(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {  // vertical wall
            for (int y = y1; y <= y2; y++) {
                walls.add("(" + x1 + "," + y + ")");
            }
        } else {  // horizontal wall
            for (int x = x1; x <= x2; x++) {
                walls.add("(" + x + "," + y1 + ")");
            }
        }
    }
}


