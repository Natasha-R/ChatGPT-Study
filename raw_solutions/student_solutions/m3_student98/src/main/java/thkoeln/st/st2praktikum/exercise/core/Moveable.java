package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.MovementStatus;
import thkoeln.st.st2praktikum.exercise.Orientation;
import thkoeln.st.st2praktikum.exercise.Vector2D;

public interface Moveable {

    Orientation getOrientation();

    Vector2D getCurrentPosition();

    Vector2D takeSingleStep();

    Vector2D nextRequestedPositionFromCurrentPosition();

    MovementStatus getMovementStatus();

    void setStatusBlocked();
}
