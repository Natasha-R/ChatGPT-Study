package thkoeln.st.st2praktikum.exercise;

import java.util.*;
import java.awt.Point;

public class Exercise0 implements Walkable {

    private Point currentLocation;
    private final Map<String, Point> directions;
    private final int[][] roomGrid;
    private final int roomWidth = 12;
    private final int roomHeight = 8;

    public Exercise0() {

        this.currentLocation = new Point(3, 0);

        this.roomGrid = new int[roomWidth][roomHeight];

        this.directions = new HashMap<>();
        directions.put("no", new Point(0, 1));
        directions.put("ea", new Point(1, 0));
        directions.put("so", new Point(0, -1));
        directions.put("we", new Point(-1, 0));

        initializeWalls();
    }

    private void initializeWalls() {

        for (int x = 0; x < roomWidth; x++) {
            for (int y = 0; y < roomHeight; y++) {
                roomGrid[x][y] = (x == 0 || x == roomWidth-1 || y == 0 || y == roomHeight-1) ? 1 : 0;
            }
        }

        for (int y = 0; y <= 3; y++) roomGrid[3][y] = 1;
        for (int y = 0; y <= 2; y++) roomGrid[7][y] = 1;
        for (int x = 4; x <= 7; x++) roomGrid[x][3] = 1;
        for (int x = 1; x <= 8; x++) roomGrid[x][4] = 1;
    }

    public String walkTo(String walkCommandString) {

        String direction = walkCommandString.substring(1, 3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));
        Point directionVector = directions.get(direction);

        if (directionVector == null) return "Invalid command";

        while (steps > 0) {
            Point nextLocation = new Point(currentLocation.x + directionVector.x, currentLocation.y + directionVector.y);
            if (!isFree(nextLocation)) break;
            currentLocation = nextLocation;
            steps--;
        }

        return formatLocation();
    }

    private boolean isFree(Point location) {

        if (location.x < 0 || location.y < 0 || location.x >= roomWidth || location.y >= roomHeight) return false;

        return roomGrid[location.x][location.y] == 0;
    }

    private String formatLocation() {

        return String.format("(%d,%d)", currentLocation.x, currentLocation.y);
    }

    public boolean isValidCoordinate(int x, int y) {

        return x >= 0 && x < roomWidth && y >= 0 && y < roomHeight;
    }

    public void addWall(int x, int y) {

        if (isValidCoordinate(x, y)) roomGrid[x][y] = 1;
    }

    public void removeWall(int x, int y) {

        if (isValidCoordinate(x, y)) roomGrid[x][y] = 0;
    }

    public String getCellStatus(int x, int y) {

        if (!isValidCoordinate(x, y)) return "Invalid coordinate";

        return roomGrid[x][y] == 0 ? "Free" : "Wall";
    }

    public String getRoomLayout() {

        StringBuilder sb = new StringBuilder();

        for (int y = roomHeight - 1; y >= 0; y--) {
            for (int x = 0; x < roomWidth; x++) {
                sb.append(roomGrid[x][y] == 0 ? "." : "#");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}


