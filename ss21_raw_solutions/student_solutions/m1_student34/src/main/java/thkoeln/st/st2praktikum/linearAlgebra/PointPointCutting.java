package thkoeln.st.st2praktikum.linearAlgebra;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class PointPointCutting implements Cutting {

    private final Point leftCuttable;
    private final Point rightCuttable;

    @Override
    public Optional<Vector> calculateCut() {
        return this.leftCuttable.equals(this.rightCuttable) ?
                Optional.of(this.leftCuttable.getCoordinates()) :
                Optional.empty();
    }

    @Override
    public Cuttable getLeftCuttable() {
        return null;
    }

    @Override
    public Cuttable getRightCuttable() {
        return null;
    }
}
