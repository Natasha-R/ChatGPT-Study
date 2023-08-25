package thkoeln.st.st2praktikum.linearAlgebra;

public class ConcreteBoundedStraight extends BoundedStraight {
    public ConcreteBoundedStraight(Vector directionVector, Vector offsetVector,
                                   double beginLambda, double endLambda) {
        super(directionVector, offsetVector, beginLambda, endLambda);
    }

    public ConcreteBoundedStraight(Vector startPoint, Vector endPoint) {
        super(startPoint, endPoint);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BoundedStraight)) {
            return false;
        }
        BoundedStraight otherBoundedStraight = (BoundedStraight) obj;
        return this.getDirectionVector()
                .equals(otherBoundedStraight.getDirectionVector())
                && this.getOffsetVector()
                .equals(otherBoundedStraight.getOffsetVector())
                && this.getBeginLambda() == otherBoundedStraight
                .getBeginLambda()
                && this.getEndLambda() == otherBoundedStraight.getEndLambda();
    }
}
