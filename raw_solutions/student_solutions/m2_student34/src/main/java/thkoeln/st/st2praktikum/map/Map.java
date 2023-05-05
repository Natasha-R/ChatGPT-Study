package thkoeln.st.st2praktikum.map;

import thkoeln.st.st2praktikum.droid.Droid;
import thkoeln.st.st2praktikum.droid.Movement;
import thkoeln.st.st2praktikum.lib.Entity;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.Optional;

public interface Map extends Entity {
    Optional<Movement> maxMove(Movement movement);

    void addRectangleObstacle(RectangleObstacle obstacle);

    boolean isInBounds(Position position);

    boolean enter(Droid droid, Position targetPosition);
    boolean leave(Droid droid);
}
