package thkoeln.st.st2praktikum.exercise;

public class MoveEast extends AbstractMove {

    @Override
    public Position moveUntilBlocked(Matrix Coordinates, Position startPosition, int stepLength) {
        Position currentPosition = new Position(startPosition.getXPos(), startPosition.getYPos());

        while (stepLength > 0) {
            Position newPosition = new Position(currentPosition.getXPos() + 1, currentPosition.getYPos());

            if (!validateStep(Coordinates, newPosition, Orientation.EA))
                return currentPosition;

            currentPosition.setXPos(newPosition.getXPos());
            stepLength--;
        }
        return currentPosition;
    }
}
