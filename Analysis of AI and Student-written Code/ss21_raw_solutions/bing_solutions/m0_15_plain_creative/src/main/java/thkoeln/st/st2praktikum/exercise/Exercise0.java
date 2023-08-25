package thkoeln.st.st2praktikum.exercise;

// A class that represents a tidy-up robot that can move in a room with walls
public class Exercise0 implements Walkable {

    // Constants for the directions
    private static final String NORTH = "no";
    private static final String EAST = "ea";
    private static final String SOUTH = "so";
    private static final String WEST = "we";

    // Constants for the room size and walls
    private static final int ROOM_WIDTH = 12;
    private static final int ROOM_HEIGHT = 8;
    private static final int[][] WALLS = {{3, 0, 3, 3}, {4, 3, 7, 3}, {7, 0, 7, 2}, {1, 4, 8, 4}};

    // Variables for the robot position
    private int x;
    private int y;

    // Constructor that sets the initial position of the robot
    public Exercise0() {
        x = 3;
        y = 0;
    }

    // A method that moves the robot according to a command string and returns its final position
    public String walkTo(String walkCommandString) {
        // Parse the command string and extract the direction and steps
        String direction = walkCommandString.substring(1, 3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));

        // Move the robot according to the direction and steps using a for loop
        for (int i = 0; i < steps; i++) {
            // Check if the robot can move from its current position
            if (isWall(x, y) || isBoundary(x, y)) {
                // The robot cannot move
                break;
            }

            // Increment or decrement the position by one depending on the direction
            switch (direction) {
                case NORTH:
                    y++;
                    break;
                case EAST:
                    x++;
                    break;
                case SOUTH:
                    y--;
                    break;
                case WEST:
                    x--;
                    break;
                default:
                    // Invalid direction
                    return null;
            }

            // Check if the robot has hit a wall or a room boundary and adjust its position accordingly
            if (isWall(x, y)) {
                // The robot has hit a wall
                switch (direction) {
                    case NORTH:
                        y--;
                        break;
                    case EAST:
                        x--;
                        break;
                    case SOUTH:
                        y++;
                        break;
                    case WEST:
                        x++;
                        break;
                }
            }

            if (isBoundary(x, y)) {
                // The robot has hit a room boundary
                switch (direction) {
                    case NORTH:
                        y = ROOM_HEIGHT - 1;
                        break;
                    case EAST:
                        x = ROOM_WIDTH - 1;
                        break;
                    case SOUTH:
                        y = 0;
                        break;
                    case WEST:
                        x = 0;
                        break;
                }
            }
        }

        // Return the final position of the robot as a string
        return "(" + x + "," + y + ")";
    }

    // A helper method that checks if a given position is a wall
    private boolean isWall(int x, int y) {
        for (int[] wall : WALLS) {
            if ((x == wall[0] || x == wall[2]) && y >= wall[1] && y <= wall[3]) {
                // The position is on a vertical wall
                return true;
            }
            if ((y == wall[1] || y == wall[3]) && x >= wall[0] && x <= wall[2]) {
                // The position is on a horizontal wall
                return true;
            }
        }
        return false;
    }

    // A helper method that checks if a given position is a room boundary
    private boolean isBoundary(int x, int y) {
        return x < 0 || x >= ROOM_WIDTH || y < 0 || y >= ROOM_HEIGHT;
    }
}