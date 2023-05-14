package thkoeln.st.st2praktikum.exercise;

public interface Movable extends Identifiable
{
    public boolean moveInDirection(TaskType key, Integer command, Connectable connection, Roomable room);

    public Coordinate showActualPosition();

    public Positionable getActualPosition();
}
