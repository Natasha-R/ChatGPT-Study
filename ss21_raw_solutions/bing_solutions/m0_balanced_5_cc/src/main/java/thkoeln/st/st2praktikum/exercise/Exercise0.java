package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    private final int[][] walls = {{3, 0, 3, 3}, {4, 3, 7, 3}, {7, 0, 7, 2}, {1, 4, 8, 4}};
    private final int[] position = {0, 0};
    private final int[] roomSize = {9, 5};

    public Exercise0() {
    }

    public String walkTo(String walkCommandString) {
        String[] command = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        int steps = Integer.parseInt(command[1]);
        switch (command[0]) {
            case "no":
                return moveNorth(steps);
            case "ea":
                return moveEast(steps);
            case "so":
                return moveSouth(steps);
            case "we":
                return moveWest(steps);
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }

    private String moveNorth(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (position[1] + i >= roomSize[1] || isWall(position[0], position[1] + i)) {
                return "(" + position[0] + ", " + (position[1] + i - 1) + ")";
            }
        }
        position[1] += steps;
        return "(" + position[0] + ", " + position[1] + ")";
    }

    private String moveEast(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (position[0] + i >= roomSize[0] || isWall(position[0] + i, position[1])) {
                return "(" + (position[0] + i - 1) + ", " + position[1] + ")";
            }
        }
        position[0] += steps;
        return "(" + position[0] + ", " + position[1] + ")";
    }

    private String moveSouth(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (position[1] - i < 0 || isWall(position[0], position[1] - i)) {
                return "(" + position[0] + ", " + (position[1] - i + 1) + ")";
            }
        }
        position[1] -= steps;
        return "(" + position[0] + ", " + position[1] + ")";
    }

    private String moveWest(int steps) {
        for (int i = 1; i <= steps; i++) {
            if (position[0] - i < 0 || isWall(position[0] - i, position[1])) {
                return "(" + (position[0] - i + 1) + ", " + position[1] + ")";
            }
        }
        position[0] -= steps;
        return "(" + position[0] + ", " + position[1] + ")";
    }

    private boolean isWall(int x, int y) {
        for (int[] wall : walls) {
            if ((wall[0] == x && wall[1] == y) || (wall[2] == x && wall[3] == y)) {
                return true;
            } else if ((wall[2] == x && wall[1] == y) || (wall[0] == x && wall[3] == y)) {
                return true;
            }
        }
        return false;
    }
}