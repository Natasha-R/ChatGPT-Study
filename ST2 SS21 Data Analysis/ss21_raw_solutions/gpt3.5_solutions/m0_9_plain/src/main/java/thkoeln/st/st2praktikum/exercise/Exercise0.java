package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements GoAble {

    private int[][] obstacles;
    private int xMax;
    private int yMax;
    private int[] currentPosition;

    public Exercise0() {
        obstacles = new int[][]{{6, 2, 6, 5}, {5, 5, 6, 5}, {5, 5, 5, 6}, {5, 6, 12, 6}};
        xMax = 12;
        yMax = 8;
        currentPosition = new int[]{11, 7};
    }

    @Override
    public String goTo(String goCommandString) {
        int[] newPosition = Arrays.copyOf(currentPosition, currentPosition.length);
        String[] goCommand = goCommandString.substring(1, goCommandString.length() - 1).split(",");
        int steps = Integer.parseInt(goCommand[1]);
        switch (goCommand[0]) {
            case "no":
                newPosition[1] += steps;
                break;
            case "ea":
                newPosition[0] += steps;
                break;
            case "so":
                newPosition[1] -= steps;
                break;
            case "we":
                newPosition[0] -= steps;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + goCommand[0]);
        }
        newPosition = clampToBoundaries(newPosition);
        for (int[] obstacle : obstacles) {
            if (isObstacleInPath(currentPosition, newPosition, obstacle)) {
                return formatPosition(currentPosition);
            }
        }
        currentPosition = newPosition;
        return formatPosition(currentPosition);
    }

    private int[] clampToBoundaries(int[] position) {
        int x = Math.max(0, Math.min(position[0], xMax));
        int y = Math.max(0, Math.min(position[1], yMax));
        return new int[]{x, y};
    }

    private boolean isObstacleInPath(int[] startPosition, int[] endPosition, int[] obstacle) {
        if (obstacle[0] == obstacle[2]) { // vertical obstacle
            int x = obstacle[0];
            int y1 = obstacle[1];
            int y2 = obstacle[3];
            if (startPosition[0] == endPosition[0] && startPosition[0] == x &&
                    Math.max(startPosition[1], endPosition[1]) >= y1 &&
                    Math.min(startPosition[1], endPosition[1]) <= y2) {
                return true;
            }
        } else if (obstacle[1] == obstacle[3]) { // horizontal obstacle
            int y = obstacle[1];
            int x1 = obstacle[0];
            int x2 = obstacle[2];
            if (startPosition[1] == endPosition[1] && startPosition[1] == y &&
                    Math.max(startPosition[0], endPosition[0]) >= x1 &&
                    Math.min(startPosition[0], endPosition[0]) <= x2) {
                return true;
            }
        }
        return false;
    }

    private String formatPosition(int[] position) {
        return String.format("(%d,%d)", position[0], position[1]);
    }

}