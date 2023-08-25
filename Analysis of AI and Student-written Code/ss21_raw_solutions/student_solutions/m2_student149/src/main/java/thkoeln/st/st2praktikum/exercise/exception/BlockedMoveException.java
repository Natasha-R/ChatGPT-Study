package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;

public class BlockedMoveException extends StoppedExecutionException {

    public BlockedMoveException(Position from, Position to) {
        super("Could not move because move from '" + from + "' to '" + to + "' is blocked");
    }

}
