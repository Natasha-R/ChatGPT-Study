package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements Walkable {

    private final int MAX_X = 11;  // x coordinates range from 0 to 11
    private final int MAX_Y = 7;   // y coordinates range from 0 to 7


    private Point currentPosition = new Point(3, 0);


    private List<List<Point>> walls;


    public Exercise0() {


        initWalls();
    }


    private void initWalls() {


        walls = new ArrayList<>();

        List<Point> wall1 = List.of(
                new Point(3,0),
                new Point(3,1),
                new Point(3,2),
                new Point(3,3)
        );

        List<Point> wall2 = List.of(
                new Point(7,0),
                new Point(7,1),
                new Point(7,2)
        );

        List<Point> wall3 = List.of(
                new Point(4,3),
                new Point(5,3),
                new Point(6,3),
                new Point(7,3)
        );

        List<Point> wall4 = List.of(
                new Point(1,4),
                new Point(2,4),
                new Point(3,4),
                new Point(4,4),
                new Point(5,4),
                new Point(6,4),
                new Point(7,4),
                new Point(8,4)
        );

        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
    }


    @Override
    public String walkTo(String walkCommandString) {


        if (walkCommandString == null || walkCommandString.isEmpty()) {
            throw new IllegalArgumentException("Invalid command string");
        }


        String command = walkCommandString.replace("[", "").replace("]", "");
        String[] parts = command.split(",");


        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid command string format");
        }


        String direction = parts[0];
        int steps;
        try {
            steps = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number of steps");
        }


        moveRobot(direction, steps);


        return currentPosition.toString();
    }


    private void moveRobot(String direction, int steps) {


        int dx = 0, dy = 0;
        switch (direction) {
            case "no":
                dy = 1;
                break;
            case "ea":
                dx = 1;
                break;
            case "so":
                dy = -1;
                break;
            case "we":
                dx = -1;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }


        for (int i = 0; i < steps; i++) {
            Point nextPosition = new Point(currentPosition.x + dx, currentPosition.y + dy);


            if (!isValidPosition(nextPosition)) {
                break;
            }


            currentPosition = nextPosition;
        }
    }


    private boolean isValidPosition(Point point) {


        if (point.x < 0 || point.x > MAX_X || point.y < 0 || point.y > MAX_Y) {
            return false;
        }


        for (List<Point> wall : walls) {
            if (wall.contains(point)) {
                return false;
            }
        }


        return true;
    }
}


