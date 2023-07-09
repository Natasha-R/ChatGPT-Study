package thkoeln.st.st2praktikum.exercise;

public interface XYMovable {

    XYPositionable getStartingPosition();

    Orientation getOrientation();

    Integer getRequestedStepLength();

    XYPositionable getCurrentPosition();

    XYPositionable takeSingleStep();

    XYPositionable nextRequestedPositionFromCurrentPosition();

    Integer getCurrentStepLength();

    MovementStatus getMovementStatus();

    void setStatusBlocked();
}
