package thkoeln.st.st2praktikum.exercise.execution.movement;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.NegativeMovementStepsException;
import thkoeln.st.st2praktikum.exercise.exception.Vector2DOutOfSpaceException;

public class Movement {

    private final Direction direction;
    private int steps;

    public Movement(Direction direction, int steps) throws NegativeMovementStepsException {
        if (steps < 0) {
            throw new NegativeMovementStepsException(steps);
        }
        this.direction = direction;
        this.steps = steps;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSteps() {
        return steps;
    }

    public Vector2D makeStep(Vector2D currentLocation) {
        if (steps > 0) {
            return makeSteps(1, currentLocation);
        } else {
            return currentLocation;
        }
    }

    public Vector2D makeCompleteMove(Vector2D currentLocation) {
        return makeSteps(steps, currentLocation);
    }

    private Vector2D makeSteps(int steps, Vector2D currentLocation) {
        Vector2D newPosition = currentLocation;
        try {
            newPosition = calculateNewPositionAfterMove(steps, currentLocation);
        } catch (Vector2DOutOfSpaceException ignore) {
        }
        reduceSteps(steps);

        return newPosition;
    }

    private Vector2D calculateNewPositionAfterMove(int steps, Vector2D currentLocation) throws Vector2DOutOfSpaceException {
        int newX = currentLocation.getX() + steps * direction.getXFactor();
        int newY = currentLocation.getY() + steps * direction.getYFactor();
        return new Vector2D(newX, newY);
    }

    private void reduceSteps(int reduction) {
        steps = steps <= reduction ? 0 : steps - reduction;
    }

    @Override
    public String toString() {
        return "[" + direction.getCode() + "," + steps + "]";
    }
}
