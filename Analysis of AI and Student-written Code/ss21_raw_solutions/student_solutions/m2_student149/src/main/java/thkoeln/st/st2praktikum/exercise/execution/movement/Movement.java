package thkoeln.st.st2praktikum.exercise.execution.movement;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;
import thkoeln.st.st2praktikum.exercise.exception.NegativeMovementStepsException;
import thkoeln.st.st2praktikum.exercise.exception.PositionOutOfSpaceException;

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

    public Position makeStep(Position currentLocation) {
        if (steps > 0) {
            return makeSteps(1, currentLocation);
        } else {
            return currentLocation;
        }
    }

    public Position makeCompleteMove(Position currentLocation) {
        return makeSteps(steps, currentLocation);
    }

    private Position makeSteps(int steps, Position currentLocation) {
        Position newPosition = currentLocation;
        try {
            newPosition = calculateNewPositionAfterMove(steps, currentLocation);
        } catch (PositionOutOfSpaceException ignore) {
        }
        reduceSteps(steps);

        return newPosition;
    }

    private Position calculateNewPositionAfterMove(int steps, Position currentLocation) throws PositionOutOfSpaceException {
        int newX = currentLocation.getX() + steps * direction.getXFactor();
        int newY = currentLocation.getY() + steps * direction.getYFactor();
        return Position.of(newX, newY);
    }

    private void reduceSteps(int reduction) {
        steps = steps <= reduction ? 0 : steps - reduction;
    }

    @Override
    public String toString() {
        return "[" + direction.getCode() + "," + steps + "]";
    }
}
