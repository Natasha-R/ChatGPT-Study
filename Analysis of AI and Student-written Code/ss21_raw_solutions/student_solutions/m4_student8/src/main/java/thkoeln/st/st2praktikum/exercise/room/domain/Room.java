package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.Transportable;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connectable;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
@NoArgsConstructor
public class Room extends AbstractRoom
{
    private Integer width;

    private Integer height;


    @OneToMany
    protected List<Connection> roomConnections;

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

        this.roomConnections = new ArrayList<>();

    }


    @Override
    public UUID identify()
    {
        return roomID;
    }

    public Connection getConnectionByID(UUID connectionID)
    {
        for (Connectable connection : roomConnections)
        {
            if (connection.identify()==connectionID)
                return (Connection) connection;
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
        if (robot.identify()!=null)
            this.cleaningRobots.add(robot);
    }

    @Override
    public UUID hasConnectionToRoom(UUID roomID)
    {
        for (Connectable connection : roomConnections)
        {
            if (connection.getDestationRoomID().equals(roomID))
            {
                return connection.identify();
            }
        }
        return null;
    }

    public Connection getConnectionToRoom(UUID roomID)
    {
        for (Connectable connection : roomConnections)
        {
            if (connection.getDestationRoomID().equals(roomID))
            {
                return (Connection) connection;
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
