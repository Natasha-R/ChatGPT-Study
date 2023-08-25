package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Walkable {
    private Point robotPosition;
    private final int roomWidth;
    private final int roomHeight;
    private final boolean[][] walls;
    private final List<Point> wallCoordinates;

    public Exercise0() {
        this.roomWidth = 12;
        this.roomHeight = 8;
        // Initialize the robot's position
        robotPosition = new Point(3, 0);
        // Initialize the walls
        walls = new boolean[roomWidth + 1][roomHeight + 1];
        wallCoordinates = new ArrayList<>();
        // Set the initial walls
        initializeRoom();
    }

    private void initializeRoom() {
        addWall(new Point(3, 0), new Point(3, 3));
        addWall(new Point(7, 0), new Point(7, 2));
        addWall(new Point(4, 3), new Point(7, 3));
        addWall(new Point(1, 4), new Point(8, 4));
    }

    public void addWall(Point from, Point to) {
        // Assuming walls run either vertically or horizontally
        if (from.x == to.x) {
            for (int y = from.y; y <= to.y; y++) {
                walls[from.x][y] = true;
                wallCoordinates.add(new Point(from.x, y));
            }
        } else if (from.y == to.y) {
            for (int x = from.x; x <= to.x; x++) {
                walls[x][from.y] = true;
                wallCoordinates.add(new Point(x, from.y));
            }
        }
    }

    public void removeWall(Point from, Point to) {
        // Assuming walls run either vertically or horizontally
        if (from.x == to.x) {
            for (int y = from.y; y <= to.y; y++) {
                walls[from.x][y] = false;
                wallCoordinates.remove(new Point(from.x, y));
            }
        } else if (from.y == to.y) {
            for (int x = from.x; x <= to.x; x++) {
                walls[x][from.y] = false;
                wallCoordinates.remove(new Point(x, from.y));
            }
        }
    }

    public void moveTo(Point position) {
        if (position.x >= 0 && position.y >= 0 && position.x < roomWidth && position.y < roomHeight) {
            this.robotPosition = position;
        }
    }

    public boolean isAccessible(Point position) {
        if (position.x < 0 || position.y < 0 || position.x >= roomWidth || position.y >= roomHeight) {
            return false; // out of bounds
        }
        return !walls[position.x][position.y]; // accessible if not a wall
    }

    @Override
    public String walkTo(String walkCommandString) {
        // Parse the command string
        String[] commandParts = walkCommandString.replaceAll("[\\[\\]]", "").split(",");
        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        // Perform the walk command
        for (int i = 0; i < steps; i++) {
            // Check for North direction
            if (direction.equals("no")) {
                if (!isWall(robotPosition.x, robotPosition.y + 1)) {
                    robotPosition.y++;
                }
            }
            // Check for South direction
            else if (direction.equals("so")) {
                if (!isWall(robotPosition.x, robotPosition.y - 1)) {
                    robotPosition.y--;
                }
            }
            // Check for West direction
            else if (direction.equals("we")) {
                if (!isWall(robotPosition.x - 1, robotPosition.y)) {
                    robotPosition.x--;
                }
            }
            // Check for East direction
            else if (direction.equals("ea")) {
                if (!isWall(robotPosition.x + 1, robotPosition.y)) {
                    robotPosition.x++;
                }
            }
            // If the direction is not recognized
            else {
                throw new IllegalArgumentException("The direction " + direction + " is not recognized.");
            }
        }

        // Return the new position
        return "(" + robotPosition.x + "," + robotPosition.y + ")";
    }

    private boolean isWall(int x, int y) {
        if (x < 0 || y < 0 || x >= roomWidth || y >= roomHeight) {
            return true; // out of bounds
        }
        return walls[x][y]; // check for internal walls
    }
}
