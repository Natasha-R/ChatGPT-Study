package thkoeln.st.st2praktikum.exercise.execution.movement;

import thkoeln.st.st2praktikum.exercise.environment.position.Position;

public class Movement {

    private final Direction direction;
    private int steps;

    public Movement(Direction direction, int steps) {
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
        Position newPosition = calculateNewPositionAfterMove(steps, currentLocation);
        reduceSteps(steps);
        return newPosition;
    }

    private Position calculateNewPositionAfterMove(int steps, Position currentLocation) {
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
