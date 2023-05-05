package thkoeln.st.st2praktikum.droid;

import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.MapPosition;
import thkoeln.st.st2praktikum.map.Position;
import thkoeln.st.st2praktikum.map.StartPosition;

public class MovementFactory {

    private static MovementFactory movementFactory;

    public static MovementFactory getInstance() {
        if (MovementFactory.movementFactory == null) {
            MovementFactory.movementFactory = new MovementFactory();
        }
        return MovementFactory.movementFactory;
    }

    public Movement createMovement(Position sourcePosition, Command command,
                                   Droid droid) {
        if (sourcePosition instanceof MapPosition && command instanceof Command.MapCommand) {
            return new MapMovement(sourcePosition,
                    calculateTargetPosition((MapPosition) sourcePosition,
                            (Command.MapCommand) command), droid);
        }
        if (sourcePosition instanceof StartPosition && command instanceof Command.EnterCommand) {
            return new EnterMovement((StartPosition) sourcePosition,
                    MapPosition.of(((Command.EnterCommand) command)
                            .getTargetMap(), Vector.of(0.5, 0.5)));
        }
        if (sourcePosition instanceof MapPosition && command instanceof Command.ConnectionCommand) {
            var connectionCommand = (Command.ConnectionCommand) command;
            if (!sourcePosition
                    .equals((connectionCommand.getConnection().getSource()))) {
                throw new IllegalArgumentException();
            }
            return new ConnectionMovement(connectionCommand.getConnection());
        }
        throw new IllegalArgumentException("Nothing found for " + sourcePosition + " and " + command);
    }

    private MapPosition calculateTargetPosition(MapPosition startPosition,
                                                Command.MapCommand command) {
        Vector startCoordinates = startPosition.getCoordinates();
        Vector targetCoordinates;

        switch (command.getDirection()) {
            case NORTH:
                targetCoordinates = startCoordinates
                        .add(Vector.of(0, command.getDistance()));
                break;
            case EAST:
                targetCoordinates = startCoordinates
                        .add(Vector.of(command.getDistance(), 0));
                break;
            case SOUTH:
                targetCoordinates = startCoordinates
                        .subtract(Vector.of(0, command.getDistance()));
                break;
            case WEST:
                targetCoordinates = startCoordinates
                        .subtract(Vector.of(command.getDistance(), 0));
                break;
            default:
                throw new UnsupportedOperationException();
        }

        return MapPosition.of(startPosition, targetCoordinates);
    }
}
