package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Moveable {

    private static final String DIRECTION_NO = "no";
    private static final String DIRECTION_EA = "ea";
    private static final String DIRECTION_SO = "so";
    private static final String DIRECTION_WE = "we";

    private int x = 7;
    private int y = 7;
    private int width = 11;
    private int height = 8;
    private List<Barrier> barriers = Arrays.asList(
            new Barrier(2, 1, 10, 1),
            new Barrier(2, 1, 2, 6),
            new Barrier(2, 6, 7, 6),
            new Barrier(10, 1, 10, 8)
    );

    public String move(String moveCommandString) {
        String[] parts = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        try {
            switch (direction) {
                case DIRECTION_NO:
                    for (int i = 0; i < steps; i++) {
                        if (y - 1 < 0 || isBarrier(x, y - 1)) {
                            break;
                        }
                        y--;
                    }
                    break;
                case DIRECTION_EA:
                    for (int i = 0; i < steps; i++) {
                        if (x + 1 >= width || isBarrier(x + 1, y)) {
                            break;
                        }
                        x++;
                    }
                    break;
                case DIRECTION_SO:
                    for (int i = 0; i < steps; i++) {
                        if (y + 1 >= height || isBarrier(x, y + 1)) {
                            break;
                        }
                        y++;
                    }
                    break;
                case DIRECTION_WE:
                    for (int i = 0; i < steps; i++) {
                        if (x - 1 < 0 || isBarrier(x - 1, y)) {
                            break;
                        }
                        x--;
                    }
                    break;
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid move command", e);
        }

        return "(" + x + "," + y + ")";
    }

    private boolean isBarrier(int x, int y) {
        for (Barrier barrier : barriers) {
            if (barrier.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    private static class Barrier {
        private int x1;
        private int y1;
        private int x2;
        private int y2;

        public Barrier(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public boolean contains(int x, int y) {
            return x >= x1 && x <= x2 && y >= y1 && y <= y2;
        }
    }
}