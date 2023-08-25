package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;

public class Exercise0 implements Walkable {
    private final Robot robot;

    public Exercise0() {
        boolean[][] room = new boolean[12][8];
        for (int i = 0; i <= 11; i++) {
            for (int j = 0; j <= 7; j++) {
                room[i][j] = true;
            }
        }

        for (int i = 0; i <= 3; i++) {
            room[3][i] = false;
        }

        for (int i = 0; i <= 2; i++) {
            room[7][i] = false;
        }

        for (int i = 4; i <= 7; i++) {
            room[i][3] = false;
        }

        for (int i = 1; i <= 8; i++) {
            room[i][4] = false;
        }

        robot = new Robot(new Cell(3, 0), room);
    }

    @Override
    public String walkTo(String walkCommandString) {
        return robot.walk(walkCommandString).toString();
    }
}

