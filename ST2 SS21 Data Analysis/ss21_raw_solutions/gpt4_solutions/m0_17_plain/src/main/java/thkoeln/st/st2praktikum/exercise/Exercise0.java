package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Walkable {

    private int width = 12;
    private int height = 9;
    private Point machinePosition;
    private List<Point[]> walls;

    public Exercise0() {
        machinePosition = new Point(0, 2);
        walls = new ArrayList<>(Arrays.asList(
                new Point[]{new Point(3, 0), new Point(3, 3)},
                new Point[]{new Point(5, 0), new Point(5, 4)},
                new Point[]{new Point(4, 5), new Point(7, 5)},
                new Point[]{new Point(7, 5), new Point(7, 9)}));
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (isValidMove(machinePosition.x, machinePosition.y + 1)) {
                        machinePosition.y++;
                    } else {
                        break;
                    }
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (isValidMove(machinePosition.x + 1, machinePosition.y)) {
                        machinePosition.x++;
                    } else {
                        break;
                    }
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (isValidMove(machinePosition.x, machinePosition.y - 1)) {
                        machinePosition.y--;
                    } else {
                        break;
                    }
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (isValidMove(machinePosition.x - 1, machinePosition.y)) {
                        machinePosition.x--;
                    } else {
                        break;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid walkCommandString format");
        }

        return String.format("(%d,%d)", machinePosition.x, machinePosition.y);
    }

    private boolean isValidMove(int newX, int newY) {
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return false;
        }

        for (Point[] wall : walls) {
            if (isCollidingWithWall(newX, newY, wall)) {
                return false;
            }
        }

        return true;
    }

    private boolean isCollidingWithWall(int newX, int newY, Point[] wall) {
        if (wall[0].x == wall[1].x) {
            if (newY >= Math.min(wall[0].y, wall[1].y) && newY < Math.max(wall[0].y, wall[1].y)) {
                if (newX == wall[0].x - 1 && machinePosition.x == newX || newX == wall[0].x && machinePosition.x == newX - 1) {
                    return true;
                }
            }
        } else if (wall[0].y == wall[1].y) {
            if (newX >= Math.min(wall[0].x, wall[1].x) && newX < Math.max(wall[0].x, wall[1].x)) {
                if (newY == wall[0].y - 1 && machinePosition.y == newY || newY == wall[0].y && machinePosition.y == newY - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
