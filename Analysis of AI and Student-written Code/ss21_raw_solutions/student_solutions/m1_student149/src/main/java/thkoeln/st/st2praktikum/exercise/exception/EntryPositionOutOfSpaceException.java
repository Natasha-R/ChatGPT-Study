package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;

public class EntryPositionOutOfSpaceException extends FailedExecutionException {

    public EntryPositionOutOfSpaceException(EnvironmentPosition position) {
        super("EntryPosition '" + position + "' is out of space borders " +
                "(width: " + position.getSpace().getWidth() + "; height: " + position.getSpace().getHeight() + ")");
    }

}
