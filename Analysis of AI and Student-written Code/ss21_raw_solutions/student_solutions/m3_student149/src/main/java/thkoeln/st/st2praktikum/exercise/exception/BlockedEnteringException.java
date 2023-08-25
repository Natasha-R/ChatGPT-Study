package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.environment.Space;

public class BlockedEnteringException extends FailedExecutionException {

    public BlockedEnteringException(Space sp, Vector2D vector2D) {
        super("Could not enter because entryPosition '" + vector2D + "' in space: '" + sp + "' is blocked");
    }

}
