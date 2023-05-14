package thkoeln.st.st2praktikum.linearAlgebra;

import java.util.Optional;

public abstract class BoundedStraight extends Straight {

    private final double beginLambda;
    private final double endLambda;

    public BoundedStraight(
            Vector directionVector,
            Vector offsetVector,
            double beginLambda,
            double endLambda) {
        super(directionVector, offsetVector);
        this.beginLambda = beginLambda;
        this.endLambda = endLambda;
    }

    public BoundedStraight(Vector startPoint, Vector endPoint) {
        super(endPoint.subtract(startPoint), startPoint);
        this.beginLambda = 0;
        this.endLambda = 1;
    }

    @Override
    public Optional<Vector> at(double lambda) {
        if (this.beginLambda <= lambda && lambda <= this.endLambda) {
            return super.at(lambda);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Vector> cut(Cuttable cuttable) {
        if (cuttable instanceof Rectangle) {
            return new RectangleBoundedStraightCutting((Rectangle) cuttable, this)
                    .calculateCut();
        }
        return super.cut(cuttable);
    }

    public double getBeginLambda() {
        return beginLambda;
    }

    public double getEndLambda() {
        return endLambda;
    }
}
