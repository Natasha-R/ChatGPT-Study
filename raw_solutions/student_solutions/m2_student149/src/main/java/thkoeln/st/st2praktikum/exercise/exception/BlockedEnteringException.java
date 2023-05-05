package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;

public class BlockedEnteringException extends FailedExecutionException {

    public BlockedEnteringException(EnvironmentPosition entryPosition) {
        super("Could not enter because entryPosition '" + entryPosition + "' is blocked");
    }

}
