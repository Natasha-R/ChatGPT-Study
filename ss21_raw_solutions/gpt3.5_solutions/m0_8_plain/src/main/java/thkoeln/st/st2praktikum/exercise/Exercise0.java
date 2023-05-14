package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    private static final int GRID_WIDTH = 12;
    private static final int GRID_HEIGHT = 8;
    private static final int START_X = 11;
    private static final int START_Y = 7;
    private static final int[][] OBSTACLES = {{6, 2, 6, 5}, {5, 5, 6, 5}, {5, 5, 5, 6}, {5, 6, 12, 6}};

    private int currentX;
    private int currentY;

    public Exercise0() {
        currentX = START_X;
        currentY = START_Y;
    }

    @Override
    public String goTo(String goCommandString) {
        String[] parts = goCommandString.substring(1, goCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        int newX = currentX;
        int newY = currentY;

        // Update the new position based on the direction and number of steps
        switch (direction) {
            case "no":
                newY = Math.max(currentY - steps, 0);
                break;
            case "ea":
                newX = Math.min(currentX + steps, GRID_WIDTH - 1);
                break;
            case "so":
                newY = Math.min(currentY + steps, GRID_HEIGHT - 1);
                break;
            case "we":
                newX = Math.max(currentX - steps, 0);
                break;
            default:
                // Invalid direction
                return "Invalid direction";
        }

        // Check for collisions with obstacles or spaceship deck boundaries
        for (int[] obstacle : OBSTACLES) {
            int obstacleStartX = obstacle[0];
            int obstacleStartY = obstacle[1];
            int obstacleEndX = obstacle[2];
            int obstacleEndY = obstacle[3];

            if ((currentX == newX && currentY == newY) || // Same position as before
                    (currentX == newX && newY >= obstacleStartY && newY <= obstacleEndY &&
                            ((obstacleStartX >= currentX && obstacleStartX <= newX) ||
                                    (obstacleEndX >= currentX && obstacleEndX <= newX))) || // Colliding with vertical obstacle
                    (currentY == newY && newX >= obstacleStartX && newX <= obstacleEndX &&
                            ((obstacleStartY >= currentY && obstacleStartY <= newY) ||
                                    (obstacleEndY >= currentY && obstacleEndY <= newY)))) { // Colliding with horizontal obstacle
                // Stay at current position
                return "(" + currentX + "," + currentY + ")";
            }
        }

        currentX = newX;
        currentY = newY;

        return "(" + currentX + "," + currentY + ")";
    }
}