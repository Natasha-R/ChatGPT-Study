package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

public class NoConnectedTransitionException extends FailedExecutionException {

    public NoConnectedTransitionException(Vector2D fromPosition, UUID toSpaceId) {
        super("There is no ConnectedTransition from position '" + fromPosition + "' to space '" + toSpaceId + "'");
    }

}
