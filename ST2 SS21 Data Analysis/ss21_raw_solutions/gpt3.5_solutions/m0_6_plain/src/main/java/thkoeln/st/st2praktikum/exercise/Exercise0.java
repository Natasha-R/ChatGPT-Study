package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {

    private int currentX; // current x-coordinate of the droid
    private int currentY; // current y-coordinate of the droid
    private int gridWidth; // width of the grid
    private int gridHeight; // height of the grid
    private boolean[][] obstacles; // 2D array to represent the obstacles in the grid

    // Constructor with hardcoded values for the droid's starting position, grid size, and obstacle positions
    public Exercise0() {
        this.currentX = 11; // Starting x-coordinate of the droid
        this.currentY = 7; // Starting y-coordinate of the droid
        this.gridWidth = 12; // Width of the grid
        this.gridHeight = 8; // Height of the grid

        // Initialize the obstacles array with false (no obstacles)
        this.obstacles = new boolean[gridWidth][gridHeight];

        // Set obstacles at specific positions (0-based coordinates)
        for (int x = 6; x <= 6; x++) {
            for (int y = 2; y <= 5; y++) {
                obstacles[x][y] = true;
            }
        }
        for (int x = 5; x <= 6; x++) {
            obstacles[x][5] = true;
        }
        for (int x = 5; x <= 5; x++) {
            for (int y = 5; y <= 6; y++) {
                obstacles[x][y] = true;
            }
        }
        for (int x = 5; x <= 12; x++) {
            obstacles[x][6] = true;
        }
    }

    // Method to move the droid based on the given command and return the new position
    @Override
    public String goTo(String goCommandString) {
        String[] goCommand = goCommandString.substring(1, goCommandString.length() - 1).split(",");
        String direction = goCommand[0];
        int steps = Integer.parseInt(goCommand[1]);

        int newX = currentX;
        int newY = currentY;

        switch (direction) {
            case "no": // Move north
                newY = Math.min(currentY + steps, gridHeight - 1);
                break;
            case "ea": // Move east
                newX = Math.min(currentX + steps, gridWidth - 1);
                break;
            case "so": // Move south
                newY = Math.max(currentY - steps, 0);
                break;
            case "we": // Move west
                newX = Math.max(currentX - steps, 0);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        // Check for obstacles and update the droid's position
        if (!obstacles[newX][newY]) {
            currentX = newX;
            currentY = newY;
        }

        // Return the new position of the droid as a formatted string
        return "(" + currentX + "," + currentY + ")";
    }
}