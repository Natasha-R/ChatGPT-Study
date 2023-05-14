package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private static final int START_X = 7;
    private static final int START_Y = 7;
    private static final int WIDTH = 11;
    private static final int HEIGHT = 8;
    private static final int[][] BARRIERS = {{2, 1}, {2, 6}, {7, 6}, {10, 1}, {10, 8}};

    private int x;
    private int y;

    public Exercise0() {
        this.x = START_X;
        this.y = START_Y;
    }

    @Override
    public String move(String moveCommandString) {
        String[] tokens = moveCommandString.replaceAll("\\[|\\]", "").split(",");
        String direction = tokens[0];
        int steps = Integer.parseInt(tokens[1]);

        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case "no":
                    if (y - 1 >= 0 && !isBarrier(x, y - 1)) {
                        y--;
                    } else {
                        break;
                    }
                    break;
                case "ea":
                    if (x + 1 < WIDTH && !isBarrier(x + 1, y)) {
                        x++;
                    } else {
                        break;
                    }
                    break;
                case "so":
                    if (y + 1 < HEIGHT && !isBarrier(x, y + 1)) {
                        y++;
                    } else {
                        break;
                    }
                    break;
                case "we":
                    if (x - 1 >= 0 && !isBarrier(x - 1, y)) {
                        x--;
                    } else {
                        break;
                    }
                    break;
            }
        }

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