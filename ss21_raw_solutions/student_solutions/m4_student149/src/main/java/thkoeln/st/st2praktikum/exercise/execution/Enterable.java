package thkoeln.st.st2praktikum.exercise.execution;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.FailedExecutionException;

import java.util.UUID;

public interface Enterable extends Executable {
    void enter(UUID spaceId, Vector2D entryPosition) throws FailedExecutionException;
}
