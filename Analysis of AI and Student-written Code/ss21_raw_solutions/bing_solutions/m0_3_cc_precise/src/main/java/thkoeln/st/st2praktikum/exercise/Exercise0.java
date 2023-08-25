package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int robotX;
    private int robotY;
    private final int roomWidth = 12;
    private final int roomHeight = 8;
    private final boolean[][] walls = new boolean[roomWidth][roomHeight];

    public Exercise0() {
        robotX = 3;
        robotY = 0;
        initializeWalls();
    }

    private void initializeWalls() {
        for (int i = 0; i <= 3; i++) walls[3][i] = true;
        for (int i = 4; i <= 7; i++) walls[i][3] = true;
        for (int i = 0; i <= 2; i++) walls[7][i] = true;
        for (int i = 1; i <= 8; i++) walls[i][4] = true;
    }

    @Override
    public String walkTo(String walkCommandString) {
        String direction = extractDirection(walkCommandString);
        int steps = extractSteps(walkCommandString);
        moveRobot(direction, steps);
        return getCurrentPosition();
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
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private void moveNorth(int steps) {
        while (steps > 0 && robotY < roomHeight - 1 && !walls[robotX][robotY + 1]) {
            robotY++;
            steps--;
        }
    }

    private void moveEast(int steps) {
        while (steps > 0 && robotX < roomWidth - 1 && !walls[robotX + 1][robotY]) {
            robotX++;
            steps--;
        }
    }

    private void moveSouth(int steps) {
        while (steps > 0 && robotY > 0 && !walls[robotX][robotY - 1]) {
            robotY--;
            steps--;
        }
    }

    private void moveWest(int steps) {
        while (steps > 0 && robotX > 0 && !walls[robotX - 1][robotY]) {
            robotX--;
            steps--;
        }
    }

    private String getCurrentPosition() {
        return "(" + robotX + "," + robotY + ")";
    }
}