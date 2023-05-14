package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class Exercise0 implements Walkable {
    private static final String NORTH = "no";
    private static final String EAST = "ea";
    private static final String SOUTH = "so";
    private static final String WEST = "we";

    private int machineX;
    private int machineY;
    private int fieldWidth;
    private int fieldHeight;
    private Set<String> walls;

    public Exercise0() {
        initializeField();
        initializeWalls();
    }

    private void initializeField() {
        this.machineX = 0;
        this.machineY = 2;
        this.fieldWidth = 12;
        this.fieldHeight = 9;
    }

    private void initializeWalls() {
        this.walls = new HashSet<>();

        // Adding boundaries as walls
        addBoundaryWalls();

        // Adding specific walls
        addVerticalWall(3, 0, 3);
        addVerticalWall(5, 0, 4);
        addHorizontalWall(4, 5, 7);
        addVerticalWall(7, 5, 8);
    }

    private void addBoundaryWalls() {
        for (int i = 0; i <= fieldWidth; i++) {
            walls.add(i + ",0");
            walls.add(i + "," + fieldHeight);
        }
        for (int i = 0; i <= fieldHeight; i++) {
            walls.add("0," + i);
            walls.add(fieldWidth + "," + i);
        }
    }

    private void addVerticalWall(int x, int startY, int endY) {
        for (int i = startY; i < endY; i++) {
            walls.add(x + "," + (i+1));
        }
    }

    private void addHorizontalWall(int startX, int y, int endX) {
        for (int i = startX; i < endX; i++) {
            walls.add((i+1) + "," + y);
        }
    }

    @Override
    public String walk(String walkCommandString) {
        try {
            String[] commandParts = parseCommand(walkCommandString);
            performWalk(commandParts[0], Integer.parseInt(commandParts[1]));
        } catch (Exception e) {
            System.out.println("Invalid command: " + walkCommandString);
        }

        return getCurrentPosition();
    }

    private String[] parseCommand(String walkCommandString) {
        return walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
    }

    private void performWalk(String direction, int steps) {
        for (int i = 0; i < steps; i++) {
            if (canMoveToNextCell(direction)) {
                moveMachine(direction);
            } else {
                break;
            }
        }
    }

    private boolean canMoveToNextCell(String direction) {
        int nextX = machineX;
        int nextY = machineY;

        switch (direction) {
            case NORTH:
                nextY++;
                break;
            case EAST:
                nextX++;
                break;
            case SOUTH:
                nextY--;
                break;
            case WEST:
                nextX--;
                break;
        }

        return !walls.contains(nextX + "," + nextY);
    }

    private void moveMachine(String direction) {
        switch (direction) {
            case NORTH:
                machineY++;
                break;
            case EAST:
                machineX++;
                break;
            case SOUTH:
                machineY--;
                break;
            case WEST:
                machineX--;
                break;
        }
    }

    private String getCurrentPosition() {
        return "(" + machineX + "," + machineY + ")";

    }
}
