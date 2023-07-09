package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Orientation;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.MovementStatus;

public interface Moveable {

    Orientation getOrientation();

    Point getCurrentPosition();

    Point takeSingleStep();

    Point nextRequestedPositionFromCurrentPosition();

    MovementStatus getMovementStatus();

    void setStatusBlocked();
}
