package thkoeln.st.st2praktikum.exercise.obstacle;

import thkoeln.st.st2praktikum.exercise.Coordinate;

public interface Blocking {
    Coordinate getBlockCoordinate(Coordinate currentCoordinate, Coordinate targetCoordinate);
}
