package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.Positionable;

public interface FieldBlockable extends Identifiable
{
    public Positionable getBeginOfBlocker();
    public Positionable getEndOfBlocker();
}
