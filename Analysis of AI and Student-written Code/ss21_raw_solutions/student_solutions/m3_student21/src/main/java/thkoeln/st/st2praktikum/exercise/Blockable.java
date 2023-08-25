package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Coordinate;

public interface Blockable {
    /**
     * Asks the object if the coordinate is already blocked by it
     * @param coordinate
     */
    public boolean positionBlocked(Coordinate coordinate);
}
