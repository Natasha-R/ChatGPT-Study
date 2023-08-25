package thkoeln.st.st2praktikum.exercise;

public class MoveNorth extends AbstractMove {

    @Override
    public Position moveUntilBlocked(Matrix Coordinates, Position startPosition, int stepLength) {
        Position currentPosition = new Position(startPosition.getXPos(), startPosition.getYPos());

        while (stepLength > 0) {
            Position newPosition = new Position(currentPosition.getXPos(), currentPosition.getYPos() + 1);

            if (!validateStep(Coordinates, newPosition, Orientation.NO))
                return currentPosition;

            currentPosition.setYPos(newPosition.getYPos());
            stepLength--;
        }
        return currentPosition;
    }
}
