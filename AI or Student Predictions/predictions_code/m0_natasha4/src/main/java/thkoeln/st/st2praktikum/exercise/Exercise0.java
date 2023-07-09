package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class Exercise0 implements Moveable {

    private static final int MAX_X = 11;
    private static final int MAX_Y = 8;
    private Coordinate currentPosition;
    private List<Obstacle> obstacles;

    public Exercise0() {
        currentPosition = new Coordinate(0, 2);
        createObstacles();
    }

    @Override
    public String moveTo(String moveCommandString) {
        Command command = Command.parseCommand(moveCommandString);
        moveDroid(command);
        return currentPosition.toString();
    }

    private void moveDroid(Command command) {
        int dx = 0, dy = 0;

        switch (command.getDirection()) {
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
        }

        for (int i = 0; i < command.getSteps(); i++) {
            Coordinate nextPosition = new Coordinate(currentPosition.getX() + dx, currentPosition.getY() + dy);
            if (canMove(currentPosition, nextPosition, command.getDirection())) {
                currentPosition = nextPosition;
            } else {
                break;
            }
        }
    }

    private boolean canMove(Coordinate current, Coordinate next, String direction) {

        if (next.getX() < 0 || next.getX() > MAX_X || next.getY() < 0 || next.getY() > MAX_Y) {
            return false;
        }
        if (direction.equals("ea") || direction.equals("no")) {
            for (Obstacle obstacle : obstacles) {
                if (obstacle.isInPathOfMovement(current, next)) {
                    return false;
                }
            }
        } else {
            for (Obstacle obstacle : obstacles) {
                if (obstacle.isInPathOfMovement(next, current)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void createObstacles() {
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new Coordinate(3, 0), new Coordinate(3, 3)));
        obstacles.add(new Obstacle(new Coordinate(5, 0), new Coordinate(5, 4)));
        obstacles.add(new Obstacle(new Coordinate(4, 5), new Coordinate(7, 5)));
        obstacles.add(new Obstacle(new Coordinate(7, 5), new Coordinate(7, 9)));
    }
}

