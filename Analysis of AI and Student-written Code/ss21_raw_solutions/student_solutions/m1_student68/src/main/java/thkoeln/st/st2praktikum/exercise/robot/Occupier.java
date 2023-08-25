package thkoeln.st.st2praktikum.exercise.robot;

import thkoeln.st.st2praktikum.exercise.core.Identifiable;
import thkoeln.st.st2praktikum.exercise.core.Coordinate;
import thkoeln.st.st2praktikum.exercise.barrier.Blockable;


public interface Occupier extends Identifiable, Blockable {
    Boolean occupiedField(Coordinate coordinate);
}

