package thkoeln.st.st2praktikum.exercise.entities;

import javassist.NotFoundException;
import thkoeln.st.st2praktikum.exercise.interfaces.Executable;

public class MoveCommand implements Executable {
    private TidyUpRobot robot;
    private Room room;
    private Direction direction;
    private int steps;

    public MoveCommand(TidyUpRobot robot, Room room, Direction direction, int steps) {
        this.robot = robot;
        this.room = room;
        this.direction = direction;
        this.steps = steps;
    }

    @Override
    public void execute() {
        Coordinate newPosition = new Coordinate(this.robot.currentPosition.x, this.robot.currentPosition.y);

        if(this.robot.currentPosition == null) {
            throw new UnsupportedOperationException();
        }

        switch (direction) {
            case no:
                newPosition.y += steps;
                break;
            case ea:
                newPosition.x += steps;
                break;
            case so:
                newPosition.y -= steps;
                break;
            case we:
                newPosition.x -= steps;
                break;
        }

        Coordinate wallCoordinateBetween = room.coordinateOfBarrierBetween(this.robot.currentPosition, newPosition);

        if(wallCoordinateBetween != null) {
            System.out.println("Wall detected");
            newPosition = wallCoordinateBetween;
            if (direction == Direction.ea) {
                newPosition.x--;
            } else if (direction == Direction.no) {
                newPosition.y--;
            }
        }

        this.robot.currentPosition = newPosition;

        System.out.println("Set robot position to " + this.robot.currentPosition.toString());
    }
}
