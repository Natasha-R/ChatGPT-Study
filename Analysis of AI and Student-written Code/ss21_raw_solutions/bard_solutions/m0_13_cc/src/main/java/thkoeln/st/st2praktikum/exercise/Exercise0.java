package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;

public class Exercise0 implements Moveable {

    private static final int SPACE_WIDTH = 11;
    private static final int SPACE_HEIGHT = 8;
    private static final int START_X = 7;
    private static final int START_Y = 7;
    private static final int[][] BARRIERS = {{2, 1, 10, 1}, {2, 1, 2, 6}, {2, 6, 7, 6}, {10, 1, 10, 8}};

    private int x;
    private int y;

    public Exercise0() {
        this.x = START_X;
        this.y = START_Y;
    }

    @Override
    public String move(String moveCommandString) {
        // Parse the move command string.
        String[] parts = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        // Check if the direction is valid.
        if (!isValidDirection(direction)) {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Check if the steps are valid.
        if (steps < 1) {
            throw new IllegalArgumentException("Invalid steps: " + steps);
        }

        // Move the cleaning device.
        for (int i = 0; i < steps; i++) {
            // Check if the cleaning device will hit a barrier.
            if (isBarrier(x, y + (direction.equals("no") ? -1 : direction.equals("so") ? 1 : 0))) {
                break;
            }

            // Move the cleaning device.
            if (direction.equals("no")) {
                y--;
            } else if (direction.equals("ea")) {
                x++;
            } else if (direction.equals("so")) {
                y++;
            } else if (direction.equals("we")) {
                x--;
            }
        }

        // Return the new coordinates of the cleaning device.
        return "(" + x + ", " + y + ")";
    }

    // Check if the given direction is valid.
    private boolean isValidDirection(String direction) {
        return Arrays.asList("no", "ea", "so", "we").contains(direction);
    }

    // Check if the given coordinates are within the bounds of the space.
    private boolean isWithinBounds(int x, int y) {
        return 0 <= x && x < SPACE_WIDTH && 0 <= y && y < SPACE_HEIGHT;
    }

    // Check if the given coordinates are within a barrier.
    private boolean isBarrier(int x, int y) {
        for (int[] barrier : BARRIERS) {
            if (isWithinBounds(x, y) && x >= barrier[0] && x <= barrier[2] && y >= barrier[1] && y <= barrier[3]) {
                return true;
            }
        }
        return false;
    }
}