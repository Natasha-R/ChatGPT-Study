package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private final int[][] walls = {{3, 0}, {3, 1}, {3, 2}, {3, 3}, {4, 3}, {5, 3}, {6, 3}, {7, 3}, {7, 2}, {7, 1}, {7, 0}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}, {6, 4}, {7, 4}};
    private final int[] robotPosition = {3, 0};
    private final int roomWidth = 12;
    private final int roomHeight = 8;

    public String walkTo(String walkCommandString) {
        String[] walkCommand = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = walkCommand[0];
        int steps = Integer.parseInt(walkCommand[1]);
        int[] newPosition = calculateNewPosition(direction, steps);
        if (isWall(newPosition)) {
            return getRobotPosition();
        }
        updateRobotPosition(newPosition);
        return getRobotPosition();
    }

    private int[] calculateNewPosition(String direction, int steps) {
        int[] newPosition = new int[]{robotPosition[0], robotPosition[1]};
        switch (direction) {
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
        }
        if (newPosition[0] < 0) {
            newPosition[0] = 0;
        } else if (newPosition[0] >= roomWidth) {
            newPosition[0] = roomWidth - 1;
        }
        if (newPosition[1] < 0) {
            newPosition[1] = 0;
        } else if (newPosition[1] >= roomHeight) {
            newPosition[1] = roomHeight - 1;
        }
        return newPosition;
    }

    private boolean isWall(int[] position) {
        for (int[] wall : walls) {
            if (position[0] == wall[0] && position[1] == wall[1]) {
                return true;
            }
        }
        return false;
    }

    private void updateRobotPosition(int[] newPosition) {
        robotPosition[0] = newPosition[0];
        robotPosition[1] = newPosition[1];
    }

    private String getRobotPosition() {
        return "(" + robotPosition[0] + "," + robotPosition[1] + ")";
    }
}