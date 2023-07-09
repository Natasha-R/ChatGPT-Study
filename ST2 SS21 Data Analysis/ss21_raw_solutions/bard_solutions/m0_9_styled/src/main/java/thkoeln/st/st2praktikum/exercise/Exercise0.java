package thkoeln.st.st2praktikum.exercise;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Moveable {

    private int x = 7;
    private int y = 7;
    private int width = 11;
    private int height = 8;
    private List<Point> barriers = new ArrayList<>();

    private Point currentLocation;

    public Exercise0() {
        barriers.add(new Point(2, 1));
        barriers.add(new Point(10, 1));
        barriers.add(new Point(2, 6));
        barriers.add(new Point(7, 6));
        barriers.add(new Point(10, 8));
    }

    @Override
    public String move(String moveCommandString) {
        // Get the direction and number of steps from the move command string.
        String[] tokens = moveCommandString.split(",");
        String direction = tokens[0];
        int steps = Integer.parseInt(tokens[1]);

        // Check if the destination is blocked.
        Point destination = null;
        switch (direction) {
            case "no":
                destination = new Point(x, y - steps);
                break;
            case "ea":
                destination = new Point(x + steps, y);
                break;
            case "so":
                destination = new Point(x, y + steps);
                break;
            case "we":
                destination = new Point(x - steps, y);
                break;
        }

        if (isBlocked(destination)) {
            // If the destination is blocked, find the closest unblocked destination.
            int dx = destination.x - currentLocation.x;
            int dy = destination.y - currentLocation.y;

            for (int i = 1; i <= steps; i++) {
                Point candidate = new Point(currentLocation.x + i * dx, currentLocation.y + i * dy);
                if (!isBlocked(candidate)) {
                    destination = candidate;
                    break;
                }
            }
        }

        // Move the cleaning device to the destination.
        currentLocation = destination;
        x = destination.x;
        y = destination.y;

        // Return the new location of the cleaning device.
        return "(" + x + "," + y + ")";
    }

    private boolean isBlocked(Point point) {
        // Check if the point is outside the space or if it is blocked by a barrier.
        return point.x < 0 || point.x >= width || point.y < 0 || point.y >= height || barriers.contains(point);
    }

    private Point getUnblockedDestination(Point currentLocation, Point destination, int steps) {
        // Get the difference between the current location and the destination.
        int dx = destination.x - currentLocation.x;
        int dy = destination.y - currentLocation.y;

        // For each step, check if the next point is unblocked.
        for (int i = 1; i <= steps; i++) {
            Point candidate = new Point(currentLocation.x + i * dx, currentLocation.y + i * dy);
            if (!isBlocked(candidate)) {
                return candidate;
            }
        }

        // If all the steps are blocked, return the current location.
        return currentLocation;
    }
}