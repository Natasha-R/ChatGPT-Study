package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {
    private int xCoordinate = 3;
    private int yCoordinate = 0;
    private final List<int[]> walls = Arrays.asList(new int[][]{{3,0},{3,1},{3,2},{3,3},{4,3},{5,3},{6,3},{7,3},{7,2},{7,1},{7,0},{1,4},{2,4},{3,4},{4,4},{5,4},{6,4},{7,4}});
    private final int width = 12;
    private final int height = 8;

    public String walkTo(String walkCommandString) throws IllegalArgumentException {
        String[] command = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = command[0];
        int steps = Integer.parseInt(command[1]);
        switch (direction) {
            case "no":
                moveNorth(steps);
                break;
            case "ea":
                moveEast(steps);
                break;
            case "so":
                moveSouth(steps);
                break;
            case "we":
                moveWest(steps);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
        return "(" + xCoordinate + "," + yCoordinate + ")";
    }

    private void moveNorth(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (yCoordinate + i >= height || isWall(xCoordinate,yCoordinate+i)) {
                yCoordinate += i-1;
                break;
            }
            yCoordinate += 1;
        }
    }

    private void moveEast(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (xCoordinate + i >= width || isWall(xCoordinate+i,yCoordinate)) {
                xCoordinate += i-1;
                break;
            }
            xCoordinate += 1;
        }
    }

    private void moveSouth(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (yCoordinate - i < 0 || isWall(xCoordinate,yCoordinate-i)) {
                yCoordinate -= i-1;
                break;
            }
            yCoordinate -= 1;
        }
    }

    private void moveWest(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (xCoordinate - i < 0 || isWall(xCoordinate-i,yCoordinate)) {
                xCoordinate -= i-1;
                break;
            }
            xCoordinate -= 1;
        }
    }

    private boolean isWall(int x,int y) {
        for (int[] wall : walls) {
            if (wall[0] == x && wall[1] == y) return true;
        }
        return false;
    }
}