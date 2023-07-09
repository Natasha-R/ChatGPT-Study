package thkoeln.st.st2praktikum.exercise.execution;

import thkoeln.st.st2praktikum.exercise.exception.BlockedMoveException;
import thkoeln.st.st2praktikum.exercise.exception.NoConnectedTransitionException;
import thkoeln.st.st2praktikum.exercise.exception.Vector2DOutOfSpaceException;

import java.util.UUID;

public interface Traversable extends Executable {
    void traverse(UUID destinationSpaceId) throws NoConnectedTransitionException, BlockedMoveException, Vector2DOutOfSpaceException;
}
