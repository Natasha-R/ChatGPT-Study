package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    private int x; // current x-coordinate
    private int y; // current y-coordinate
    private int[][] obstacles; // array to store obstacle coordinates

    // Constructor
    public Exercise0() {
        x = 11; // starting x-coordinate
        y = 7; // starting y-coordinate
        // Initialize obstacle coordinates
        obstacles = new int[][]{{6, 2, 6, 5}, {5, 5, 6, 5}, {5, 5, 5, 6}, {5, 6, 12, 6}};
    }

    @Override
    public String goTo(String goCommandString) {
        // Parse goCommandString to extract direction and steps
        String direction = goCommandString.substring(1, 3);
        int steps = Integer.parseInt(goCommandString.substring(4, goCommandString.length() - 1));

        // Update coordinates based on direction and steps
        switch (direction) {
            case "no": // Move north
                for (int i = 0; i < steps; i++) {
                    if (isObstacle(x, y + 1)) { // Check for obstacle
                        break;
                    }
                    y++;
                }
                break;
            case "ea": // Move east
                for (int i = 0; i < steps; i++) {
                    if (isObstacle(x + 1, y)) { // Check for obstacle
                        break;
                    }
                    x++;
                }
                break;
            case "so": // Move south
                for (int i = 0; i < steps; i++) {
                    if (isObstacle(x, y - 1)) { // Check for obstacle
                        break;
                    }
                    y--;
                }
                break;
            case "we": // Move west
                for (int i = 0; i < steps; i++) {
                    if (isObstacle(x - 1, y)) { // Check for obstacle
                        break;
                    }
                    x--;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Clamp coordinates within spaceship deck boundaries
        x = Math.max(0, Math.min(x, 11));
        y = Math.max(0, Math.min(y, 7));

        // Return current coordinates as string
        return "(" + x + "," + y + ")";
    }

    // Helper method to check if given coordinates are part of an obstacle
    private boolean isObstacle(int x, int y) {
        for (int[] obstacle : obstacles) {
            if (x >= obstacle[0] && x <= obstacle[2] && y >= obstacle[1] && y <= obstacle[3]) {
                return true;
            }
        }
        return false;
    }
}