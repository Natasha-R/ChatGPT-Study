package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Walkable {
    private static final int FIELD_WIDTH = 12;
    private static final int FIELD_HEIGHT = 9;

    private Point machinePosition;
    private List<Wall> walls;

    public Exercise0() {
        machinePosition = new Point(0, 2);
        walls = new ArrayList<>();
        walls.add(new Wall(new Point(3, 0), new Point(3, 3)));
        walls.add(new Wall(new Point(5, 0), new Point(5, 4)));
        walls.add(new Wall(new Point(4, 5), new Point(7, 5)));
        walls.add(new Wall(new Point(7, 5), new Point(7, 9)));

        // Add field boundary walls
        walls.add(new Wall(new Point(0, 0), new Point(0, FIELD_HEIGHT)));
        walls.add(new Wall(new Point(0, 0), new Point(FIELD_WIDTH, 0)));
        walls.add(new Wall(new Point(FIELD_WIDTH, 0), new Point(FIELD_WIDTH, FIELD_HEIGHT)));
        walls.add(new Wall(new Point(0, FIELD_HEIGHT), new Point(FIELD_WIDTH, FIELD_HEIGHT)));
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        Point newPosition = new Point(machinePosition);
        for (int i = 0; i < steps; i++) {
            newPosition = getNextPosition(direction, newPosition);
            if (!canMoveTo(newPosition)) {
                break;
            }
            machinePosition = newPosition;
        }
        return "(" + machinePosition.x + "," + machinePosition.y + ")";
    }

    private Point getNextPosition(String direction, Point position) {
        Point newPosition = new Point(position);
        switch (direction) {
            case "no":
                newPosition.y += 1;
                break;
            case "ea":
                newPosition.x += 1;
                break;
            case "so":
                newPosition.y -= 1;
                break;
            case "we":
                newPosition.x -= 1;
                break;
        }
        return newPosition;
    }

    private boolean canMoveTo(Point position) {
        if (position.x < 0 || position.y < 0 || position.x >= FIELD_WIDTH || position.y >= FIELD_HEIGHT) {
            return false;
        }

        for (Wall wall : walls) {
            if (wall.intersects(position)) {
                return false;
            }
        }
        return true;
    }

    private static class Wall {
        Point start;
        Point end;

        Wall(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        boolean intersects(Point position) {
            if (start.x == end.x) {
                // Vertical wall
                return position.y >= start.y && position.y < end.y &&
                        (position.x == start.x || position.x == start.x - 1);
            } else {
                // Horizontal wall
                return position.x >= start.x && position.x < end.x &&
                        (position.y == start.y || position.y == start.y - 1);
            }
        }
    }
}