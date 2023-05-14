package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements GoAble {

    private static final int GRID_WIDTH = 12;
    private static final int GRID_HEIGHT = 8;
    private static final int[][] OBSTACLES = {{6, 2, 6, 5}, {5, 5, 6, 5}, {5, 5, 5, 6}, {5, 6, 12, 6}};
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private int[] position = {11, 7};

    public String goTo(String goCommandString) throws IllegalArgumentException {
        int[] newPosition = Arrays.copyOf(position, position.length);
        String[] goCommand = goCommandString.replaceAll("[^A-Za-z0-9]", "").split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

        if (goCommand.length != 2) {
            throw new IllegalArgumentException("Invalid command: " + goCommandString);
        }

        int steps = Integer.parseInt(goCommand[1]);
        int directionIndex = getDirectionIndex(goCommand[0]);

        while (steps > 0) {
            int[] nextPosition = Arrays.copyOf(newPosition, newPosition.length);
            nextPosition[0] += DIRECTIONS[directionIndex][0];
            nextPosition[1] += DIRECTIONS[directionIndex][1];

            if (isOutOfBounds(nextPosition) || collidesWithObstacle(newPosition, nextPosition)) {
                break;
            }

            newPosition = nextPosition;
            steps--;
        }

        position = newPosition;
        return "(" + position[0] + "," + position[1] + ")";
    }

    private boolean isOutOfBounds(int[] position) {
        return position[0] < 0 || position[0] >= GRID_WIDTH || position[1] < 0 || position[1] >= GRID_HEIGHT;
    }

    private boolean collidesWithObstacle(int[] currentPosition, int[] nextPosition) {
        for (int[] obstacle : OBSTACLES) {
            if (currentPosition[0] == nextPosition[0] && currentPosition[0] == obstacle[0]
                    && obstacle[1] <= Math.max(currentPosition[1], nextPosition[1]) && obstacle[3] >= Math.min(currentPosition[1], nextPosition[1])) {
                return true;
            }
            if (currentPosition[1] == nextPosition[1] && currentPosition[1] == obstacle[1]
                    && obstacle[0] <= Math.max(currentPosition[0], nextPosition[0]) && obstacle[2] >= Math.min(currentPosition[0], nextPosition[0])) {
                return true;
            }
        }
        return false;
    }

    private int getDirectionIndex(String direction) throws IllegalArgumentException {
        List<String> directions = Arrays.asList("no", "ea", "so", "we");
        int directionIndex = directions.indexOf(direction.toLowerCase());
        if (directionIndex == -1) {
            throw new IllegalArgumentException("Invalid direction: " + direction);
        }
        return directionIndex;
    }
}
