package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

public class Vector2DOutOfSpaceException extends RuntimeException {

    public Vector2DOutOfSpaceException(Vector2D position, Space space) {
        super("Position '" + position + "' is out of space borders " +
                "(width: " + space.getWidth() + "; height: " + space.getHeight() + ")");
    }

    public Vector2DOutOfSpaceException(int x, int y) {
        super("Coordinates of position have to be positive: " + "x:" + x + " y:" + y);
    }

}
