package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.Vector2D;

public class BlockedMoveException extends StoppedExecutionException {

    public BlockedMoveException(Vector2D from, Vector2D to) {
        super("Could not move because move from '" + from + "' to '" + to + "' is blocked");
    }

}
