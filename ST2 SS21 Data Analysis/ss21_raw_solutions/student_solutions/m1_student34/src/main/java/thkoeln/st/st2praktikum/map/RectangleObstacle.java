package thkoeln.st.st2praktikum.map;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import thkoeln.st.st2praktikum.droid.Movement;
import thkoeln.st.st2praktikum.linearAlgebra.Cuttable;
import thkoeln.st.st2praktikum.linearAlgebra.Rectangle;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.Optional;


@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class RectangleObstacle extends Rectangle implements Obstacle {

    private final Vector upperLeftCorner;
    private final Vector lowerLeftCorner;
    private final Vector upperRightCorner;
    private final Vector lowerRightCorner;

    @Override
    public BoundedObstacle[] getSites() {
        return new BoundedObstacle[]{
                new BoundedObstacle(this.getUpperLeftCorner(), this
                        .getUpperRightCorner()),
                new BoundedObstacle(this.getLowerLeftCorner(), this
                        .getLowerRightCorner()),
                new BoundedObstacle(this.getLowerLeftCorner(), this
                        .getUpperLeftCorner()),
                new BoundedObstacle(this.getLowerRightCorner(), this
                        .getUpperRightCorner())
        };
    }

    @Override
    public Optional<Vector> cut(Cuttable cuttable) {
        if (cuttable instanceof Movement) {
            return new RectangleMovementCutting(this, (Movement) cuttable)
                    .calculateCut();
        }
        return super.cut(cuttable);
    }
}
