package thkoeln.st.st2praktikum.exercise;

// Define the class that implements the Walkable interface
public class Exercise0 implements Walkable {

    // Define the constants for the room size and the directions
    private static final int ROOM_WIDTH = 12;
    private static final int ROOM_HEIGHT = 8;
    private static final String NORTH = "no";
    private static final String EAST = "ea";
    private static final String SOUTH = "so";
    private static final String WEST = "we";

    // Define the arrays that represent the left and bottom walls in the room
    // A cell is true if it has a wall on its left or bottom side
    private static final boolean[][] LEFT_WALLS = new boolean[ROOM_WIDTH][ROOM_HEIGHT];
    private static final boolean[][] BOTTOM_WALLS = new boolean[ROOM_WIDTH][ROOM_HEIGHT];

    static {
        // Mark the cells that have a wall on their left side
        for (int y = 0; y <= 3; y++) {
            LEFT_WALLS[3][y] = true;
        }
        for (int y = 0; y <= 2; y++) {
            LEFT_WALLS[7][y] = true;
        }
        // Mark the cells that have a wall on their bottom side
        for (int x = 4; x <= 7; x++) {
            BOTTOM_WALLS[x][3] = true;
        }
        for (int x = 1; x <= 8; x++) {
            BOTTOM_WALLS[x][4] = true;
        }
    }


    // Define the fields for the robot's position
    private int robotX;
    private int robotY;

    // Define the constructor that sets the robot's initial position
    public Exercise0() {
        robotX = 3;
        robotY = 0;
    }

    // Implement the walkTo method that moves the robot according to the command
    @Override
    public String walkTo(String walkCommandString) {
        // Parse the command string and extract the direction and the steps
        String direction = parseDirection(walkCommandString);
        int steps = parseSteps(walkCommandString);

        // Move the robot according to the direction and the steps, stopping at walls or boundaries
        switch (direction) {
            case NORTH: // Move north
                moveNorth(steps);
                break;
            case EAST: // Move east
                moveEast(steps);
                break;
            case SOUTH: // Move south
                moveSouth(steps);
                break;
            case WEST: // Move west
                moveWest(steps);
                break;
            default: // Invalid direction
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Return the robot's position as a string in the format "(x,y)"
        return "(" + robotX + "," + robotY + ")";
    }

    // Parse the command string and extract the direction part
    private String parseDirection(String walkCommandString) {
        walkCommandString = walkCommandString.trim();
        if (!walkCommandString.startsWith("[") || !walkCommandString.endsWith("]")) {
            throw new IllegalArgumentException("Invalid command format");
        }
        walkCommandString = walkCommandString.substring(1, walkCommandString.length() - 1);
        String[] parts = walkCommandString.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid command format");
        }
        return parts[0].trim().toLowerCase();
    }

    // Parse the command string and extract the steps part
    private int parseSteps(String walkCommandString) {
        walkCommandString = walkCommandString.trim();
        if (!walkCommandString.startsWith("[") || !walkCommandString.endsWith("]")) {
            throw new IllegalArgumentException("Invalid command format");
        }
        walkCommandString = walkCommandString.substring(1, walkCommandString.length() - 1);
        String[] parts = walkCommandString.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid command format");
        }
        return Integer.parseInt(parts[1].trim());
    }

    // Move the robot north by a given number of steps
    private void moveNorth(int steps) {
        for (int i = 0; i < steps; i++) {
            // Check if there is a wall or a boundary above the robot
            if (robotY == ROOM_HEIGHT - 1 || BOTTOM_WALLS[robotX][robotY + 1]) {
                break; // Stop moving
            }
            robotY++; // Move one cell up
        }
    }

    // Move the robot east by a given number of steps
    private void moveEast(int steps) {
        for (int i = 0; i < steps; i++) {
            // Check if there is a wall or a boundary on the right of the robot
            if (robotX == ROOM_WIDTH - 1 || LEFT_WALLS[robotX + 1][robotY]) {
                break; // Stop moving
            }
            robotX++; // Move one cell right
        }
    }

    // Move the robot south by a given number of steps
    private void moveSouth(int steps) {
        for (int i = 0; i < steps; i++) {
            // Check if there is a wall or a boundary below the robot
            if (robotY == 0 || BOTTOM_WALLS[robotX][robotY]) {
                break; // Stop moving
            }
            robotY--; // Move one cell down
        }
    }

    // Move the robot west by a given number of steps
    private void moveWest(int steps) {
        for (int i = 0; i < steps; i++) {
            // Check if there is a wall or a boundary on the left of the robot
            if (robotX == 0 || LEFT_WALLS[robotX][robotY]) {
                break; // Stop moving
            }
            robotX--; // Move one cell left
        }
    }
}