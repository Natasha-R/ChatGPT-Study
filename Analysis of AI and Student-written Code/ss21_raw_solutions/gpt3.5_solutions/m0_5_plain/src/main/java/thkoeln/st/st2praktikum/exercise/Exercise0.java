package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    private int[][] obstacles;
    private int[][] grid;
    private int currentX;
    private int currentY;

    // Constructor
    public Exercise0() {
        // Initialize the grid and obstacles based on the provided map
        obstacles = new int[][]{{6, 2, 6, 5}, {5, 5, 6, 5}, {5, 5, 5, 6}, {5, 6, 12, 6}};
        grid = new int[12][8];
        currentX = 11;
        currentY = 7;
    }

    @Override
    public String goTo(String goCommandString) {
        // Parse the goCommandString to get the direction and steps
        String direction = goCommandString.substring(1, 3);
        int steps = Integer.parseInt(goCommandString.substring(4, goCommandString.length() - 1));

        // Update the current position based on the direction and steps
        switch (direction) {
            case "no":
                for (int i = 1; i <= steps; i++) {
                    if (currentY + i >= 8 || isObstacle(currentX, currentY + i)) {
                        currentY += i - 1;
                        break;
                    }
                    currentY += i;
                }
                break;
            case "ea":
                for (int i = 1; i <= steps; i++) {
                    if (currentX + i >= 12 || isObstacle(currentX + i, currentY)) {
                        currentX += i - 1;
                        break;
                    }
                    currentX += i;
                }
                break;
            case "so":
                for (int i = 1; i <= steps; i++) {
                    if (currentY - i < 0 || isObstacle(currentX, currentY - i)) {
                        currentY -= i - 1;
                        break;
                    }
                    currentY -= i;
                }
                break;
            case "we":
                for (int i = 1; i <= steps; i++) {
                    if (currentX - i < 0 || isObstacle(currentX - i, currentY)) {
                        currentX -= i - 1;
                        break;
                    }
                    currentX -= i;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Return the updated position as a formatted string
        return "(" + currentX + "," + currentY + ")";
    }

    // Helper method to check if a given position is an obstacle
    private boolean isObstacle(int x, int y) {
        for (int[] obstacle : obstacles) {
            if (x >= obstacle[0] && x <= obstacle[2] && y >= obstacle[1] && y <= obstacle[3]) {
                return true;
            }
        }
        return false;
    }
}
