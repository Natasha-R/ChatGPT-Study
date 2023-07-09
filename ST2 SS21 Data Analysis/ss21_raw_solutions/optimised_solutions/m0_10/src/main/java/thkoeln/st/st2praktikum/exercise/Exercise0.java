package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Walkable {

    private Point robotLocation;

    public Exercise0() {
        initializeRobotLocation();
        System.out.println("Initialized robot location: " + formatRobotLocationToString(robotLocation));
        createWalls();
        System.out.println("Walls created");
    }

    @Override
    public String walkTo(String walkCommandString) {
        String command = extractCommandFromWalkCommandString(walkCommandString);
        int steps = extractStepsFromWalkCommandString(walkCommandString);

        System.out.println("Walk command received: " + walkCommandString);
        System.out.println("Command: " + command + ", Steps: " + steps);

        for (int i = 0; i < steps; i++) {
            robotLocation = computeNewRobotLocation(command, robotLocation);
            System.out.println("New robot location: " + formatRobotLocationToString(robotLocation));
        }

        return formatRobotLocationToString(robotLocation);
    }

    private void initializeRobotLocation() {
        robotLocation = new Point(3, 0);
    }

    private void createWalls() {
        List<List<Integer>> walls = new ArrayList<>();
        walls.add(defineWall(3,0,3,3));
        walls.add(defineWall(7,0,7,2));
        walls.add(defineWall(4,3,7,3));
        walls.add(defineWall(1,4,8,4));
    }

    private List<Integer> defineWall(int x1, int y1, int x2, int y2) {
        List<Integer> wall = new ArrayList<>();
        wall.add(x1);
        wall.add(y1);
        wall.add(x2);
        wall.add(y2);
        return wall;
    }

    private String extractCommandFromWalkCommandString(String walkCommandString) {
        String command = walkCommandString.substring(1, walkCommandString.length() - 1).split(",")[0];
        System.out.println("Command extracted: " + command);
        return command;
    }

    private int extractStepsFromWalkCommandString(String walkCommandString) {
        int steps = Integer.parseInt(walkCommandString.substring(1, walkCommandString.length() - 1).split(",")[1]);
        System.out.println("Steps extracted: " + steps);
        return steps;
    }

    private Point computeNewRobotLocation(String command, Point currentLocation) {
        int currentX = currentLocation.x;
        int currentY = currentLocation.y;

        System.out.println("Current robot location: " + formatRobotLocationToString(currentLocation));

        switch (command) {
            case "no":
                if (!isWallOrBoundary(currentX, currentY + 1)) currentY++;
                break;
            case "ea":
                if (!isWallOrBoundary(currentX + 1, currentY)) currentX++;
                break;
            case "so":
                if (!isWallOrBoundary(currentX, currentY - 1)) currentY--;
                break;
            case "we":
                if (!isWallOrBoundary(currentX - 1, currentY)) currentX--;
                break;
        }
        return new Point(currentX, currentY);
    }

    private boolean isWallOrBoundary(int x, int y) {
        boolean wallOrBoundary = isBoundary(x, y) || isWall(x, y);
        System.out.println("Checking if location (" + x + "," + y + ") is a wall or boundary: " + wallOrBoundary);
        return wallOrBoundary;
    }

    private boolean isBoundary(int x, int y) {
        boolean boundary = x < 0 || x > 11 || y < 0 || y > 7;
        System.out.println("Checking if location (" + x + "," + y + ") is a boundary: " + boundary);
        return boundary;
    }

    private boolean isWall(int x, int y) {
        List<List<Integer>> walls = new ArrayList<>();
        walls.add(defineWall(3,0,3,3));
        walls.add(defineWall(7,0,7,2));
        walls.add(defineWall(4,3,7,3));
        walls.add(defineWall(1,4,8,4));

        for (List<Integer> wall: walls) {
            if ((wall.get(0) == x && wall.get(1) <= y && y <= wall.get(3)) ||
                    (wall.get(1) == y && wall.get(0) <= x && x <= wall.get(2))) {
                System.out.println("Location (" + x + "," + y + ") is a wall");
                return true;
            }
        }
        System.out.println("Location (" + x + "," + y + ") is not a wall");
        return false;
    }

    private String formatRobotLocationToString(Point robotLocation) {
        return "(" + robotLocation.x + "," + robotLocation.y + ")";
    }
}
