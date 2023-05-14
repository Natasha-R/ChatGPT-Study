package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;


import thkoeln.st.st2praktikum.exercise.Blocking;
import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;


public interface Occupier extends Identifiable, Blocking {
    Boolean occupiedField(Coordinate coordinate);
}

