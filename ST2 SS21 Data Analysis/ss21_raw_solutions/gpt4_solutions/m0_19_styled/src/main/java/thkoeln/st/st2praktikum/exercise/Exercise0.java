package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Walkable {

    // Declare the variables for the current position of the mining machine
    private int currentX;
    private int currentY;

    // Define the size of the field
    private int fieldSizeX = 12;
    private int fieldSizeY = 9;

    // Create a list to store the wall coordinates
    private List<int[]> walls = new ArrayList<>();

    // Constructor
    public Exercise0() {
        // Set the initial position of the mining machine
        currentX = 0;
        currentY = 2;

        // Add the walls to the list
        walls.add(new int[]{3, 0, 3, 3});
        walls.add(new int[]{5, 0, 5, 4});
        walls.add(new int[]{4, 5, 7, 5});
        walls.add(new int[]{7, 5, 7, 9});
    }

    // Method to move the mining machine
    @Override
    public String walk(String walkCommandString) {
        // Remove the brackets and split the command into direction and steps
        String[] commands = walkCommandString.substring(1, walkCommandString.length()-1).split(",");
        String direction = commands[0];
        int steps = Integer.parseInt(commands[1]);

        // Loop for the number of steps
        for (int i = 0; i < steps; i++) {
            // Check the direction and move the mining machine
            if (direction.equals("no")) {
                if (!isWall(currentX, currentY+1)) {
                    currentY = currentY + 1;
                    if (currentY > fieldSizeY - 1) {
                        currentY = fieldSizeY - 1;
                    }
                }
            } else if (direction.equals("ea")) {
                if (!isWall(currentX+1, currentY)) {
                    currentX = currentX + 1;
                    if (currentX > fieldSizeX - 1) {
                        currentX = fieldSizeX - 1;
                    }
                }
            } else if (direction.equals("so")) {
                if (!isWall(currentX, currentY-1)) {
                    currentY = currentY - 1;
                    if (currentY < 0) {
                        currentY = 0;
                    }
                }
            } else if (direction.equals("we")) {
                if (!isWall(currentX-1, currentY)) {
                    currentX = currentX - 1;
                    if (currentX < 0) {
                        currentX = 0;
                    }
                }
            }
        }
        // Return the final position of the mining machine
        return "(" + currentX + "," + currentY + ")";
    }

    // Method to check if there is a wall at the given position
    private boolean isWall(int x, int y) {
        // Loop over all the walls
        for (int[] wall : walls) {
            // Check if the given position is inside the wall
            if (x == wall[0] || x == wall[2]) {
                if (y >= Math.min(wall[1], wall[3]) && y <= Math.max(wall[1], wall[3])) {
                    return true;
                }
            } else if (y == wall[1] || y == wall[3]) {
                if (x >= Math.min(wall[0], wall[2]) && x <= Math.max(wall[0], wall[2])) {
                    return true;
                }
            }
        }
        // If no wall is found, return false
        return false;
    }
}
