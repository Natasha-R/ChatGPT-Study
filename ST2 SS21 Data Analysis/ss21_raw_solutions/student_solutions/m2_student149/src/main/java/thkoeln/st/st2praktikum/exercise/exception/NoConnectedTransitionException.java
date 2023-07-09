package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.environment.Space;
import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;

public class NoConnectedTransitionException extends FailedExecutionException {

    public NoConnectedTransitionException(EnvironmentPosition fromPosition, Space toSpace) {
        super("There is no ConnectedTransition from position '" + fromPosition + "' to space '" + toSpace + "'");
    }

}
