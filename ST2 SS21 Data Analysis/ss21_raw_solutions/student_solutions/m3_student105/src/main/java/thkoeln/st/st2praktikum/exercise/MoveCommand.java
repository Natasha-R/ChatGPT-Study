package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.exceptions.ParseException;
import thkoeln.st.st2praktikum.exercise.interfaces.Controller;
import thkoeln.st.st2praktikum.exercise.interfaces.Moveable;

import java.util.ArrayList;

@Getter
public class MoveCommand extends Command {
    public Coordinate startPosition;
    public Coordinate endPosition;
    public Direction direction;
    private Moveable affected;

    public MoveCommand(Coordinate from, Coordinate to, Moveable affected) {
        this.startPosition = from;
        this.endPosition = to;

        this.affected = affected;

        if (to.getX() > from.getX()) {
            direction = Direction.EAST;
        } else if (to.getX() < from.getX()) {
            direction = Direction.WEST;
        } else if (to.getY() > from.getY()) {
            direction = Direction.NORTH;
        } else if (to.getY() < from.getY()) {
            direction = Direction.SOUTH;
        }
    }

    @Override
    public boolean executeOn(Controller controller) {
        controller.move(this);
        return true;
    }

    public static MoveCommand build(String directionString, String distanceString, MaintenanceDroid droid) throws ParseException {
        MoveCommand moveCommand;
        int distance = Integer.parseInt(distanceString);

        if (directionString.startsWith("n")) {
            moveCommand = new MoveCommand(droid.currentPosition, new Coordinate(droid.currentPosition.getX(), droid.currentPosition.getY() + distance), droid);
        } else if (directionString.startsWith("s")) {
            moveCommand = new MoveCommand(droid.currentPosition, new Coordinate(droid.currentPosition.getX(), droid.currentPosition.getY() - distance), droid);
        } else if (directionString.startsWith("e")) {
            moveCommand = new MoveCommand(droid.currentPosition, new Coordinate(droid.currentPosition.getX() + distance, droid.currentPosition.getY()), droid);
        } else if (directionString.startsWith("w")) {
            moveCommand = new MoveCommand(droid.currentPosition, new Coordinate(droid.currentPosition.getX() - distance, droid.currentPosition.getY()), droid);
        } else {
            throw new ParseException("Could not parse command direction: " + directionString);
        }

        if (moveCommand.endPosition.getX() > droid.grid.getWidth()) {
            moveCommand.endPosition.setX(droid.grid.getWidth());
        } else if (moveCommand.endPosition.getX() < 0) {
            moveCommand.endPosition.setX(0);
        }
        if (moveCommand.endPosition.getY() > droid.grid.getHeight()) {
            moveCommand.endPosition.setY(droid.grid.getHeight());
        } else if (moveCommand.endPosition.getY() < 0) {
            moveCommand.endPosition.setY(0);
        }

        moveCommand.affected = droid;
        return moveCommand;
    }

    public ArrayList<MoveCommand> getSingleSteps() {
        ArrayList<MoveCommand> moveCommands = new ArrayList<>(11);
        switch (this.direction) {
            case NORTH:
                for (int i = this.startPosition.getY(); i < this.endPosition.getY(); i++) {
                    try {
                        moveCommands.add(new MoveCommand(new Coordinate(this.startPosition.getX(), i), new Coordinate(this.endPosition.getX(), i + 1), affected));
                    } catch (RuntimeException re) {
                        break;
                    }
                }
                break;
            case SOUTH:
                for (int i = this.startPosition.getY(); i >= this.endPosition.getY(); i--) {
                    try {
                        moveCommands.add(new MoveCommand(new Coordinate(this.startPosition.getX(), i), new Coordinate(this.startPosition.getX(), i - 1), affected));
                    } catch (RuntimeException re) {
                        break;
                    }
                }
                break;
            case EAST:
                for (int i = this.startPosition.getX(); i < this.endPosition.getX(); i++) {
                    try {
                        moveCommands.add(new MoveCommand(new Coordinate(i, this.startPosition.getY()), new Coordinate(i + 1, this.endPosition.getY()), affected));
                    } catch (RuntimeException re) {
                        break;
                    }
                }
                break;
            case WEST:
                for (int i = this.startPosition.getX(); i >= this.endPosition.getX(); i--) {
                    try {
                    moveCommands.add(new MoveCommand(new Coordinate(i, this.startPosition.getY()), new Coordinate(i - 1, this.startPosition.getY()), affected));
                    } catch (RuntimeException re) {
                        break;
                    }
                }
                break;
        }
        return moveCommands;
    }
}
