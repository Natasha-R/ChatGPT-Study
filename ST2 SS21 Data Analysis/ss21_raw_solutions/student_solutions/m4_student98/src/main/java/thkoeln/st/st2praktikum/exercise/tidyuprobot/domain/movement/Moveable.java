package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Orientation;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.MovementStatus;

public interface Moveable {

    Orientation getOrientation();

    Vector2D getCurrentPosition();

    Vector2D takeSingleStep();

    Vector2D nextRequestedPositionFromCurrentPosition();

    MovementStatus getMovementStatus();

    void setStatusBlocked();
}
