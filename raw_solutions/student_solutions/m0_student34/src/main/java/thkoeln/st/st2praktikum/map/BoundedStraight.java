package thkoeln.st.st2praktikum.map;

import java.util.Optional;

public class BoundedStraight extends Straight {

    private final double beginLambda;
    private final double endLambda;

    public BoundedStraight(
            int[] directionVector,
            int[] offsetVector,
            LinearSystem linearSystem,
            double beginLambda,
            double endLambda) {
        super(directionVector, offsetVector, linearSystem);
        this.beginLambda = beginLambda;
        this.endLambda = endLambda;
    }

    public BoundedStraight(
            double[] directionVector,
            double[] offsetVector,
            LinearSystem linearSystem,
            double beginLambda,
            double endLambda) {
        super(directionVector, offsetVector, linearSystem);
        this.beginLambda = beginLambda;
        this.endLambda = endLambda;
    }

    public BoundedStraight(double[] startPoint, double[] endPoint, LinearSystem linearSystem) {
        super(
                new double[]{
                        endPoint[0] - startPoint[0],
                        endPoint[1] - startPoint[1]
                },
                startPoint,
                linearSystem
        );
        this.beginLambda = 0;
        this.endLambda = 1;
    }

    @Override
    public Optional<double[]> at(double lambda) {
        if (this.beginLambda <= lambda && lambda <= this.endLambda) {
            return super.at(lambda);
        }
        return Optional.empty();
    }

    public double getBeginLambda() {
        return beginLambda;
    }

    public double getEndLambda() {
        return endLambda;
    }
}
