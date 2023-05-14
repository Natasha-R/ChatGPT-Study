package thkoeln.st.st2praktikum.exercise.coordinate;

import org.springframework.data.util.Pair;

public class Coordinate implements CoordinateInterface {
    private Pair<Integer, Integer> coordinate;

    public Coordinate(Integer x , Integer y){
        this.coordinate = Pair.of(x,y);
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
