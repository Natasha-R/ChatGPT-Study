package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.Coordinate;


public interface Occupier extends Identifiable, Blocking {
    Boolean occupiedField(Coordinate coordinate);
}

