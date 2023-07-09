package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private static final String INVALID_DIRECTION_MESSAGE = "Invalid direction: ";
    private static final String INVALID_STEPS_MESSAGE = "Invalid number of steps: ";

    private int x = 7;
    private int y = 7;
    private int width = 11;
    private int height = 8;
    private String[] barriers = {"(2,1)-(10,1)", "(2,1)-(2,6)", "(2,6)-(7,6)", "(10,1)-(10,8)"};

    public String move(String moveCommandString) {
        // Check if the command string is valid.
        if (!moveCommandString.startsWith("[") || !moveCommandString.endsWith("]")) {
            throw new IllegalArgumentException("Invalid move command string: " + moveCommandString);
        }

        // Parse the command string into its components.
        String[] parts = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        // Check if the direction is valid.
        if (!isValidDirection(direction)) {
            throw new IllegalArgumentException(INVALID_DIRECTION_MESSAGE + direction);
        }

        // Check if the number of steps is valid.
        if (steps < 1) {
            throw new IllegalArgumentException(INVALID_STEPS_MESSAGE + steps);
        }

        // Move the cleaning device.
        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case "no":
                    y--;
                    break;
                case "ea":
                    x++;
                    break;
                case "so":
                    y++;
                    break;
                case "we":
                    x--;
                    break;
                default:
                    throw new IllegalStateException("Unknown direction: " + direction);
            }

            // Check if the cleaning device has hit a barrier.
            if (isBarrier(x, y)) {
                break;
            }
        }

        // Return the new coordinates of the cleaning device.
        return "(" + x + "," + y + ")";
    }

    private boolean isValidDirection(String direction) {
        return direction.equals("no") || direction.equals("ea") || direction.equals("so") || direction.equals("we");
    }

    private boolean isBarrier(int x, int y) {
        for (String barrier : barriers) {
            String[] coords = barrier.split("-");
            int x1 = Integer.parseInt(coords[0].substring(1, coords[0].length() - 1));
            int y1 = Integer.parseInt(coords[1].substring(1, coords[1].length() - 1));
            int x2 = Integer.parseInt(coords[2].substring(1, coords[2].length() - 1));
            int y2 = Integer.parseInt(coords[3].substring(1, coords[3].length() - 1));

            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                return true;
            }
        }

        return false;
    }
}