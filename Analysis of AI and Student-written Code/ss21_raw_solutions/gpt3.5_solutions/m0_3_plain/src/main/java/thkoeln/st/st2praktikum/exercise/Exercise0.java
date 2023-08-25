package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements GoAble {
    private int[][] grid; // 2D array representing the spaceship deck grid
    private int startX; // starting x-coordinate of the maintenance droid
    private int startY; // starting y-coordinate of the maintenance droid
    private List<int[]> obstacles; // list of obstacles represented as start and end coordinates

    public Exercise0() {
        // Define the spaceship deck grid size
        int gridSizeX = 12;
        int gridSizeY = 8;
        grid = new int[gridSizeX][gridSizeY];

        // Set the starting coordinates of the maintenance droid
        startX = 11;
        startY = 7;

        // Define the obstacles
        obstacles = new ArrayList<>();
        obstacles.add(new int[]{6, 2, 6, 5});
        obstacles.add(new int[]{5, 5, 6, 5});
        obstacles.add(new int[]{5, 5, 5, 6});
        obstacles.add(new int[]{5, 6, 12, 6});
    }

    @Override
    public String goTo(String goCommandString) {
        // Parse the goCommandString to extract direction and number of steps
        String[] parts = goCommandString.substring(1, goCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        // Update the coordinates of the maintenance droid based on the direction and steps
        int newX = startX;
        int newY = startY;
        if (direction.equals("no")) {
            newY += steps;
        } else if (direction.equals("ea")) {
            newX += steps;
        } else if (direction.equals("so")) {
            newY -= steps;
        } else if (direction.equals("we")) {
            newX -= steps;
        }

        // Check if the new coordinates are within the spaceship deck boundaries
        if (newX < 0) {
            newX = 0;
        } else if (newX >= grid.length) {
            newX = grid.length - 1;
        }
        if (newY < 0) {
            newY = 0;
        } else if (newY >= grid[0].length) {
            newY = grid[0].length - 1;
        }

        // Check if the new coordinates are blocked by any obstacles
        for (int[] obstacle : obstacles) {
            int startX = obstacle[0];
            int startY = obstacle[1];
            int endX = obstacle[2];
            int endY = obstacle[3];

            if ((newX == startX && newY >= startY && newY <= endY) || (newY == startY && newX >= startX && newX <= endX)) {
                // The new coordinates are blocked by an obstacle, reset to the previous coordinates
                newX = startX;
                newY = startY;
                break;
            }
        }

        startX = newX;
        startY = newY;

        // Return the new coordinates as a string in the format "(x, y)"
        return "(" + startX + "," + startY + ")";
    }
}