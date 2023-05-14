package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements GoAble {

    private Point droidLocation;
    private final int maxX;
    private final int maxY;
    private final List<Point[]> obstaclePairs;

    public Exercise0() {
        this.droidLocation = new Point(11, 7);
        this.maxX = 11;
        this.maxY = 7;

        // Initialize obstacle pairs
        this.obstaclePairs = new ArrayList<>();
        generateObstaclePairs(new Point(6,2), new Point(6,5));
        generateObstaclePairs(new Point(5,5), new Point(6,5));
        generateObstaclePairs(new Point(5,5), new Point(5,6));
        generateObstaclePairs(new Point(5,6), new Point(12,6));
    }

    @Override
    public String goTo(String goCommandString) {
        // Remove brackets and split command into direction and steps
        String[] commands = goCommandString.replace("[", "").replace("]", "").split(",");
        String direction = commands[0];
        int steps = Integer.parseInt(commands[1]);

        // Determine new position based on direction
        for (int i = 0; i < steps; i++) {
            Point newLocation = new Point(droidLocation);
            switch (direction) {
                case "no": newLocation.y++; break;
                case "ea": newLocation.x++; break;
                case "so": newLocation.y--; break;
                case "we": newLocation.x--; break;
            }

            // Check if new location is valid
            if (isValidMovement(droidLocation, newLocation)) {
                droidLocation = newLocation;
            } else {
                break;
            }
        }

        // Return new location
        return "(" + droidLocation.x + "," + droidLocation.y + ")";
    }

    private boolean isValidMovement(Point oldLocation, Point newLocation) {
        // Check if location is within boundaries
        if (newLocation.x < 0 || newLocation.x > maxX || newLocation.y < 0 || newLocation.y > maxY) {
            return false;
        }

        // Check if the new location is an obstacle
        for (Point[] obstaclePair : obstaclePairs) {
            if (oldLocation.equals(obstaclePair[0]) && newLocation.equals(obstaclePair[1]) ||
                    oldLocation.equals(obstaclePair[1]) && newLocation.equals(obstaclePair[0])) {
                return false;
            }
        }

        return true;
    }

    private void generateObstaclePairs(Point start, Point end) {
        if(start.x == end.x) {
            // Vertical obstacle
            for(int i = start.y; i < end.y; i++) {
                obstaclePairs.add(new Point[]{new Point(start.x - 1, i), new Point(start.x, i)});
            }
        } else if(start.y == end.y) {
            // Horizontal obstacle
            for(int i = start.x; i < end.x; i++) {
                obstaclePairs.add(new Point[]{new Point(i, start.y - 1), new Point(i, start.y)});
            }
        }
    }
}