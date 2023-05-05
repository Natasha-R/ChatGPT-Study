package thkoeln.st.st2praktikum.exercise.execution;

import thkoeln.st.st2praktikum.exercise.exception.BlockedMoveException;
import thkoeln.st.st2praktikum.exercise.exception.EntryPositionOutOfSpaceException;
import thkoeln.st.st2praktikum.exercise.exception.NoConnectedTransitionException;

import java.util.UUID;

public interface Traversable extends Executable {
    void traverse(UUID destinationSpaceId) throws NoConnectedTransitionException, BlockedMoveException, EntryPositionOutOfSpaceException;
}
