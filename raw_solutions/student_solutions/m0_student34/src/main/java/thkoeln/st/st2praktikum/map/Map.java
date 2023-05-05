package thkoeln.st.st2praktikum.map;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class Map {

    private final List<Straight> boundaries;
    private final LinearSystem linearSystem;

    public static Map defaultMap() {
        var ls = new LinearSystem();
        var ex1 = new double[]{1, 0};
        var ex2 = new double[]{0, 1};
//        var boundaries = Arrays.asList(
//                new Straight(ex1, new double[]{0, 0}, ls),
//                new Straight(ex2, new double[]{0, 0}, ls),
//                new Straight(ex1, new double[]{0, 8}, ls),
//                new Straight(ex2, new double[]{12, 0}, ls),
//                // v1
//                new BoundedStraight(ex1, new double[]{0.5, 4.5}, ls, 0, 8),
//                new BoundedStraight(ex1, new double[]{0.5, 3.5}, ls, 0, 8),
//                new BoundedStraight(ex2, new double[]{0.5, 3.5}, ls, 0, 1),
//                new BoundedStraight(ex2, new double[]{8.5, 3.5}, ls, 0, 1),
//                //v2
//                new BoundedStraight(ex1, new double[]{2.5, 3.5}, ls, 0, 1),
//                new BoundedStraight(ex1, new double[]{2.5, -0.5}, ls, 0, 1),
//                new BoundedStraight(ex2, new double[]{2.5, -0.5}, ls, 0, 4),
//                new BoundedStraight(ex2, new double[]{3.5, -0.5}, ls, 0, 4),
//                //v3
//                new BoundedStraight(ex1, new double[]{3.5, 3.5}, ls, 0, 4),
//                new BoundedStraight(ex1, new double[]{3.5, 2.5}, ls, 0, 4),
//                new BoundedStraight(ex2, new double[]{3.5, 2.5}, ls, 0, 1),
//                new BoundedStraight(ex2, new double[]{7.5, 2.5}, ls, 0, 1),
//                //v4
//                new BoundedStraight(ex1, new double[]{6.5, 2.5}, ls, 0, 1),
//                new BoundedStraight(ex1, new double[]{6.5, -0.5}, ls, 0, 1),
//                new BoundedStraight(ex2, new double[]{6.5, -0.5}, ls, 0, 3),
//                new BoundedStraight(ex2, new double[]{7.5, -0.5}, ls, 0, 3)
//        );
        var boundaries = Arrays.asList(
                new Straight(ex1, new double[]{0, 0}, ls),
                new Straight(ex2, new double[]{0, 0}, ls),
                new Straight(ex1, new double[]{0, 8}, ls),
                new Straight(ex2, new double[]{12, 0}, ls),
                // v1
                new BoundedStraight(ex1, new double[]{0.75, 4.25}, ls, 0, 7.5),
                new BoundedStraight(ex1, new double[]{0.75, 3.75}, ls, 0, 7.5),
                new BoundedStraight(ex2, new double[]{0.75, 3.75}, ls, 0, 0.5),
                new BoundedStraight(ex2, new double[]{8.25, 3.75}, ls, 0, 0.5),
                //v2
                new BoundedStraight(ex1, new double[]{2.75, 3.25}, ls, 0, 0.5),
                new BoundedStraight(ex1, new double[]{2.75, -0.75}, ls, 0, 0.5),
                new BoundedStraight(ex2, new double[]{2.75, -0.25}, ls, 0, 3.5),
                new BoundedStraight(ex2, new double[]{3.25, -0.25}, ls, 0, 3.5),
                //v3
                new BoundedStraight(ex1, new double[]{3.75, 3.25}, ls, 0, 3.5),
                new BoundedStraight(ex1, new double[]{3.75, 2.75}, ls, 0, 3.5),
                new BoundedStraight(ex2, new double[]{3.75, 2.75}, ls, 0, 0.5),
                new BoundedStraight(ex2, new double[]{7.25, 2.75}, ls, 0, 0.5),
                //v4
                new BoundedStraight(ex1, new double[]{6.75, 2.25}, ls, 0, 0.5),
                new BoundedStraight(ex1, new double[]{6.75, -0.75}, ls, 0, 0.5),
                new BoundedStraight(ex2, new double[]{6.75, -0.25}, ls, 0, 2.5),
                new BoundedStraight(ex2, new double[]{7.25, -0.25}, ls, 0, 2.5)
        );

        return new Map(boundaries, ls);
    }

    public BoundedStraight maxMove(BoundedStraight move) {
        var startPosition = move.at(move.getBeginLambda()).get();
        var distance = new Comparator<double[]>() {
            @Override
            public int compare(double[] v1, double[] v2) {
                double dist1 = 0.0;
                double dist2 = 0.0;
                for(int i = 0; i < v1.length; i++) {
                    dist1 += Math.pow(v1[i] - startPosition[i], 2);
                    dist2 += Math.pow(v2[i] - startPosition[i], 2);
                }
                return Double.compare(dist1, dist2);
            }
        };
        var maximalPoint = this.boundaries.stream()
                .map(move::cut)
                .filter(Optional::isPresent)
                .map(it -> it.get())
                .min(distance)
                .orElseGet(() -> move.at(move.getEndLambda()).get());

        return new BoundedStraight(move.at(move.getBeginLambda()).get(), maximalPoint, this.linearSystem);
    }
}
