package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Positionable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.room.domain.Roomable;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connectable;

public interface Movable extends Identifiable
{
    public boolean moveInDirection(TaskType key, Integer command, Connectable connection, Roomable room);

    public Coordinate showActualPosition();

    public Positionable getActualPosition();
}
