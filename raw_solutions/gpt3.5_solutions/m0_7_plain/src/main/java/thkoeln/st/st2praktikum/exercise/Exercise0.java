package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    private int droidX;
    private int droidY;
    private final int gridWidth;
    private final int gridHeight;
    private final int[][] obstacles;

    public Exercise0() {
        // Initialize droid starting position and grid size
        droidX = 11;
        droidY = 7;
        gridWidth = 12;
        gridHeight = 8;

        // Define obstacle positions
        obstacles = new int[][]{
                {6, 2, 6, 5},
                {5, 5, 6, 5},
                {5, 5, 5, 6},
                {5, 6, 12, 6}
        };
    }

    @Override
    public String goTo(String goCommandString) {
        // Parse goCommandString to extract direction and steps
        String direction = goCommandString.substring(1, 3);
        int steps = Integer.parseInt(goCommandString.substring(4, goCommandString.length() - 1));

        // Update droid position based on direction and steps
        switch (direction) {
            case "no":
                droidY = Math.max(0, droidY - steps);
                break;
            case "ea":
                droidX = Math.min(gridWidth - 1, droidX + steps);
                break;
            case "so":
                droidY = Math.min(gridHeight - 1, droidY + steps);
                break;
            case "we":
                droidX = Math.max(0, droidX - steps);
                break;
            default:
                // Invalid direction, do nothing
                break;
        }

        // Check for collision with obstacles or spaceship deck boundaries
        for (int[] obstacle : obstacles) {
            int obstacleX1 = obstacle[0];
            int obstacleY1 = obstacle[1];
            int obstacleX2 = obstacle[2];
            int obstacleY2 = obstacle[3];

            if ((droidX >= obstacleX1 && droidX <= obstacleX2) && (droidY >= obstacleY1 && droidY <= obstacleY2)) {
                // Collision with obstacle, reset droid position
                droidX = Math.max(obstacleX2 + 1, Math.min(gridWidth - 1, droidX));
                droidY = Math.max(obstacleY2 + 1, Math.min(gridHeight - 1, droidY));
                break;
            }
        }

        // Format and return droid's current position as a string
        return String.format("(%d,%d)", droidX, droidY);
    }
}