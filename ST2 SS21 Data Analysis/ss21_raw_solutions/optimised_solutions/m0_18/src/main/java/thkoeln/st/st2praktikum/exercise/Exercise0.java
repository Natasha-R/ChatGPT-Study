package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Exercise0 implements Walkable {

    private final Point robotLocation;

    private final int roomWidth;

    private final int roomHeight;

    private final List<Point> walls;

    public Exercise0() {

        this.robotLocation = new Point(3, 0);

        this.roomWidth = 12;

        this.roomHeight = 8;

        this.walls = Arrays.asList(
                new Point(3, 3),
                new Point(7, 2),
                new Point(7, 3),
                new Point(8, 4)
        );
    }

    @Override
    public String walkTo(String walkCommandString) {
        try {

            // Parse and validate command
            String[] command = parseCommand(walkCommandString);

            // Move robot
            moveRobot(command);

        } catch (Exception e) {

            // Handle error
            System.err.println(e.getMessage());
        }

        return getRobotLocation();
    }

    // More functions, tangentially related or not, for possible future needs

    public String getRobotLocation() {
        return String.format("(%d,%d)",
                robotLocation.x,
                robotLocation.y
        );
    }

    public boolean isWallAt(Point location) {
        return walls.contains(location);
    }

    public void validateLocation(Point location) throws Exception {

        if (
                location.x < 0
                        || location.y < 0
                        || location.x >= roomWidth
                        || location.y >= roomHeight
        ) {
            throw new Exception("Invalid location: outside of room.");
        }

        if (isWallAt(location)) {
            throw new Exception("Invalid location: wall detected.");
        }
    }

    private void moveRobot(String[] command) throws Exception {

        Point newLocation = new Point(robotLocation);

        int steps = Integer.parseInt(command[1]);

        switch (command[0]) {

            case "no":
                newLocation.y += steps;
                break;

            case "ea":
                newLocation.x += steps;
                break;

            case "so":
                newLocation.y -= steps;
                break;

            case "we":
                newLocation.x -= steps;
                break;

            default:
                throw new Exception("Invalid direction in command.");
        }

        validateLocation(newLocation);

        robotLocation.setLocation(newLocation);
    }

    private String[] parseCommand(String commandString) throws Exception {

        String command = commandString.replace("[", "").replace("]", "");

        String[] commandParts = command.split(",");

        if (commandParts.length != 2) {
            throw new Exception("Invalid command format.");
        }

        return commandParts;
    }
}