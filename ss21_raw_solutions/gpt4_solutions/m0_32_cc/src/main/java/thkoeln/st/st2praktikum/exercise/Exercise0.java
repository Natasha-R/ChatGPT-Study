package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    private static final int FIELD_WIDTH = 12;
    private static final int FIELD_HEIGHT = 9;

    private static final String NORTH = "no";
    private static final String EAST = "ea";
    private static final String SOUTH = "so";
    private static final String WEST = "we";

    private Point miningMachinePosition;
    private final int[][] wallCoordinates;

    public Exercise0() {
        miningMachinePosition = new Point(0, 2);
        wallCoordinates = new int[][] {
                {3, 0, 3, 3},
                {5, 0, 5, 4},
                {4, 5, 7, 5},
                {7, 5, 7, 9}
        };
    }

    @Override
    public String walk(String walkCommandString) {
        List<String> commandParts = parseCommand(walkCommandString);
        String direction = commandParts.get(0);
        int steps = Integer.parseInt(commandParts.get(1));

        for (int i = 0; i < steps; i++) {
            if (!attemptMoveInDirection(direction)) {
                break;
            }
        }

        return formatPosition(miningMachinePosition);
    }

    private List<String> parseCommand(String command) {
        return Arrays.asList(command.replaceAll("[\\[\\]]", "").split(","));
    }

    private boolean attemptMoveInDirection(String direction) {
        switch (direction) {
            case NORTH:
                return moveIfPossible(0, 1);
            case EAST:
                return moveIfPossible(1, 0);
            case SOUTH:
                return moveIfPossible(0, -1);
            case WEST:
                return moveIfPossible(-1, 0);
        }
        throw new IllegalArgumentException("Invalid direction: " + direction);
    }

    private boolean moveIfPossible(int dx, int dy) {
        int newX = miningMachinePosition.x + dx;
        int newY = miningMachinePosition.y + dy;

        if (isMovePossible(newX, newY)) {
            miningMachinePosition.move(newX, newY);
            return true;
        }
        return false;
    }

    private boolean isMovePossible(int x, int y) {
        return isWithinFieldBounds(x, y) && !isWall(x, y);
    }

    private boolean isWithinFieldBounds(int x, int y) {
        return x >= 0 && x < FIELD_WIDTH && y >= 0 && y < FIELD_HEIGHT;
    }

    private boolean isWall(int x, int y) {
        for (int[] wall : wallCoordinates) {
            if ((x == wall[0] && y >= wall[1] && y < wall[3]) || (y == wall[1] && x >= wall[0] && x < wall[2])) {
                return true;
            }
        }
        return false;
    }

    private String formatPosition(Point position) {
        return "(" + position.x + "," + position.y + ")";
    }
}
