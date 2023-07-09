package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

public interface Blocking {
    Coordinate getBlockCoordinate(Coordinate currentCoordinate, Coordinate targetCoordinate);
}
