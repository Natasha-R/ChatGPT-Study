package thkoeln.st.st2praktikum.exercise.coordinate;

import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.exercise.Coordinate;

public class MyCoordinate implements MyCoordinateInterface {
    private final Pair<Integer, Integer> coordinate;

    public MyCoordinate(Coordinate coordinate) {
        this.coordinate = Pair.of(coordinate.getX(), coordinate.getY());
    }

    @Override
    public Integer getX() {
        return coordinate.getFirst();
    }

    @Override
    public Integer getY() {
        return coordinate.getSecond();
    }
}
