package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements GoAble {
    private int x = 4;
    private int y = 0;
    private List<Barrier> barriers = new ArrayList<Barrier>();
    private int fieldWidth = 11;
    private int fieldHeight = 8;

    public Exercise0() {
        // Add the barriers to the list
        barriers.add(new Barrier("(1,0)-(1,6)"));
        barriers.add(new Barrier("(1,6)-(4,6)"));
        barriers.add(new Barrier("(5,6)-(7,6)"));
        barriers.add(new Barrier("(7,1)-(7,6)"));
    }

    @Override
    public String go(String goCommandString) {
        String[] goCommand = goCommandString.replaceAll("[\\[\\]]", "").split(",");
        String direction = goCommand[0];
        int steps = Integer.parseInt(goCommand[1]);

        for (int i = 0; i < steps; i++) {
            int nextX = x;
            int nextY = y;

            switch (direction) {
                case "no":
                    nextY += 1;
                    break;
                case "so":
                    nextY -= 1;
                    break;
                case "we":
                    nextX -= 1;
                    break;
                case "ea":
                    nextX += 1;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid direction String");
            }

            // Check if the mining machine goes out of bounds
            if (nextX < 0 || nextX >= fieldWidth || nextY < 0 || nextY >= fieldHeight) {
                // Mining machine goes out of bounds, stay at current position
                return "(" + x + "," + y + ")";
            }

            // Check if the next step hits a barrier
            for (Barrier barrier : barriers) {
                if (barrier.willHitBarrier(x, y, direction)) {
                    // Barrier will be hit, stay at current position
                    return "(" + x + "," + y + ")";
                }
            }

            // Move to the next position
            x = nextX;
            y = nextY;

        }

        return "(" + x + "," + y + ")";
    }

}