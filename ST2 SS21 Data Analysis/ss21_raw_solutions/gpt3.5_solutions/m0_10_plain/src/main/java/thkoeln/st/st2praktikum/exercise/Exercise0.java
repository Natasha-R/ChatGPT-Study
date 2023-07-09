package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    // Define the size of the spaceship deck
    private static final int WIDTH = 12;
    private static final int HEIGHT = 8;

    // Define the locations of the obstacles
    private static final int[][] OBSTACLES = {
            {6, 2, 6, 5},
            {5, 5, 6, 5},
            {5, 5, 5, 6},
            {5, 6, 12, 6}
    };

    // Define the initial position of the maintenance droid
    private int x = 11;
    private int y = 7;

    @Override
    public String goTo(String goCommandString) {
        // Parse the command string
        String[] parts = goCommandString.replaceAll("[\\[\\]]", "").split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        // Move the maintenance droid in the specified direction
        switch (direction) {
            case "no":
                y += steps;
                break;
            case "ea":
                x += steps;
                break;
            case "so":
                y -= steps;
                break;
            case "we":
                x -= steps;
                break;
        }

        // Check for collisions with obstacles or the spaceship deck boundary
        for (int[] obstacle : OBSTACLES) {
            if (x >= obstacle[0] && x <= obstacle[2] && y >= obstacle[1] && y <= obstacle[3]) {
                // Collision detected, adjust the position of the droid
                if (direction.equals("no")) {
                    y = obstacle[1] - 1;
                } else if (direction.equals("ea")) {
                    x = obstacle[0] - 1;
                } else if (direction.equals("so")) {
                    y = obstacle[3] + 1;
                } else if (direction.equals("we")) {
                    x = obstacle[2] + 1;
                }
                break;
            }
        }

        // Check if the maintenance droid has moved out of the spaceship deck boundary
        if (x < 0) {
            x = 0;
        } else if (x >= WIDTH) {
            x = WIDTH - 1;
        }
        if (y < 0) {
            y = 0;
        } else if (y >= HEIGHT) {
            y = HEIGHT - 1;
        }

        // Return the current position of the maintenance droid
        return "(" + x + "," + y + ")";
    }
}
