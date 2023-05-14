package thkoeln.st.st2praktikum.exercise.room.domain;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Positionable;

public interface FieldBlockable extends Identifiable
{
    public Coordinate getBeginOfBlocker();
    public Coordinate getEndOfBlocker();
}
