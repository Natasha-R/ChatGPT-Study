package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;


@Getter
@Entity
@NoArgsConstructor
public class Room extends AbstractRoom
{
    private Integer width;

    private Integer height;

    public Room(Integer width, Integer height) throws RuntimeException
    {
        super();
        if (width == null || height == null)
            throw new RuntimeException("room size should not be null");

        if (width.intValue() < 0 || height.intValue() < 0 )
            throw new RuntimeException("Room size should not be negative!");
        else
        {
            this.width = width;
            this.height = height;
        }
    }


    @Override
    public UUID identify()
    {
        return roomID;
    }

    public Connection getConnectionByID(UUID connectionID)
    {
        for (Connection connection : roomConnections)
        {
            if (connection.getConnectionID()==connectionID)
                return connection;
        }
        return null;
    }



    @Override
    public void addWallToRoom(Wall wall)
    {
        this.walls.add(wall);
    }

    @Override
    public void addRoomConnection(Connectable connection)
    {
        this.roomConnections.add((Connection) connection);
    }

    @Override
    public void addRobotToRoom(Transportable robot)
    {
        if (getRobotByID(robot.identify())==null)
            this.cleaningRobots.add((TidyUpRobot) robot);
    }

    @Override
    public UUID hasConnectionToRoom(UUID roomID)
    {
        for (Connectable connection : roomConnections)
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
