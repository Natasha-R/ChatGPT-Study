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

    // A method that implements the walkTo interface
    public String walkTo(String walkCommandString) {
        // Parse the command string and extract the direction and steps
        walkCommandString = walkCommandString.replace("[", "").replace("]", "");
        String[] parts = walkCommandString.split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        // Move the robot according to the direction and steps
        switch (direction) {
            case NORTH:
                moveNorth(steps);
                break;
            case EAST:
                moveEast(steps);
                break;
            case SOUTH:
                moveSouth(steps);
                break;
            case WEST:
                moveWest(steps);
                break;
            default:
                // Invalid direction
                break;
        }

        // Return the current position of the robot as a string
        return "(" + x + "," + y + ")";
    }

    // A helper method that moves the robot north by a given number of steps
    private void moveNorth(int steps) {
        // Loop through each step and check if there is a wall or boundary ahead
        for (int i = 0; i < steps; i++) {
            // Check if the robot is at the top boundary of the room
            if (y == ROOM_HEIGHT - 1) {
                // Stop moving
                break;
            }
            // Check if there is a wall above the robot
            boolean wallAbove = false;
            for (int[] wall : WALLS) {
                // A wall is above the robot if it has the same x coordinate and its lower y coordinate is equal to the robot's upper y coordinate
                if (wall[0] == x && wall[1] == y + 1) {
                    wallAbove = true;
                    break;
                }
            }
            if (wallAbove) {
                // Stop moving
                break;
            }
            // Move one step north
            y++;
        }
    }

    // A helper method that moves the robot east by a given number of steps
    private void moveEast(int steps) {
        // Loop through each step and check if there is a wall or boundary ahead
        for (int i = 0; i < steps; i++) {
            // Check if the robot is at the right boundary of the room
            if (x == ROOM_WIDTH - 1) {
                // Stop moving
                break;
            }
            // Check if there is a wall to the right of the robot
            boolean wallRight = false;
            for (int[] wall : WALLS) {
                // A wall is to the right of the robot if it has the same y coordinate and its left x coordinate is equal to the robot's right x coordinate
                if (wall[1] == y && wall[0] == x + 1) {
                    wallRight = true;
                    break;
                }
            }
            if (wallRight) {
                // Stop moving
                break;
            }
            // Move one step east
            x++;
        }
    }

    // A helper method that moves the robot south by a given number of steps
    private void moveSouth(int steps) {
        // Loop through each step and check if there is a wall or boundary ahead
        for (int i = 0; i < steps; i++) {
            // Check if the robot is at the bottom boundary of the room
            if (y == 0) {
                // Stop moving
                break;
            }
            // Check if there is a wall below the robot
            boolean wallBelow = false;
            for (int[] wall : WALLS) {
                // A wall is below the robot if it has the same x coordinate and its upper y coordinate is equal to the robot's lower y coordinate
                if (wall[0] == x && wall[3] == y) {
                    wallBelow = true;
                    break;
                }
            }
            if (wallBelow) {
                // Stop moving
                break;
            }
            // Move one step south
            y--;
        }
    }

    // A helper method that moves the robot west by a given number of steps
    private void moveWest(int steps) {
        // Loop through each step and check if there is a wall or boundary ahead
        for (int i = 0; i < steps; i++) {
            // Check if the robot is at the left boundary of the room
            if (x == 0) {
                // Stop moving
                break;
            }
            // Check if there is a wall to the left of the robot
            boolean wallLeft = false;
            for (int[] wall : WALLS) {
                // A wall is to the left of the robot if it has the same y coordinate and its right x coordinate is equal to the robot's left x coordinate
                if (wall[1] == y && wall[2] == x) {
                    wallLeft = true;
                    break;
                }
            }
            if (wallLeft) {
                // Stop moving
                break;
            }
            // Move one step west
            x--;
        }
    }
}
