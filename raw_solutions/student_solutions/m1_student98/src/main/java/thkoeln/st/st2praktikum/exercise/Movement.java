package thkoeln.st.st2praktikum.exercise;

import java.util.Optional;

public class Movement implements XYMovable {

    private final XYPositionable startingPosition;
    private final Orientation orientation;
    private final Integer requestedStepLength;

    private XYPositionable currentPosition;
    private Integer currentStepLength;
    private MovementStatus movementStatus;


    public Movement (XYPositionable startingPosition, Orientation orientation, Integer stepLength) {
        this.startingPosition = startingPosition;
        this.orientation = orientation;
        this.requestedStepLength = stepLength;
        this.currentPosition = startingPosition;
        this.currentStepLength = 0;
        this.movementStatus = MovementStatus.READY;
    }

    @Override
    public XYPositionable takeSingleStep() {
        if (!isStatusMoveable()) {
            return currentPosition;
        }
        if (!movementNotFinishedYet()) {
            movementStatus = MovementStatus.FINISHED;
            return currentPosition;
        }
        this.currentPosition = calculateNewPosition(currentPosition, 1);
        this.currentStepLength++;
        this.movementStatus = MovementStatus.RUNNING;
        return this.currentPosition;
    }

    @Override
    public XYPositionable nextRequestedPositionFromCurrentPosition() {
        return calculateNewPosition(this.currentPosition, 1);
    }

    @Override
    public void setStatusBlocked() {
        this.movementStatus = MovementStatus.BLOCKED;
    }

    private Boolean isStatusMoveable() {
        return (movementStatus == MovementStatus.READY || movementStatus == MovementStatus.RUNNING);
    }

    private Boolean movementNotFinishedYet() {
        return (currentStepLength < requestedStepLength);
    }

    private XYPositionable calculateNewPosition(XYPositionable position, Integer stepLength) {
        Integer newXPos = position.getXPos();
        Integer newYPos = position.getYPos();
        switch (this.orientation) {
            case NO: newYPos = position.getYPos() + stepLength; break;
            case EA: newXPos = position.getXPos() + stepLength; break;
            case SO: newYPos = position.getYPos() - stepLength; break;
            case WE: newXPos = position.getXPos() - stepLength; break;
        }
        return position.clone(Optional.of(newXPos), Optional.of(newYPos), Optional.empty());
    }

    @Override
    public XYPositionable getStartingPosition() {
        return startingPosition;
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public Integer getRequestedStepLength() {
        return requestedStepLength;
    }

    @Override
    public XYPositionable getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public Integer getCurrentStepLength() {
        return currentStepLength;
    }

    @Override
    public MovementStatus getMovementStatus() {
        return movementStatus;
    }
}
