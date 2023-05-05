package thkoeln.st.st2praktikum.linearAlgebra;

import java.util.Optional;

public abstract class Point implements Cuttable {
    @Override
    public Optional<Vector> cut(Cuttable cuttable) {
        if (cuttable instanceof Point) {
            return new PointPointCutting(this, (Point) cuttable)
                    .calculateCut();
        }
        if (cuttable instanceof Straight) {
            return new StraightPointCutting((Straight) cuttable, this)
                    .calculateCut();
        }
        if (cuttable instanceof Rectangle) {
            return new RectanglePointCutting((Rectangle) cuttable, this)
                    .calculateCut();
        }
        throw new UnsupportedOperationException();
    }

    public abstract Vector getCoordinates();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point)) {
            return false;
        }
        Point otherPoint = (Point) o;

        return this.getCoordinates().equals(otherPoint.getCoordinates());
    }
}
