package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class Exercise0 implements Walkable {
    private final int WIDTH = 12; // Size of room.
    private final int HEIGHT = 8;

    private int robotX = 3; // Initial robot position.
    private int robotY = 0;

    private final Set<String> walls = new HashSet<>();

    public Exercise0() {
        // Initialize walls.
        initWalls();
        System.out.println("Walls initialized: " + walls);  // 1. Print statement
    }

    // Helper method to initialize walls in the room.
    private void initWalls() {
        addWall(3, 0, 3, 3);
        addWall(7, 0, 7, 2);
        addWall(4, 3, 7, 3);
        addWall(1, 4, 8, 4);
    }

    // Helper method to add a wall to the room.
    private void addWall(int startX, int startY, int endX, int endY) {
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                walls.add(buildPosition(i, j));
            }
        }
    }

    // Helper method to build a position string.
    private String buildPosition(int x, int y) {
        return x + "," + y;
    }

    @Override
    public String walkTo(String walkCommandString) {
        String direction = parseDirection(walkCommandString);
        int steps = parseSteps(walkCommandString);

        System.out.println("Parsed Direction: " + direction + ", Steps: " + steps);  // 2. Print statement

        for (int i = 0; i < steps; i++) {
            if (!canMove(direction)) {
                System.out.println("Invalid move or hit a wall at direction: " + direction);  // 4. Print statement
                break;
            }

            System.out.println("Before Move: Position = " + buildPosition(robotX, robotY));  // 3. Print statement
            move(direction);
            System.out.println("After Move: Position = " + buildPosition(robotX, robotY));   // 3. Print statement
        }

        return buildPosition(robotX, robotY);
    }

    // Helper method to parse the direction from a command string.
    private String parseDirection(String command) {
        return command.replaceAll("\\[|\\]", "").split(",")[0];
    }

    // Helper method to parse the number of steps from a command string.
    private int parseSteps(String command) {
        return Integer.parseInt(command.replaceAll("\\[|\\]", "").split(",")[1]);
    }

    // Helper method to check if the robot can move in a given direction.
    private boolean canMove(String direction) {
        switch (direction) {
            case "no":
                return robotY < HEIGHT - 1 && !walls.contains(buildPosition(robotX, robotY + 1));
            case "ea":
                return robotX < WIDTH - 1 && !walls.contains(buildPosition(robotX + 1, robotY));
            case "so":
                return robotY > 0 && !walls.contains(buildPosition(robotX, robotY - 1));
            case "we":
                return robotX > 0 && !walls.contains(buildPosition(robotX - 1, robotY));
            default:
                return false;
        }
    }

    // Helper method to move the robot in a given direction.
    private void move(String direction) {
        switch (direction) {
            case "no":
                robotY++;
                break;
            case "ea":
                robotX++;
                break;
            case "so":
                robotY--;
                break;
            case "we":
                robotX--;
                break;
        }
    }
}


