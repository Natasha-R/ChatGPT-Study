package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Moveable {

    private int x = 7;
    private int y = 7;
    private int width = 11;
    private int height = 8;
    private List<java.awt.Point> barriers = new ArrayList<>();

    public Exercise0() {
        barriers.add(new java.awt.Point(2, 1));
        barriers.add(new java.awt.Point(10, 1));
        barriers.add(new java.awt.Point(2, 6));
        barriers.add(new java.awt.Point(7, 6));
        barriers.add(new java.awt.Point(10, 8));
    }

    @Override
    public String move(String moveCommandString) {
        String[] tokens = moveCommandString.split(",");
        String direction = tokens[0];
        int steps = Integer.parseInt(tokens[1]);

        Point newPosition;
        switch (direction) {
            case "no":
                newPosition = new Point(x, y - steps);
                break;
            case "ea":
                newPosition = new Point(x + steps, y);
                break;
            case "so":
                newPosition = new Point(x, y + steps);
                break;
            case "we":
                newPosition = new Point(x - steps, y);
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        if (isWithinBounds(newPosition) && !isBlocked(newPosition)) {
            x = newPosition.x;
            y = newPosition.y;
            return "(" + x + "," + y + ")";
        } else {
            return "Cannot move to " + newPosition + ".";
        }
    }

    private boolean isWithinBounds(Point point) {
        return point.x >= 0 && point.x < width && point.y >= 0 && point.y < height;
    }

    private boolean isBlocked(Point point) {
        for (Point barrier : barriers) {
            if (barrier.x == point.x && barrier.y == point.y) {
                return true;
            }
        }
        return false;
    }
}