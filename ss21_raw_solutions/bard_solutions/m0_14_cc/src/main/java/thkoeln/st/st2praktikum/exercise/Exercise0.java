package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private static final int WIDTH = 11;
    private static final int HEIGHT = 8;
    private static final int START_X = 7;
    private static final int START_Y = 7;

    private static final int[][] BARRIERS = {
            {2, 1, 10, 1},
            {2, 1, 2, 6},
            {2, 6, 7, 6},
            {10, 1, 10, 8}
    };

    private int x;
    private int y;

    public Exercise0() {
        this.x = START_X;
        this.y = START_Y;
    }

    @Override
    public String move(String moveCommandString) {
        // Parse the move command string.
        String[] tokens = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = tokens[0];
        int steps = Integer.parseInt(tokens[1]);

        // Check if the direction is valid.
        if (!isValidDirection(direction)) {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Move the cleaning device in the specified direction.
        for (int i = 0; i < steps; i++) {
            if (!moveInDirection(direction)) {
                break;
            }
        }

        // Return the new coordinates of the cleaning device.
        return "(" + x + "," + y + ")";
    }

    private boolean moveInDirection(String direction) {
        switch (direction) {
            case "no":
                if (isBarrier(x, y - 1)) {
                    return false;
                }
                y--;
                break;
            case "ea":
                if (isBarrier(x + 1, y)) {
                    return false;
                }
                x++;
                break;
            case "so":
                if (isBarrier(x, y + 1)) {
                    return false;
                }
                y++;
                break;
            case "we":
                if (isBarrier(x - 1, y)) {
                    return false;
                }
                x--;
                break;
            default:
                return false;
        }

        return true;
    }

    private boolean isValidDirection(String direction) {
        return direction.equals("no") || direction.equals("ea") || direction.equals("so") || direction.equals("we");
    }

    private boolean isBarrier(int x, int y) {
        for (int[] barrier : BARRIERS) {
            if (x >= barrier[0] && x <= barrier[2] && y >= barrier[1] && y <= barrier[3]) {
                return true;
            }
        }

        return false;
    }
}