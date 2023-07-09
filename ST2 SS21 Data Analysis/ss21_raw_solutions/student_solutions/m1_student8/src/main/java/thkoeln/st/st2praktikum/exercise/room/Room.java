package thkoeln.st.st2praktikum.exercise.room;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.robot.Transportable;
import thkoeln.st.st2praktikum.exercise.wall.FieldBlockable;

import java.util.UUID;


@Getter
public class Room extends AbstractRoom
{
    private int width;

    private int height;

    public Room(int width, int height)
    {
        super();
        this.width = width;
        this.height = height;

    }


    @Override
    public UUID identify()
    {
        return roomID;
    }

    @Override
    public void addWallToRoom(FieldBlockable wall)
    {
        this.walls.add(wall);
    }

    @Override
    public void addRoomConnection(Connectable connection)
    {
        this.roomConnections.put(connection.identify(),connection);
    }

    @Override
    public void addRobotToRoom(Transportable robot)
    {
        this.cleaningRobots.put(robot.identify(),robot);
    }

    @Override
    public UUID hasConnectionToRoom(UUID roomID)
    {
        for (Connectable connection : roomConnections.values())
        {
            if (connection.showConnectedDestinationRoom().identify().equals(roomID))
            {
                return connection.identify();
            }
        }
        return null;
    }

    @Override
    public void removeRobot(UUID robotID)
    {
     this.cleaningRobots.remove(robotID);
    }


}
