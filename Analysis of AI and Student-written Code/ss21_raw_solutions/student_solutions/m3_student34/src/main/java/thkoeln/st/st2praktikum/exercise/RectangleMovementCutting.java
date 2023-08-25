package thkoeln.st.st2praktikum.exercise;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@RequiredArgsConstructor
public class RectangleMovementCutting implements Cutting {

    private final RectangleObstacle rectangleObstacle;
    private final Movement movement;

    @Override
    public Optional<Vector> calculateCut() {
        return Arrays.stream(rectangleObstacle.getSites())
                .flatMap(it -> it.cut(this.movement).stream())
                .map(it -> MapPosition.of(movement.getSourcePosition(), it))
                .sorted(Comparator.comparing(
                        it -> movement.getSourcePosition().distance(it)
                )).map(MapPosition::getCoordinates)
                .findFirst();
    }

    @Override
    public Cuttable getLeftCuttable() {
        return this.rectangleObstacle;
    }

    @Override
    public Cuttable getRightCuttable() {
        return this.movement;
    }
}
