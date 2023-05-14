package thkoeln.st.st2praktikum.exercise.barrier;

import thkoeln.st.st2praktikum.exercise.core.Coordinate;

public interface Blockable {
    Coordinate getBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate);
}
