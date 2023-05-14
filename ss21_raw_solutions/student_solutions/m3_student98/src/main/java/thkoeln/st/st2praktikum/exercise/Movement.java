package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.core.Moveable;

public class Movement implements Moveable {

    private final Vector2D startingPosition;
    private final Orientation orientation;
    private final Integer requestedStepLength;

    private Vector2D currentPosition;
    private Integer currentStepLength;
    private MovementStatus movementStatus;

    public Movement (Vector2D startingPosition, Orientation orientation, Integer stepLength) {
        this.startingPosition = startingPosition;
        this.orientation = orientation;
        this.requestedStepLength = stepLength;
        this.currentPosition = startingPosition;
        this.currentStepLength = 0;
        this.movementStatus = MovementStatus.READY;
    }

    @Override
    public Vector2D takeSingleStep() {
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
    public Vector2D nextRequestedPositionFromCurrentPosition() {
        return calculateNewPosition(this.currentPosition, 1);
    }

    @Override
    public void setStatusBlocked() {
        this.movementStatus = MovementStatus.BLOCKED;
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public Vector2D getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public MovementStatus getMovementStatus() {
        return movementStatus;
    }

    private Boolean isStatusMoveable() {
        return (movementStatus == MovementStatus.READY || movementStatus == MovementStatus.RUNNING);
    }

    private Boolean movementNotFinishedYet() {
        return (currentStepLength < requestedStepLength);
    }

    private Vector2D calculateNewPosition(Vector2D position, Integer stepLength) {
        Integer newXPos = position.getX();
        Integer newYPos = position.getY();
        switch (this.orientation) {
            case NO: newYPos = position.getY() + stepLength; break;
            case EA: newXPos = position.getX() + stepLength; break;
            case SO: newYPos = position.getY() - stepLength; break;
            case WE: newXPos = position.getX() - stepLength; break;
        }
        return new Vector2D(newXPos, newYPos, position.getRoom());
    }
}
