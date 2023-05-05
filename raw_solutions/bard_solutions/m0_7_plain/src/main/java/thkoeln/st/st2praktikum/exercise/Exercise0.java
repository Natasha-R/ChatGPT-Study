package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private static final int WIDTH = 11;
    private static final int HEIGHT = 8;
    private static final int START_X = 7;
    private static final int START_Y = 7;
    private static final int[][] BARRIERS = {{2, 1}, {10, 1}, {2, 6}, {7, 6}, {10, 8}};

    private int x;
    private int y;

    public Exercise0() {
        this.x = START_X;
        this.y = START_Y;
    }

    @Override
    public String move(String moveCommandString) {
        String[] parts = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        for (int i = 0; i < steps; i++) {
            if (direction.equals("no")) {
                if (y > 0 && y - i < HEIGHT - 1 && !isBarrier(x, y - 1)) {
                    y--;
                } else {
                    break;
                }
            } else if (direction.equals("ea")) {
                if (x < WIDTH - 1 && x + i < WIDTH - 1 && !isBarrier(x + 1, y)) {
                    x++;
                } else {
                    break;
                }
            } else if (direction.equals("so")) {
                if (y < HEIGHT - 1 && y + i < HEIGHT - 1 && !isBarrier(x, y + 1)) {
                    y++;
                } else {
                    break;
                }
            } else if (direction.equals("we")) {
                if (x > 0 && x - i > 0 && !isBarrier(x - 1, y)) {
                    x--;
                } else {
                    break;
                }
            }
        }

        return "(" + x + "," + y + ")";
    }

    private boolean isBarrier(int x, int y) {
        for (int[] barrier : BARRIERS) {
            if (x == barrier[0] && y == barrier[1]) {
                return true;
            }
        }
        return false;
    }
}