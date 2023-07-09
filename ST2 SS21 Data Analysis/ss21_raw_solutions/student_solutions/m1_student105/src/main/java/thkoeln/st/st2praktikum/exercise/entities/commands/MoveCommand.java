package thkoeln.st.st2praktikum.exercise.entities.commands;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Direction;
import thkoeln.st.st2praktikum.exercise.entities.Coordinate;
import thkoeln.st.st2praktikum.exercise.entities.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.entities.Spaceship;
import thkoeln.st.st2praktikum.exercise.exceptions.ParseException;
import thkoeln.st.st2praktikum.exercise.interfaces.Moveable;

import java.util.ArrayList;

@Getter
public class MoveCommand extends Command {
    public Coordinate startPosition;
    public Coordinate endPosition;
    public Direction direction;
    private Moveable affected;

    public MoveCommand(Coordinate from, Coordinate to) {
        this.startPosition = from;
        this.endPosition = to;

        if (to.x > from.x) {
            direction = Direction.EAST;
        } else if (to.x < from.x) {
            direction = Direction.WEST;
        } else if (to.y > from.y) {
            direction = Direction.NORTH;
        } else if (to.y < from.y) {
            direction = Direction.SOUTH;
        }
    }

    @Override
    public boolean executeOn(Spaceship ship) {
        ship.move(this);
        return true;
    }

    public static MoveCommand build(String directionString, String distanceString, MaintenanceDroid droid) throws ParseException {
        MoveCommand moveCommand;
        int distance = Integer.parseInt(distanceString);

        if (directionString.startsWith("n")) {
            moveCommand = new MoveCommand(droid.currentPosition, new Coordinate(droid.currentPosition.x, droid.currentPosition.y + distance));
        } else if (directionString.startsWith("s")) {
            moveCommand = new MoveCommand(droid.currentPosition, new Coordinate(droid.currentPosition.x, droid.currentPosition.y - distance));
        } else if (directionString.startsWith("e")) {
            moveCommand = new MoveCommand(droid.currentPosition, new Coordinate(droid.currentPosition.x + distance, droid.currentPosition.y));
        } else if (directionString.startsWith("w")) {
            moveCommand = new MoveCommand(droid.currentPosition, new Coordinate(droid.currentPosition.x - distance, droid.currentPosition.y));
        } else {
            throw new ParseException("Could not parse command direction: " + directionString);
        }

        if (moveCommand.endPosition.x > droid.grid.getWidth()) {
            moveCommand.endPosition.x = droid.grid.getWidth();
        } else if (moveCommand.endPosition.x < 0) {
            moveCommand.endPosition.x = 0;
        }
        if (moveCommand.endPosition.y > droid.grid.getHeight()) {
            moveCommand.endPosition.y = droid.grid.getHeight();
        } else if (moveCommand.endPosition.y < 0) {
            moveCommand.endPosition.y = 0;
        }

        moveCommand.affected = droid;
        return moveCommand;
    }

    public ArrayList<MoveCommand> getSingleSteps() {
        ArrayList<MoveCommand> moveCommands = new ArrayList<>(11);
        switch (this.direction) {
            case NORTH:
                for (int i = this.startPosition.y; i < this.endPosition.y; i++) {
                    moveCommands.add(new MoveCommand(new Coordinate(this.startPosition.x, i), new Coordinate(this.endPosition.x, i + 1)));
                }
                break;
            case SOUTH:
                for (int i = this.startPosition.y; i >= this.endPosition.y; i--) {
                    moveCommands.add(new MoveCommand(new Coordinate(this.startPosition.x, i), new Coordinate(this.startPosition.x, i - 1)));
                }
                break;
            case EAST:
                for (int i = this.startPosition.x; i < this.endPosition.x; i++) {
                    moveCommands.add(new MoveCommand(new Coordinate(i, this.startPosition.y), new Coordinate(i + 1, this.endPosition.y)));
                }
                break;
            case WEST:
                for (int i = this.startPosition.x; i >= this.endPosition.x; i--) {
                    moveCommands.add(new MoveCommand(new Coordinate(i, this.startPosition.y), new Coordinate(i - 1, this.startPosition.y)));
                }
                break;
        }
        return moveCommands;
    }
}
