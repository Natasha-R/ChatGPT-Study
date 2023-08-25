package thkoeln.st.st2praktikum.exercise.abstractions.interfaces;

import thkoeln.st.st2praktikum.exercise.Coordinate;

public interface Blocker {

    boolean isBlocked(Coordinate current, Coordinate future);
}
