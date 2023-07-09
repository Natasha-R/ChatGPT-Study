package thkoeln.st.st2praktikum.map;

import thkoeln.st.st2praktikum.linearAlgebra.Cuttable;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.Optional;

public class StartPosition implements Position {
    @Override
    public double distance(Position otherPosition) {
        return Double.MAX_VALUE;
    }

    @Override
    public Map getMap() {
        return null;
    }

    @Override
    public Vector getCoordinates() {
        return null;
    }

    @Override
    public Optional<Vector> cut(Cuttable otherStraight) {
        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StartPosition;
    }
}
