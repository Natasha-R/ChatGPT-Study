package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private static final int START_X = 7;
    private static final int START_Y = 7;
    private static final int WIDTH = 11;
    private static final int HEIGHT = 8;
    private static final int[][] BARRIERS = {{2, 1}, {10, 1}, {2, 6}, {7, 6}, {10, 8}};

    private int x = START_X;
    private int y = START_Y;

    public Exercise0() {
        // Nothing here
    }

    @Override
    public String move(String moveCommandString) {
        // Parse the command string
        String[] tokens = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = tokens[0];
        int steps = Integer.parseInt(tokens[1]);

        // Move the device
        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (y - 1 < 0 || isBarrier(x, y - 1)) {
                        break;
                    }
                    y--;
                }
                if (y < 0) {
                    y = 0;
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (x + 1 >= WIDTH || isBarrier(x + 1, y)) {
                        break;
                    }
                    x++;
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (y + 1 >= HEIGHT || isBarrier(x, y + 1)) {
                        break;
                    }
                    y++;
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (x - 1 < 0 || isBarrier(x - 1, y)) {
                        break;
                    }
                    x--;
                }
                break;
        }

        // Return the new location of the device
        return "(" + x + "," + y + ")";
    }

    private boolean isBarrier(int x, int y) {
        for (int[] barrier : BARRIERS) {
            if (barrier[0] == x && barrier[1] == y) {
                return true;
            }
        }
        return false;
    }
}