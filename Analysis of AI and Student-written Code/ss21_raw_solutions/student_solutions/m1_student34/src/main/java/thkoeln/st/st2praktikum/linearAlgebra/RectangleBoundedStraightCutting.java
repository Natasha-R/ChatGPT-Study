package thkoeln.st.st2praktikum.linearAlgebra;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
public class RectangleBoundedStraightCutting implements Cutting {

    private final Rectangle rectangle;
    private final BoundedStraight straight;

    @Override
    public Optional<Vector> calculateCut() {
        var beginPosition = straight.at(straight.getBeginLambda())
                .orElseThrow();

        return Arrays.stream(rectangle.getSites())
                .flatMap(it -> it.cut(this.straight).stream())
                .min(Comparator.comparing(
                        cutPosition -> cutPosition.subtract(beginPosition)
                                .absoluteValue()
                ));
    }

    @Override
    public Cuttable getLeftCuttable() {
        return this.rectangle;
    }

    @Override
    public Cuttable getRightCuttable() {
        return this.straight;
    }
}
