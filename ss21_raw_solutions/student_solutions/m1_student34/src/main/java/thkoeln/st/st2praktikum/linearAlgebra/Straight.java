package thkoeln.st.st2praktikum.linearAlgebra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public abstract class Straight implements Cuttable {

    // f: x = l * directionVector + offsetVector
    protected final Vector directionVector;
    protected final Vector offsetVector;

    /**
     * Returns the point of intersection for the two vectors.
     * If they don't cut each other or are the same an Optional.empty will be returned.
     *
     * @param cuttable
     * @return intersection of the two vectors
     */
    public Optional<Vector> cut(Cuttable cuttable) {
        if (cuttable instanceof Straight) {
            return new StraightCutting(this, (Straight) cuttable)
                    .calculateCut();
        }
        if (cuttable instanceof Point) {
            return new StraightPointCutting(this, (Point) cuttable)
                    .calculateCut();
        }
        throw new UnsupportedOperationException();
    }

    public Optional<Vector> at(double lambda) {
        return Optional.of(this.offsetVector
                .add(this.directionVector.multiply(lambda)));
    }
}
