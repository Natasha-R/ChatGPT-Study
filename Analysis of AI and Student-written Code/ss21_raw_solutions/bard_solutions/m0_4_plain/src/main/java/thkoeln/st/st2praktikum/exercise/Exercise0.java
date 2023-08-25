package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Moveable {

    private static final int START_X = 7;
    private static final int START_Y = 7;
    private static final int WIDTH = 11;
    private static final int HEIGHT = 8;
    private static final List<Barrier> BARRIERS = Arrays.asList(
            new Barrier(0, 1, 10, 1),
            new Barrier(0, 1, 2, 6),
            new Barrier(2, 6, 7, 6),
            new Barrier(10, 1, 10, 8)
    );

    private int x;
    private int y;
    private int width;
    private int height;
    private List<Barrier> barriers;

    public Exercise0() {
        this(START_X, START_Y, WIDTH, HEIGHT, BARRIERS);
    }

    public Exercise0(int x, int y, int width, int height, List<Barrier> barriers) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.barriers = barriers;
    }

    @Override
    public String move(String moveCommandString) {
        String[] parts = moveCommandString.split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        if (direction.equals("no")) {
            y -= steps;
        } else if (direction.equals("ea")) {
            x += steps;
        } else if (direction.equals("so")) {
            y += steps;
        } else if (direction.equals("we")) {
            x -= steps;
        }

        if (x < 0) {
            x = 0;
        } else if (x >= width) {
            x = width - 1;
        }

        if (y < 0) {
            y = 0;
        } else if (y >= height) {
            y = height - 1;
        }

        for (Barrier barrier : barriers) {
            if (barrier.intersects(x, y)) {
                x = barrier.getX();
                y = barrier.getY();
            }
        }

        return "(" + x + "," + y + ")";
    }
}