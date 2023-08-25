package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;
import java.util.Optional;

@Embeddable
public class StartPosition implements Position {
    @Override
    public double distance(Position otherPosition) {
        return Double.MAX_VALUE;
    }

    @Override
    @Transient
    public Map getMap() {
        return null;
    }

    @Override
    @Embedded
    public Vector getCoordinates() {
        return null;
    }

    protected void setCoordinates(Vector coordinates) {

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

    public static StartPosition NULL = new StartPosition(){};
}
