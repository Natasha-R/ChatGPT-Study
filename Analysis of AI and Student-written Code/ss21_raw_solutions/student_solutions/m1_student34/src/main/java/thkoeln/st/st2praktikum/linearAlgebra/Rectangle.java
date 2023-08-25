package thkoeln.st.st2praktikum.linearAlgebra;

import java.util.Optional;

public abstract class Rectangle implements Cuttable {

    @Override
    public Optional<Vector> cut(Cuttable cuttable) {
        if (cuttable instanceof BoundedStraight) {
            return new RectangleBoundedStraightCutting(this,
                    (BoundedStraight) cuttable).calculateCut();
        }
        if (cuttable instanceof Point) {
            return new RectanglePointCutting(this, (Point) cuttable)
                    .calculateCut();
        }
        throw new UnsupportedOperationException();
    }

    public abstract BoundedStraight[] getSites();

    public abstract Vector getUpperLeftCorner();

    public abstract Vector getLowerLeftCorner();

    public abstract Vector getUpperRightCorner();

    public abstract Vector getLowerRightCorner();
}
