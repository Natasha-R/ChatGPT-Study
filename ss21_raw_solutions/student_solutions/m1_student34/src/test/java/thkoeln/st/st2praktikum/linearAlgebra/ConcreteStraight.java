package thkoeln.st.st2praktikum.linearAlgebra;

public class ConcreteStraight extends Straight {

    public ConcreteStraight(Vector directionVector, Vector offsetVector) {
        super(directionVector, offsetVector);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Straight)) {
            return false;
        }
        Straight otherStraight = (Straight) obj;

        return this.getDirectionVector()
                .equals(otherStraight.getDirectionVector())
                && this.getOffsetVector()
                .equals(otherStraight.getOffsetVector());
    }
}
