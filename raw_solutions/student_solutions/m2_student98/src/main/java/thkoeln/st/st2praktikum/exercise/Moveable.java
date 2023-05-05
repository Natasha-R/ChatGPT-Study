package thkoeln.st.st2praktikum.exercise;

public interface Moveable {

    Orientation getOrientation();

    Vector2DRoom getCurrentPosition();

    Vector2DRoom takeSingleStep();

    Vector2DRoom nextRequestedPositionFromCurrentPosition();

    MovementStatus getMovementStatus();

    void setStatusBlocked();
}
