package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Exercise0 implements Walkable {
    // Define the field's size
    private final int FIELD_WIDTH = 12;
    private final int FIELD_HEIGHT = 9;
    // Define the starting position of the mining machine
    private Point miningMachineLocation;

    // Constructor
    public Exercise0() {
        // Initialize mining machine's location
        miningMachineLocation = new Point(0, 2);
    }

    // Implement the walk method as required by the Walkable interface
    @Override
    public String walk(String walkCommandString) {
        // Remove brackets from the command and split it into direction and steps
        String[] parts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = parts[0].trim(); // Get direction
        int steps = Integer.parseInt(parts[1].trim()); // Get steps

        // Loop for the number of steps
        for (int i = 0; i < steps; i++) {
            // Make a copy of the current location
            Point newLocation = new Point(miningMachineLocation);

            // Move the machine based on the direction
            switch (direction) {
                case "no": // Move north
                    newLocation.y += 1;
                    break;
                case "ea": // Move east
                    newLocation.x += 1;
                    break;
                case "so": // Move south
                    newLocation.y -= 1;
                    break;
                case "we": // Move west
                    newLocation.x -= 1;
                    break;
            }

            // Check if the move is valid
            if (isValidMove(newLocation)) {
                // If it is, update the machine's location
                miningMachineLocation = newLocation;
            } else {
                // If it's not, stop moving
                break;
            }
        }

        // Return the machine's location in the required format
        return String.format("(%d,%d)", miningMachineLocation.x, miningMachineLocation.y);
    }

    // Helper method to check if a move is valid
    private boolean isValidMove(Point newLocation) {
        // Check if the move is within the field's boundaries
        if (newLocation.x < 0 || newLocation.x >= FIELD_WIDTH
                || newLocation.y < 0 || newLocation.y >= FIELD_HEIGHT) {
            return false;
        }

        // Check for collisions with walls
        if ((newLocation.x == 3 && newLocation.y < 3)
                || (newLocation.x == 5 && newLocation.y < 4)
                || (newLocation.y == 5 && newLocation.x >= 4 && newLocation.x < 7)
                || (newLocation.x == 7 && newLocation.y >= 5)) {
            return false;
        }

        // If no collisions or boundary violations, the move is valid
        return true;
    }
}


