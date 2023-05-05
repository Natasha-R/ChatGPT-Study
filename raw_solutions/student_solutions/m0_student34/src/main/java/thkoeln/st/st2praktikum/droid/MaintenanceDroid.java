package thkoeln.st.st2praktikum.droid;

import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.map.BoundedStraight;
import thkoeln.st.st2praktikum.map.LinearSystem;
import thkoeln.st.st2praktikum.map.Map;
import thkoeln.st.st2praktikum.parser.Direction;

import java.util.function.BiFunction;
import java.util.stream.Stream;

@AllArgsConstructor
public class MaintenanceDroid {
    private final Map map;
    private double[] position;
    private LinearSystem linearSystem;

    public static double[] defaultPosition() {
        return new double[]{3.5, 0.5};
    }

    public void move(Pair<Direction, Integer> movement) {
        BiFunction<double[], Pair<Direction, Integer>, double[]> calculateTargetPosition =
                ((position, direction) -> {
                    switch (movement.getFirst()) {
                        case NORTH:
                            return new double[]{this.position[0], this.position[1] + movement.getSecond()};
                        case EAST:
                            return new double[]{this.position[0] + movement.getSecond(), this.position[1]};
                        case SOUTH:
                            return new double[]{this.position[0], this.position[1] - movement.getSecond()};
                        case WEST:
                            return new double[]{this.position[0] - movement.getSecond(), this.position[1]};
                        default:
                            throw new UnsupportedOperationException();
                    }
                });

        BiFunction<double[], double[], Integer> calculateDistance =
                (startPosition, targetPosition) ->
                        (int) Math.round(Math.sqrt(Stream.of(startPosition, targetPosition)
                            .map((it) -> it[0] + it[1])
                            .map(it -> Math.pow(it, 2))
                            .mapToDouble(Double::doubleValue)
                            .sum()));
                ;

        var targetPosition = calculateTargetPosition.apply(this.position, movement);
        var movementStraight = new BoundedStraight(this.position, targetPosition, this.linearSystem);
        movementStraight = this.map.maxMove(movementStraight);

        var startPosition = this.position;
        targetPosition = movementStraight.at(movementStraight.getEndLambda()).get();
        targetPosition = new double[] {((int) targetPosition[0]) + 0.5, ((int) targetPosition[1]) + 0.5};
        this.position = targetPosition;
    }

    public double[] getPosition() {
        return position;
    }
}
