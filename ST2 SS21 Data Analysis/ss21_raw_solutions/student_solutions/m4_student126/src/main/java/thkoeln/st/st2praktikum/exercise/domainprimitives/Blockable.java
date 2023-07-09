package thkoeln.st.st2praktikum.exercise.domainprimitives;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

public interface Blockable {

    boolean isBlocked(Coordinate current, Coordinate future);
}
