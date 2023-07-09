package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class Exercise0 implements Walkable {

    // Starting position
    private int currentXPosition = 0;
    private int currentYPosition = 2;

    // Field dimensions
    private static final int MAX_FIELD_WIDTH = 12;
    private static final int MAX_FIELD_HEIGHT = 9;

    // Walls
    private static final List<int[]> WALL_COORDINATES = Arrays.asList(
            new int[]{3, 0, 3, 3},
            new int[]{5, 0, 5, 4},
            new int[]{4, 5, 7, 5},
            new int[]{7, 5, 7, 9}
    );

    @Override
    public String walk(String walkCommandString) {
        try {
            Command command = parseCommand(walkCommandString);
            executeCommand(command);
        } catch (Exception e) {
            System.err.println("Invalid command: " + e.getMessage());
        }
        return getCurrentPositionAsString();
    }

    private Command parseCommand(String commandString) throws Exception {
        String direction = commandString.substring(1, 3);
        int steps = Integer.parseInt(commandString.substring(4, commandString.length() - 1));
        return new Command(direction, steps);
    }

    private void executeCommand(Command command) {
        for (int i = 0; i < command.steps; i++) {
            if (canMoveToNextPosition(command.direction)) {
                updateCurrentPosition(command.direction);
            } else {
                break;
            }
        }
    }

    private boolean canMoveToNextPosition(String direction) {
        int nextXPosition = getNextXPosition(direction);
        int nextYPosition = getNextYPosition(direction);
        return !hasWallAtPosition(nextXPosition, nextYPosition) && isPositionWithinField(nextXPosition, nextYPosition);
    }

    private int getNextXPosition(String direction) {
        return direction.equals("ea") ? currentXPosition + 1 : direction.equals("we") ? currentXPosition - 1 : currentXPosition;
    }

    private int getNextYPosition(String direction) {
        return direction.equals("no") ? currentYPosition + 1 : direction.equals("so") ? currentYPosition - 1 : currentYPosition;
    }

    private void updateCurrentPosition(String direction) {
        currentXPosition = getNextXPosition(direction);
        currentYPosition = getNextYPosition(direction);
    }

    private boolean hasWallAtPosition(int x, int y) {
        return WALL_COORDINATES.stream().anyMatch(wall -> isPositionWithinWall(x, y, wall));
    }

    private boolean isPositionWithinWall(int x, int y, int[] wall) {
        return x == wall[0] && y >= wall[1] && y <= wall[3];
    }

    private boolean isPositionWithinField(int x, int y) {
        return x >= 0 && x < MAX_FIELD_WIDTH && y >= 0 && y < MAX_FIELD_HEIGHT;
    }

    private String getCurrentPositionAsString() {
        return String.format("(%d,%d)", currentXPosition, currentYPosition);
    }

    private class Command {
        String direction;
        int steps;

        Command(String direction, int steps) {
            this.direction = direction;
            this.steps = steps;
        }
    }
}
