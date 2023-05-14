package thkoeln.st.st2praktikum.exercise;

public interface FieldBlockable extends Identifiable
{
    public Positionable getBeginOfBlocker();
    public Positionable getEndOfBlocker();
}
