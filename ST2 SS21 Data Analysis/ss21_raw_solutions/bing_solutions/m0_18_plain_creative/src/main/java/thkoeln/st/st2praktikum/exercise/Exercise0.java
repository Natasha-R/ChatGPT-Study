package thkoeln.st.st2praktikum.exercise;

// A class that represents a tidy-up robot that can walk in a room with walls
public class Exercise0 implements Walkable {

    // Constants for directions
    private static final String NORTH = "no";
    private static final String EAST = "ea";
    private static final String SOUTH = "so";
    private static final String WEST = "we";

    // Constants for room size and walls
    private static final int ROOM_WIDTH = 12;
    private static final int ROOM_HEIGHT = 8;
    private static final boolean[][] WALLS = new boolean[ROOM_WIDTH + 1][ROOM_HEIGHT + 1]; // a 2D array that stores whether there is a wall on each grid line

    static {
        // Initialize the walls according to the personal map
        for (int x = 3; x <= 7; x++) {
            WALLS[x][3] = true; // wall between (3,0)-(3,3) and (4,3)-(7,3)
        }
        for (int y = 0; y <= 4; y++) {
            WALLS[3][y] = true; // wall between (3,0)-(3,3)
            WALLS[7][y] = true; // wall between (7,0)-(7,2)
        }
        for (int x = 1; x <= 8; x++) {
            WALLS[x][4] = true; // wall between (1,4)-(8,4)
        }
    }

    // Instance variables for robot position
    private int x; // the x-coordinate of the robot
    private int y; // the y-coordinate of the robot

    // Constructor that sets the initial position of the robot according to the personal map
    public Exercise0() {
        this.x = 3;
        this.y = 0;
    }

    // A method that moves the robot according to a walk command string and returns its coordinate
    public String walkTo(String walkCommandString) {
        // Parse the walk command string into direction and steps
        String direction = walkCommandString.substring(1, 3); // extract the direction part
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1)); // extract and convert the steps part

        // Move the robot according to the direction and steps, stopping at walls or boundaries
        switch (direction) {
            case NORTH:
                while (steps > 0 && y < ROOM_HEIGHT - 1 && !WALLS[x][y + 1]) { // while there are steps left and no wall or boundary above
                    y++; // move one cell up
                    steps--; // decrease one step
                }
                break;
            case EAST:
                while (steps > 0 && x < ROOM_WIDTH - 1 && !WALLS[x + 1][y]) { // while there are steps left and no wall or boundary to the right
                    x++; // move one cell right
                    steps--; // decrease one step
                }
                break;
            case SOUTH:
                while (steps > 0 && y > 0 && !WALLS[x][y - 1]) { // while there are steps left and no wall or boundary below
                    y--; // move one cell down
                    steps--; // decrease one step
                }
                break;
            case WEST:
                while (steps > 0 && x > 0 && !WALLS[x][y]) { // while there are steps left and no wall or boundary to the left
                    x--; // move one cell left
                    steps--; // decrease one step
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction); // throw an exception if the direction is not valid
        }

        // Return the coordinate of the robot after walking as a string
        return "(" + x + "," + y + ")";
    }
}