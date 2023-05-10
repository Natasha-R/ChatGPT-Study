package thkoeln.st.st2praktikum.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {

    private static final int FIELD_WIDTH = 12;
    private static final int FIELD_HEIGHT = 9;

    private int minerPositionX = 0;
    private int minerPositionY = 2;
    private final boolean[][] wallMatrix = new boolean[FIELD_WIDTH][FIELD_HEIGHT];

    public Exercise0() {
        initializeWalls();
    }

    private void initializeWalls() {
        setWall(3, 0, 3);
        setWall(5, 0, 4);
        setWall(4, 5, 5);
        setWall(7, 5, 9);
    }

    private void setWall(int x, int yStart, int yEnd) {
        for (int y = yStart; y < yEnd; y++) {
            wallMatrix[x][y] = true;
        }
    }

    @Override
    public String walk(String walkCommandString) {
        Pattern pattern = Pattern.compile("\\[(\\w+),(\\d+)\\]");
        Matcher matcher = pattern.matcher(walkCommandString);

        if (matcher.matches()) {
            String direction = matcher.group(1);
            int steps = Integer.parseInt(matcher.group(2));
            moveMiner(direction, steps);
        }

        return formatMinerPosition();
    }

    private void moveMiner(String direction, int steps) {
        switch (direction) {
            case "no":
                moveMinerNorth(steps);
                break;
            case "so":
                moveMinerSouth(steps);
                break;
            case "ea":
                moveMinerEast(steps);
                break;
            case "we":
                moveMinerWest(steps);
                break;
        }
    }

    private void moveMinerNorth(int steps) {
        for (int i = 0; i < steps && minerPositionY + 1 < FIELD_HEIGHT && !wallMatrix[minerPositionX][minerPositionY + 1]; i++) {
            minerPositionY++;
        }
    }

    private void moveMinerSouth(int steps) {
        for (int i = 0; i < steps && minerPositionY - 1 >= 0 && !wallMatrix[minerPositionX][minerPositionY - 1]; i++) {
            minerPositionY--;
        }
    }

    private void moveMinerEast(int steps) {
        for (int i = 0; i < steps && minerPositionX + 1 < FIELD_WIDTH && !wallMatrix[minerPositionX + 1][minerPositionY]; i++) {
            minerPositionX++;
        }
    }

    private void moveMinerWest(int steps) {
        for (int i = 0; i < steps && minerPositionX - 1 >= 0 && !wallMatrix[minerPositionX - 1][minerPositionY]; i++) {
            minerPositionX--;
        }
    }

    private String formatMinerPosition() {
        return "(" + minerPositionX + "," + minerPositionY + ")";
    }
}
