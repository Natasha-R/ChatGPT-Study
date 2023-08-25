package thkoeln.st.st2praktikum.exercise;

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
