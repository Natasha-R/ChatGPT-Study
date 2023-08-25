package thkoeln.st.st2praktikum.exercise.robot;

import thkoeln.st.st2praktikum.exercise.obstacle.Blocking;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.core.Identifiable;


public interface Occupier extends Identifiable, Blocking {
    Boolean occupiedField(Coordinate coordinate);
}

