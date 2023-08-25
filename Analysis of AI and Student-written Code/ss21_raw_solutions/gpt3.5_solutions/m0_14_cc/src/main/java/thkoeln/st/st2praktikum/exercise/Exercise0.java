package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements GoAble {

    private final int SPACESHIP_WIDTH = 12;
    private final int SPACESHIP_HEIGHT = 8;

    private final List<int[]> OBSTACLES = Arrays.asList(
            new int[]{6, 2, 6, 5},
            new int[]{5, 5, 6, 5},
            new int[]{5, 5, 5, 6},
            new int[]{5, 6, 12, 6}
    );

    private int[] droidPosition = new int[]{11, 7};

    @Override
    public String goTo(String goCommandString) throws IllegalArgumentException {
        int[] newDroidPosition = Arrays.copyOf(droidPosition, droidPosition.length);
        String[] goCommand = parseCommand(goCommandString);

        int steps = Integer.parseInt(goCommand[1]);

        switch (goCommand[0]) {
            case "no":
                newDroidPosition[1] += steps;
                break;
            case "ea":
                newDroidPosition[0] += steps;
                break;
            case "so":
                newDroidPosition[1] -= steps;
                break;
            case "we":
                newDroidPosition[0] -= steps;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + goCommand[0]);
        }

        if (isPositionOutOfBounds(newDroidPosition)) {
            throw new IllegalArgumentException("New position is out of bounds: " + Arrays.toString(newDroidPosition));
        }

        if (doesIntersectObstacle(newDroidPosition)) {
            return formatCoordinateString(droidPosition);
        } else {
            droidPosition = newDroidPosition;
            return formatCoordinateString(droidPosition);
        }
    }

    private boolean doesIntersectObstacle(int[] position) {
        for (int[] obstacle : OBSTACLES) {
            if (doesOverlap(obstacle, position)) {
                return true;
            }
        }
        return false;
    }

    private boolean doesOverlap(int[] obstacle, int[] position) {
        // Obstacle is horizontal and overlaps with position
        if (obstacle[1] == obstacle[3] && obstacle[1] == position[1] && position[0] >= obstacle[0] && position[0] <= obstacle[2]) {
            return true;
        }
        // Obstacle is vertical and overlaps with position
        if (obstacle[0] == obstacle[2] && obstacle[0] == position[0] && position[1] >= obstacle[1] && position[1] <= obstacle[3]) {
            return true;
        }
        return false;
    }

    private boolean isPositionOutOfBounds(int[] position) {
        return position[0] < 0 || position[0] >= SPACESHIP_WIDTH || position[1] < 0 || position[1] >= SPACESHIP_HEIGHT;
    }

    private String[] parseCommand(String commandString) throws IllegalArgumentException {
        String[] command = commandString.replaceAll("\\[|\\]", "").split(",");
        if (command.length != 2) {
            throw new IllegalArgumentException("Invalid command: " + commandString);
        }
        return command;
    }

    private String formatCoordinateString(int[] coordinate) {
        return String.format("(%d,%d)", coordinate[0], coordinate[1]);
    }
}
