package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    // The width and height of the room in cells
    private static final int ROOM_WIDTH = 12;
    private static final int ROOM_HEIGHT = 8;

    // The initial x and y coordinates of the robot in cells
    private static final int INITIAL_X = 3;
    private static final int INITIAL_Y = 0;

    // The current x and y coordinates of the robot in cells
    private int x;
    private int y;

    // A two-dimensional array that represents the room grid
    // A cell is true if it is blocked by a wall or a boundary, false otherwise
    private boolean[][] grid;

    // A constructor that initializes the robot and the grid
    public Exercise0() {
        // Set the initial position of the robot
        x = INITIAL_X;
        y = INITIAL_Y;

        // Create the grid with the given width and height
        grid = new boolean[ROOM_WIDTH][ROOM_HEIGHT];

        // Mark the boundaries of the room as blocked cells
        for (int i = 0; i < ROOM_WIDTH; i++) {
            grid[i][0] = true; // bottom boundary
            grid[i][ROOM_HEIGHT - 1] = true; // top boundary
        }
        for (int j = 0; j < ROOM_HEIGHT; j++) {
            grid[0][j] = true; // left boundary
            grid[ROOM_WIDTH - 1][j] = true; // right boundary
        }

        // Mark the walls as blocked cells
        for (int j = 0; j <= 3; j++) {
            grid[3][j] = true; // wall between (3,0) and (3,3)
        }
        for (int i = 4; i <= 7; i++) {
            grid[i][3] = true; // wall between (4,3) and (7,3)
        }
        for (int j = 0; j <= 2; j++) {
            grid[7][j] = true; // wall between (7,0) and (7,2)
        }
        for (int i = 1; i <= 8; i++) {
            grid[i][4] = true; // wall between (1,4) and (8,4)
        }
    }

    // A method that implements the Walkable interface
    public String walkTo(String walkCommandString) {
        // Parse the walk command string into direction and steps
        String direction = walkCommandString.substring(1, 3); // e.g. "no"
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1)); // e.g. 2

        // Move the robot according to the direction and steps
        switch (direction) {
            case "no": // north
                for (int i = 0; i < steps; i++) {
                    if (y < ROOM_HEIGHT - 1 && !grid[x][y + 1]) { // check if the robot is not at the top boundary and the next cell is not blocked
                        y++; // move one cell up
                    } else {
                        break; // stop moving if at the boundary or blocked by a wall
                    }
                }
                break;
            case "ea": // east
                for (int i = 0; i < steps; i++) {
                    if (x < ROOM_WIDTH - 1 && !grid[x + 1][y]) { // check if the robot is not at the right boundary and the next cell is not blocked
                        x++; // move one cell right
                    } else {
                        break; // stop moving if at the boundary or blocked by a wall
                    }
                }
                break;
            case "so": // south
                for (int i = 0; i < steps; i++) {
                    if (y > 0 && !grid[x][y - 1]) { // check if the robot is not at the bottom boundary and the next cell is not blocked
                        y--; // move one cell down
                    } else {
                        break; // stop moving if at the boundary or blocked by a wall
                    }
                }
                break;
            case "we": // west
                for (int i = 0; i < steps; i++) {
                    if (x > 0 && !grid[x - 1][y]) { // check if the robot is not at the left boundary and the next cell is not blocked
                        x--; // move one cell left
                    } else {
                        break; // stop moving if at the boundary or blocked by a wall
                    }
                }
                break;
            default: // invalid direction
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Return the current position of the robot as a string
        return "(" + x + "," + y + ")";
    }
}

