package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements GoAble {

    private List<Path> walls = new ArrayList<>();
    private Point position = new Point(8, 3);
    private int width = 12;
    private int height = 9;

    public Exercise0() {
        walls.add(new Path(new Point(4, 1), new Point(4, 7)));
        walls.add(new Path(new Point(6, 2), new Point(9, 2)));
        walls.add(new Path(new Point(6, 2), new Point(6, 5)));
        walls.add(new Path(new Point(6, 5), new Point(9, 5)));
    }

    @Override
    public String go(String goCommandString) {
        String direction = goCommandString.substring(1, 3);
        int steps = Integer.parseInt(goCommandString.substring(4, goCommandString.length() - 1));

        Movement movement;

        switch(direction) {
            case "no":
                movement = new Movement(
                        new Point (position.getX(), position.getY()),
                        new Point(position.getX(), position.getY() + steps),
                        Movement.Direction.NORTH
                );
                break;
            case "ea":
                movement = new Movement(
                        new Point (position.getX(), position.getY()),
                        new Point(position.getX() + steps, position.getY()),
                        Movement.Direction.EAST
                );
                break;
            case "so":
                movement = new Movement(
                        new Point (position.getX(), position.getY()),
                        new Point(position.getX(), position.getY() - steps),
                        Movement.Direction.SOUTH
                );
                break;
            case "we":
                movement = new Movement(
                        new Point (position.getX(), position.getY()),
                        new Point(position.getX() - steps, position.getY()),
                        Movement.Direction.WEST
                );
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }

        System.out.println("From: " + movement.getFrom().toString());
        System.out.println("To: " + movement.getTo().toString());

        Point collisionPoint = movement.getLastPossibleMovementPoint(walls, width, height);
        if(collisionPoint != null) {
            System.out.println("Collides with path at " + collisionPoint);
            position = collisionPoint;
            return position.toString();
        }

        System.out.println("");

        position = movement.getTo();

        return position.toString();
    }
}
