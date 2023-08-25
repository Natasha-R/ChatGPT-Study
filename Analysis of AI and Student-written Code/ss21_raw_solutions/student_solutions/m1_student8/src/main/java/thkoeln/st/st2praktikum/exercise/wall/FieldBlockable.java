package thkoeln.st.st2praktikum.exercise.wall;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.position.Positionable;

public interface FieldBlockable extends Identifiable
{
    public Positionable getBeginOfBlocker();
    public Positionable getEndOfBlocker();
}
