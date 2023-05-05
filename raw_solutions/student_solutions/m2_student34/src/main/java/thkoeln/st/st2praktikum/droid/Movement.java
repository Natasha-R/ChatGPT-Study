package thkoeln.st.st2praktikum.droid;

import thkoeln.st.st2praktikum.linearAlgebra.Cuttable;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.Position;
import thkoeln.st.st2praktikum.map.RectangleMovementCutting;
import thkoeln.st.st2praktikum.map.RectangleObstacle;

import java.util.Optional;

public interface Movement extends Cuttable {
    Position getSourcePosition();

    Position getTargetPosition();

    Movement changeTargetPosition(Position targetPosition) throws IllegalStateException;

    @Override
    default Optional<Vector> cut(Cuttable cuttable) {
        if (cuttable instanceof RectangleObstacle) {
            return new RectangleMovementCutting((RectangleObstacle) cuttable,
                    this).calculateCut();
        }
        throw new UnsupportedOperationException();
    }
}
