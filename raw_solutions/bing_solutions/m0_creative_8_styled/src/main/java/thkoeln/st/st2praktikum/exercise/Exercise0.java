package thkoeln.st.st2praktikum.exercise;

import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    // The size of the room
    private static final int WIDTH = 12;
    private static final int HEIGHT = 8;

    // The starting location of the robot
    private int x = 3;
    private int y = 0;

    // The walls in the room
    private List<Wall> walls = Arrays.asList(
            new Wall(3, 0, 3, 3),
            new Wall(4, 3, 7, 3),
            new Wall(7, 0, 7, 2),
            new Wall(1, 4, 8, 4)
    );

    // The directions for the robot
    private enum Direction {
        NO, EA, SO, WE
    }

    // A helper class to represent a wall
    private static class Wall {
        int x1;
        int y1;
        int x2;
        int y2;

        public Wall(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    // The method to move the robot according to a command
    @Override
    public String walkTo(String walkCommandString) {
        // Parse the command string
        walkCommandString = walkCommandString.trim();
        if (!walkCommandString.startsWith("[") || !walkCommandString.endsWith("]")) {
            System.out.println("Invalid command format");
            return null;
        }
        walkCommandString = walkCommandString.substring(1, walkCommandString.length() - 1);
        String[] parts = walkCommandString.split(",");
        if (parts.length != 2) {
            System.out.println("Invalid command format");
            return null;
        }
        Direction direction = Direction.valueOf(parts[0].toUpperCase());
        int steps = Integer.parseInt(parts[1]);

        // Move the robot according to the direction and steps
        if (direction == Direction.NO) {
            moveNorth(steps);
        } else if (direction == Direction.EA) {
            moveEast(steps);
        } else if (direction == Direction.SO) {
            moveSouth(steps);
        } else if (direction == Direction.WE) {
            moveWest(steps);
        } else {
            System.out.println("Invalid direction");
            return null;
        }

        // Return the current location of the robot
        return "(" + x + "," + y + ")";
    }

    // A helper method to move the robot north
    private void moveNorth(int steps) {
        // Check if there is a wall or a boundary above the robot
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.x1 == wall.x2 && wall.x1 == x && wall.y1 <= y && wall.y2 >= y + steps) {
                // The wall blocks the robot from moving north
                steps = wall.y1 - y - 1;
                break;
            }
        }
        if (y + steps >= HEIGHT) {
            // The boundary blocks the robot from moving north
            steps = HEIGHT - y - 1;
        }
        // Update the y coordinate of the robot
        y = y + steps;
    }

    // A helper method to move the robot east
    private void moveEast(int steps) {
        // Check if there is a wall or a boundary to the right of the robot
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.y1 == wall.y2 && wall.y1 == y && wall.x1 <= x && wall.x2 >= x + steps) {
                // The wall blocks the robot from moving east
                steps = wall.x1 - x - 1;
                break;
            }
        }
        if (x + steps >= WIDTH) {
            // The boundary blocks the robot from moving east
            steps = WIDTH - x - 1;
        }
        // Update the x coordinate of the robot
        x = x + steps;
    }

    // A helper method to move the robot south
    private void moveSouth(int steps) {
        // Check if there is a wall or a boundary below the robot
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.x1 == wall.x2 && wall.x1 == x && wall.y1 <= y - steps && wall.y2 >= y) {
                // The wall blocks the robot from moving south
                steps = y - wall.y2 - 1;
                break;
            }
        }
        if (y - steps < 0) {
            // The boundary blocks the robot from moving south
            steps = y;
        }
        // Update the y coordinate of the robot
        y = y - steps;
    }

    // A helper method to move the robot west
    private void moveWest(int steps) {
        // Check if there is a wall or a boundary to the left of the robot
        for (int i = 0; i < walls.size(); i++) {
            Wall wall = walls.get(i);
            if (wall.y1 == wall.y2 && wall.y1 == y && wall.x1 <= x - steps && wall.x2 >= x) {
                // The wall blocks the robot from moving west
                steps = x - wall.x2 - 1;
                break;
            }
        }
        if (x - steps < 0) {
            // The boundary blocks the robot from moving west
            steps = x;
        }
        // Update the x coordinate of the robot
        x = x - steps;
    }

}
