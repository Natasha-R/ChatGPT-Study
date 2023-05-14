package thkoeln.st.st2praktikum.exercise.room.domain;

import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.Transportable;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connectable;

import java.util.UUID;

public interface Roomable extends Identifiable
{
    public void addWallToRoom(Wall wall);

    public void addRoomConnection(Connectable connection);

    public void addRobotToRoom(Transportable robot);

    public UUID hasConnectionToRoom(UUID roomID);

    public void removeRobot(UUID robotID);
}
