package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    private static final int WIDTH = 12;
    private static final int HEIGHT = 8;
    private static final int[][] OBSTACLES = {{6,2,6,5}, {5,5,6,5}, {5,5,5,6}, {5,6,12,6}};
    private static final int[][] DIRECTIONS = {{0,1}, {1,0}, {0,-1}, {-1,0}}; // {dx, dy} for each direction
    private int x;
    private int y;

    public Exercise0() {
        this.x = 11;
        this.y = 7;
    }

    public String goTo(String goCommandString) {
        String[] commandParts = goCommandString.replaceAll("[\\[\\]]", "").split(",");
        int directionIndex = getDirectionIndex(commandParts[0]);
        int steps = Integer.parseInt(commandParts[1]);

        int dx = DIRECTIONS[directionIndex][0];
        int dy = DIRECTIONS[directionIndex][1];

        for (int i = 0; i < steps; i++) {
            int newX = x + dx;
            int newY = y + dy;
            boolean obstacleHit = false;

            // Check if new position hits an obstacle or boundary
            for (int[] obstacle : OBSTACLES) {
                if (dx == 0 && obstacle[0] <= newX && newX <= obstacle[2] && obstacle[1] <= y && y <= obstacle[3]) {
                    newX = obstacle[0] - 1;
                    obstacleHit = true;
                } else if (dy == 0 && obstacle[1] <= newY && newY <= obstacle[3] && obstacle[0] <= x && x <= obstacle[2]) {
                    newY = obstacle[1] - 1;
                    obstacleHit = true;
                }
            }
            if (newX < 0) newX = 0;
            if (newX >= WIDTH) newX = WIDTH - 1;
            if (newY < 0) newY = 0;
            if (newY >= HEIGHT) newY = HEIGHT - 1;

            if (obstacleHit) {
                break; // Stop if obstacle hit
            } else {
                x = newX;
                y = newY;
            }
        }

        return "(" + (x+1) + "," + (y+1) + ")";
    }

    private int getDirectionIndex(String direction) {
        switch (direction) {
            case "no":
                return 0;
            case "ea":
                return 1;
            case "so":
                return 2;
            case "we":
                return 3;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
}