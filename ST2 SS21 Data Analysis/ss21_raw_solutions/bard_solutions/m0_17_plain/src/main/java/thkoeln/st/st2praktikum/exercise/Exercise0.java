package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Moveable {

    private static final int ROWS = 8;
    private static final int COLUMNS = 11;

    private static final List<List<Boolean>> BARRIERS = new ArrayList<>();
    static {
        for (int i = 0; i < ROWS; i++) {
            List<Boolean> row = new ArrayList<>();
            for (int j = 0; j < COLUMNS; j++) {
                row.add(false);
            }
            BARRIERS.add(row);
        }
        BARRIERS.get(1).set(0, true);
        BARRIERS.get(1).set(1, true);
        BARRIERS.get(1).set(2, true);
        BARRIERS.get(1).set(3, true);
        BARRIERS.get(1).set(4, true);
        BARRIERS.get(1).set(5, true);
        BARRIERS.get(1).set(6, true);
        BARRIERS.get(1).set(7, true);
        BARRIERS.get(1).set(8, true);
        BARRIERS.get(2).set(0, true);
        BARRIERS.get(2).set(6, true);
        BARRIERS.get(7).set(0, true);
        BARRIERS.get(7).set(8, true);
    }

    private int x;
    private int y;

    public Exercise0() {
        this.x = 7;
        this.y = 7;
    }

    @Override
    public String move(String moveCommandString) {
        String[] commandParts = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        int newX = x;
        int newY = y;
        switch (direction) {
            case "no":
                newY -= steps;
                break;
            case "ea":
                newX += steps;
                break;
            case "so":
                newY += steps;
                break;
            case "we":
                newX -= steps;
                break;
        }

        while (newX < 0 || newX >= COLUMNS || newY < 0 || newY >= ROWS || BARRIERS.get(newY).get(newX)) {
            steps--;
            if (steps == 0) {
                break;
            }
            if (direction == "no") {
                newY++;
            } else if (direction == "ea") {
                newX++;
            } else if (direction == "so") {
                newY--;
            } else if (direction == "we") {
                newX--;
            }
        }

        if (steps > 0) {
            x = newX;
            y = newY;
        }

        return String.format("(%d,%d)", x, y);
    }
}