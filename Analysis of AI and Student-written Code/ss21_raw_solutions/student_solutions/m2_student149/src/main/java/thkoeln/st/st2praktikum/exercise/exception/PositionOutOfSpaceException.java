package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.environment.Space;
import thkoeln.st.st2praktikum.exercise.environment.position.Position;

public class PositionOutOfSpaceException extends RuntimeException {

    public PositionOutOfSpaceException(Position position, Space space) {
        super("Position '" + position + "' is out of space borders " +
                "(width: " + space.getWidth() + "; height: " + space.getHeight() + ")");
    }

    public PositionOutOfSpaceException(int x, int y) {
        super("Coordinates of position have to be positive: " + "x:" + x + " y:" + y);
    }

}
