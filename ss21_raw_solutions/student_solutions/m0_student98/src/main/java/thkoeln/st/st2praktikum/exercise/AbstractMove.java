package thkoeln.st.st2praktikum.exercise;

abstract public class AbstractMove {

    abstract public Position moveUntilBlocked(Matrix Coordinates, Position position, int length);

    protected Boolean validateStep(Matrix Coordinates, Position position, Orientation orientation) {
        return Coordinates.validatePosition(position, orientation);
    }
}
