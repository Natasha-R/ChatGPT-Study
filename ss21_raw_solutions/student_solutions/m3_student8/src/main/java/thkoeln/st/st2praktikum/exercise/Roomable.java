package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Roomable extends Identifiable
{
    public void addWallToRoom(Wall wall);

    public void addRoomConnection(Connectable connection);

    public void addRobotToRoom(Transportable robot);

    public UUID hasConnectionToRoom(UUID roomID);

    public void removeRobot(UUID robotID);
}
