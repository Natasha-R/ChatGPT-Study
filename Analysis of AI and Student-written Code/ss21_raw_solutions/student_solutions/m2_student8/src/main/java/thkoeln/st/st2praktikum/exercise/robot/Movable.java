package thkoeln.st.st2praktikum.exercise.robot;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.TaskType;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Positionable;
import thkoeln.st.st2praktikum.exercise.room.Roomable;

public interface Movable extends Identifiable
{
    public boolean moveInDirection(TaskType key, Integer command, Connectable connection, Roomable room);

    public Coordinate showActualPosition();

    public Positionable getActualPosition();
}
