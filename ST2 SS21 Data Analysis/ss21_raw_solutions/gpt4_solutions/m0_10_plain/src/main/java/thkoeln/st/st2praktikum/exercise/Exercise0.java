package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    private static final int FIELD_WIDTH = 12;
    private static final int FIELD_HEIGHT = 9;
    private Point miningMachineLocation;
    private List<String> walls;

    public Exercise0() {
        miningMachineLocation = new Point(0, 2);
        walls = Arrays.asList("3,0-3,3", "5,0-5,4", "4,5-7,5", "7,5-7,9");
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        for (int i = 0; i < steps; i++) {
            Point newPosition = new Point(miningMachineLocation);
            switch (direction) {
                case "no":
                    newPosition.y++;
                    break;
                case "ea":
                    newPosition.x++;
                    break;
                case "so":
                    newPosition.y--;
                    break;
                case "we":
                    newPosition.x--;
                    break;
            }

            if (isValidPosition(newPosition)) {
                miningMachineLocation = newPosition;
            } else {
                break;
            }
        }

        return String.format("(%d,%d)", miningMachineLocation.x, miningMachineLocation.y);
    }

    private boolean isValidPosition(Point position) {
        if (position.x < 0 || position.x >= FIELD_WIDTH || position.y < 0 || position.y >= FIELD_HEIGHT) {
            return false;
        }

        for (String wall : walls) {
            String[] coordinates = wall.split("-");
            String[] startCoords = coordinates[0].split(",");
            String[] endCoords = coordinates[1].split(",");

            int startX = Integer.parseInt(startCoords[0]);
            int startY = Integer.parseInt(startCoords[1]);
            int endX = Integer.parseInt(endCoords[0]);
            int endY = Integer.parseInt(endCoords[1]);

            if (startX == endX) {
                if (position.x == startX && position.y >= startY && position.y < endY) {
                    return false;
                }
            } else if (startY == endY) {
                if (position.y == startY && position.x >= startX && position.x < endX) {
                    return false;
                }
            }
        }

        return true;
    }
}