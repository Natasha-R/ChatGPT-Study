package thkoeln.st.st2praktikum.exercise.execution;

import thkoeln.st.st2praktikum.exercise.exception.BlockedMoveException;
import thkoeln.st.st2praktikum.exercise.execution.movement.Movement;

public interface Movable extends Executable {
    void move(Movement movement) throws BlockedMoveException;
}
