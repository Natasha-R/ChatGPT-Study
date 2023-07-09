package thkoeln.st.st2praktikum.exercise;

// A class that represents a robot that can walk in a room with walls
public class Exercise0 implements Walkable {

    // Constants for the room size and wall locations
    public static final int ROOM_WIDTH = 12;
    public static final int ROOM_HEIGHT = 8;
    public static final int[][] WALLS = {{3,0,3,3}, {4,3,7,3}, {7,0,7,2}, {1,4,8,4}};

    // Fields for the robot's position and direction
    private int x; // x-coordinate of the robot
    private int y; // y-coordinate of the robot
    private String dir; // direction of the robot (no, ea, so, we)

    // Constructor that sets the initial position and direction of the robot
    public Exercise0() {
        x = 3;
        y = 0;
        dir = "no";
    }

    // Method that takes a walk command string and returns the final position of the robot
    public String walkTo(String walkCommandString) {
        updateDirection(walkCommandString);
        moveRobot(walkCommandString);
        return getPosition();
    }

    // Method that updates the robot's direction based on the input
    private void updateDirection(String walkCommandString) {
        dir = parseDirection(walkCommandString);
    }

    // Method that moves the robot as many steps as possible without hitting a wall or boundary
    private void moveRobot(String walkCommandString) {
        int steps = parseSteps(walkCommandString);
        while (steps > 0 && isValidMove()) {
            updatePosition();
            steps--;
        }
    }

    // Method that returns the final position of the robot as a string in the format "(x,y)"
    private String getPosition() {
        return "(" + x + "," + y + ")";
    }

    // Method that parses the direction from the walk command string
    private String parseDirection(String walkCommandString) {
        walkCommandString = removeBrackets(walkCommandString);
        String[] parts = splitByComma(walkCommandString);
        return parts[0];
    }

    // Method that parses the steps from the walk command string
    private int parseSteps(String walkCommandString) {
        walkCommandString = removeBrackets(walkCommandString);
        String[] parts = splitByComma(walkCommandString);
        return Integer.parseInt(parts[1]);
    }

    // Method that removes brackets from a string
    private String removeBrackets(String s) {
        return s.replace("[", "").replace("]", "");
    }

    // Method that splits a string by comma
    private String[] splitByComma(String s) {
        return s.split(",");
    }

    // Method that checks if the next move is valid based on the current position and direction
    private boolean isValidMove() {
        return !isBoundaryCrossed() && !isWallCrossed();
    }

    // Method that updates the position based on the direction
    private void updatePosition() {
        switch (dir) {
            case "no":
                y++;
                break;
            case "ea":
                x++;
                break;
            case "so":
                y--;
                break;
            case "we":
                x--;
                break;
        }
    }

    // Method that checks if the next move would cross a room boundary
    private boolean isBoundaryCrossed() {
        return (x == 0 && dir.equals("we")) ||
                (x == ROOM_WIDTH - 1 && dir.equals("ea")) ||
                (y == 0 && dir.equals("so")) ||
                (y == ROOM_HEIGHT - 1 && dir.equals("no"));
    }

    // Method that checks if the next move would cross a wall
    private boolean isWallCrossed() {
        for (int[] wall : WALLS) {
            if (isVerticalWall(wall) && isSideOfWall(wall)) return true;
            if (isHorizontalWall(wall) && isAboveOrBelowWall(wall)) return true;
        }
        return false;
    }

    // Method that checks if a wall is vertical
    private boolean isVerticalWall(int[] wall) {
        int x1 = wall[0];
        int x2 = wall[2];
        return x1 == x2;
    }

    // Method that checks if a wall is horizontal
    private boolean isHorizontalWall(int[] wall) {
        int y1 = wall[1];
        int y2 = wall[3];
        return y1 == y2;
    }

    // Method that checks if the robot is on the side of a vertical wall
    private boolean isSideOfWall(int[] wall) {
        int x1 = wall[0];
        int y1 = wall[1];
        int y2 = wall[3];
        return (dir.equals("ea") && x == x1 - 1 && y >= y1 && y <= y2) ||
                (dir.equals("we") && x == x1 && y >= y1 && y <= y2);
    }

    // Method that checks if the robot is above or below a horizontal wall
    private boolean isAboveOrBelowWall(int[] wall) {
        int x1 = wall[0];
        int x2 = wall[2];
        int y1 = wall[1];
        return (dir.equals("no") && y == y1 - 1 && x >= x1 && x <= x2) ||
                (dir.equals("so") && y == y1 && x >= x1 && x <= x2);
    }
}

