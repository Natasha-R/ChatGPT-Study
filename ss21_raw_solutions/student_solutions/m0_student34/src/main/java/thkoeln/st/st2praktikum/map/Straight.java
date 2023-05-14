package thkoeln.st.st2praktikum.map;

import org.springframework.data.util.Pair;

import java.util.Optional;

public class Straight {

    private final LinearSystem linearSystem;
    // f: x = l * directionVector + offsetVector
    protected final double[] directionVector;
    protected final double[] offsetVector;

    public Straight(int[] directionVector, int[] offsetVector, LinearSystem linearSystem) {
        this.linearSystem = linearSystem;

        if (directionVector.length != offsetVector.length) {
            throw new IllegalArgumentException();
        }
        this.directionVector = new double[directionVector.length];
        this.offsetVector = new double[offsetVector.length];

        for (int i = 0; i < directionVector.length; i++) {
            this.directionVector[i] = directionVector[i];
            this.offsetVector[i] = offsetVector[i];
        }
    }

    public Straight(double[] directionVector, double[] offsetVector, LinearSystem linearSystem) {
        this.linearSystem = linearSystem;

        if (directionVector.length != offsetVector.length) {
            throw new IllegalArgumentException();
        }
        this.directionVector = new double[directionVector.length];
        this.offsetVector = new double[offsetVector.length];

        System.arraycopy(directionVector, 0, this.directionVector, 0, directionVector.length);
        System.arraycopy(offsetVector, 0, this.offsetVector, 0, offsetVector.length);
    }

    /**
     * Returns the point of intersection for the two vectors.
     * If they don't cut each other or are the same an Optional.empty will be returned.
     *
     * @param otherStraight
     * @return intersection of the two vectors
     */
    public Optional<double[]> cut(Straight otherStraight) {
        if (this.directionVector.length != otherStraight.getDirectionVector().length) {
            throw new IllegalArgumentException();
        }
        double[][] lsMatrix = new double[directionVector.length][3];
        for (int i = 0; i < this.directionVector.length; i++) {
            lsMatrix[i][0] = this.directionVector[i];
            lsMatrix[i][1] = otherStraight.getDirectionVector()[i] * -1;
            lsMatrix[i][2] = otherStraight.getOffsetVector()[i] - this.offsetVector[i];
        }
        Pair<double[], Integer> result = this.linearSystem.solve(lsMatrix);

        if (result.getSecond() == 1) {
            double l1 = result.getFirst()[0];
            double l2 = result.getFirst()[1];
            if (this.at(l1).isPresent() && otherStraight.at(l2).isPresent()) {
                return Optional.of(this.at(l1).get());
            }
        }
        return Optional.empty();

    }

    public Optional<double[]> at(double lambda) {
        return Optional.of(new double[]{
                this.offsetVector[0] + lambda * this.directionVector[0],
                this.offsetVector[1] + lambda * this.directionVector[1]
        });
    }

    public double[] getDirectionVector() {
        return directionVector;
    }

    public double[] getOffsetVector() {
        return offsetVector;
    }
}
