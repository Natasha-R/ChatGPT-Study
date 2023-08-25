package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int robotX = 3;
    private int robotY = 0;
    private int roomWidth = 12;
    private int roomHeight = 8;
    private boolean[][] verticalWalls = new boolean[roomWidth + 1][roomHeight];
    private boolean[][] horizontalWalls = new boolean[roomWidth][roomHeight + 1];

    public Exercise0() {
        initializeWalls();
    }

    private void initializeWalls() {
        // Room boundaries
        for (int i = 0; i < roomHeight; i++) {
            verticalWalls[0][i] = true;
            verticalWalls[roomWidth][i] = true;
        }
        for (int i = 0; i < roomWidth; i++) {
            horizontalWalls[i][0] = true;
            horizontalWalls[i][roomHeight] = true;
        }
        // Inner walls
        for (int i = 0; i <= 3; i++) {
            verticalWalls[3][i] = true;
        }
        for (int i = 4; i <= 7; i++) {
            horizontalWalls[i][3] = true;
        }
        for (int i = 0; i <= 2; i++) {
            verticalWalls[7][i] = true;
        }
        for (int i = 1; i <= 8; i++) {
            horizontalWalls[i][4] = true;
        }
    }

    public String walkTo(String walkCommandString) {
        String direction = extractDirection(walkCommandString);
        int steps = extractSteps(walkCommandString);

        moveRobot(direction, steps);

        return "(" + robotX + "," + robotY + ")";
    }

    private String extractDirection(String walkCommandString) {
        String[] parts = walkCommandString.split(",");
        return parts[0].substring(1);
    }

    private int extractSteps(String walkCommandString) {
        String[] parts = walkCommandString.split(",");
        return Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
    }

    private void moveRobot(String direction, int steps) {
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
        }
    }

    private void moveNorth(int steps) {
        for (int i = 0; i < steps; i++) {
            if (horizontalWalls[robotX][robotY + 1]) break;
            robotY++;
        }
    }

    private void moveEast(int steps) {
        for (int i = 0; i < steps; i++) {
            if (verticalWalls[robotX + 1][robotY]) break;
            robotX++;
        }
    }

    private void moveSouth(int steps) {
        for (int i = 0; i < steps; i++) {
            if (horizontalWalls[robotX][robotY]) break;
            robotY--;
        }
    }

    private void moveWest(int steps) {
        for (int i = 0; i < steps; i++) {
            if (verticalWalls[robotX][robotY]) break;
            robotX--;
        }
    }
}