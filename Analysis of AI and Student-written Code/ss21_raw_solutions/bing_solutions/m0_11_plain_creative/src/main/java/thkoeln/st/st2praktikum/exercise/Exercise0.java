package thkoeln.st.st2praktikum.exercise;

// A class that implements the Walkable interface
public class Exercise0 implements Walkable {

    // A nested class that represents a cell in the room
    public static class Cell {
        // The x and y coordinates of the cell
        private int x;
        private int y;

        // A constructor that takes the x and y coordinates as parameters
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // A getter method for the x coordinate
        public int getX() {
            return x;
        }

        // A getter method for the y coordinate
        public int getY() {
            return y;
        }

        // A setter method for the x coordinate
        public void setX(int x) {
            this.x = x;
        }

        // A setter method for the y coordinate
        public void setY(int y) {
            this.y = y;
        }

        // A method that returns a string representation of the cell
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    // A nested class that represents a wall in the room
    public static class Wall {
        // The start and end cells of the wall
        private Cell start;
        private Cell end;

        // A constructor that takes the start and end cells as parameters
        public Wall(Cell start, Cell end) {
            this.start = start;
            this.end = end;
        }

        // A getter method for the start cell
        public Cell getStart() {
            return start;
        }

        // A getter method for the end cell
        public Cell getEnd() {
            return end;
        }
    }

    // The width and height of the room
    private int width;
    private int height;

    // The array of walls in the room
    private Wall[] walls;

    // The current cell of the tidy-up robot
    private Cell current;

    // A constructor that takes no parameters and uses hardcoded values
    public Exercise0() {
        // The width and height of the room are 12 and 8 respectively
        this.width = 12;
        this.height = 8;

        // The array of walls in the room has four elements
        this.walls = new Wall[4];

        // The first wall is between (3,0) and (3,3)
        this.walls[0] = new Wall(new Cell(3, 0), new Cell(3, 3));

        // The second wall is between (4,3) and (7,3)
        this.walls[1] = new Wall(new Cell(4, 3), new Cell(7, 3));

        // The third wall is between (7,0) and (7,2)
        this.walls[2] = new Wall(new Cell(7, 0), new Cell(7, 2));

        // The fourth wall is between (1,4) and (8,4)
        this.walls[3] = new Wall(new Cell(1, 4), new Cell(8, 4));

        // The current cell of the tidy-up robot is (3,0)
        this.current = new Cell(3, 0);
    }

    // A method that implements the walkTo interface
    public String walkTo(String walkCommandString) {

        // Parse the walk command string into direction and steps
        String direction = walkCommandString.substring(1, 3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));

        // Initialize variables for the new x and y coordinates of the robot
        int newX = current.getX();
        int newY = current.getY();

        // Switch on the direction and update the new coordinates accordingly
        switch (direction) {
            case "no":
                newY += steps; // Move north by adding steps to y coordinate
                break;
            case "ea":
                newX += steps; // Move east by adding steps to x coordinate
                break;
            case "so":
                newY -= steps; // Move south by subtracting steps from y coordinate
                break;
            case "we":
                newX -= steps; // Move west by subtracting steps from x coordinate
                break;
            default:
                return "Invalid direction"; // Return an error message if direction is invalid
        }
        // Check if the new coordinates are within the room boundaries, and adjust them if not
        if (newX < 0) {
            newX = 0; // Cannot move beyond the left boundary
        }
        if (newX >= width) {
            newX = width - 1; // Cannot move beyond the right boundary
        }
        if (newY < 0) {
            newY = 0; // Cannot move beyond the bottom boundary
        }
        if (newY >= height) {
            newY = height - 1; // Cannot move beyond the top boundary
        }

// Create a new cell object with the new coordinates
        Cell newCell = new Cell(newX, newY);

// Check if there is a wall between the current cell and the new cell, and adjust them if there is
        for (Wall wall : walls) {

            // Get the start and end cells of the wall
            Cell wallStart = wall.getStart();
            Cell wallEnd = wall.getEnd();

            // Check if the wall is horizontal or vertical
            boolean horizontal = wallStart.getY() == wallEnd.getY();
            boolean vertical = wallStart.getX() == wallEnd.getX();

            if (horizontal) {
                // If horizontal, check if it blocks any movement along y axis

                // Get the minimum and maximum x coordinates of the wall segment
                int minX = Math.min(wallStart.getX(), wallEnd.getX());
                int maxX = Math.max(wallStart.getX(), wallEnd.getX());

                // Get the y coordinate of the wall segment
                int y = wallStart.getY();

                if (current.getY() < y && newCell.getY() >= y && newX >= minX && newX <= maxX) {
                    newCell.setY(y - 1); // If moving up and hitting a horizontal wall, stop below it
                }
                if (current.getY() > y && newCell.getY() <= y && newX >= minX && newX <= maxX) {
                    newCell.setY(y + 1); // If moving down and hitting a horizontal wall, stop above it
                }

            } else if (vertical) {
                // If vertical, check if it blocks any movement along x axis

                // Get the minimum and maximum y coordinates of the wall segment
                int minY = Math.min(wallStart.getY(), wallEnd.getY());
                int maxY = Math.max(wallStart.getY(), wallEnd.getY());

                // Get the x coordinate of the wall segment
                int x = wallStart.getX();

                if (current.getX() < x && newCell.getX() >= x && newY >= minY && newY <= maxY) {
                    newCell.setX(x - 1); // If moving right and hitting a vertical wall, stop left of it
                }
                if (current.getX() > x && newCell.getX() <= x && newY >= minY && newY <= maxY) {
                    newCell.setX(x + 1); // If moving left and hitting a vertical wall, stop right of it
                }

            }
        }

// Update the current cell with the new cell
        current = newCell;

// Return a string representation of the current cell
        return current.toString();
    }
}