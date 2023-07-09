package thkoeln.st.st2praktikum.exercise.execution;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;

import java.util.UUID;

public interface Enterable extends Executable {
    void enter(UUID spaceId, Position entryPosition) throws FailedExecutionException;
}
