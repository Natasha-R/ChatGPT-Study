package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TidyUpRobot extends AbstractEntity implements IRobotMovement
{
    @Id
    private UUID robotId;

    private UUID currentRoom;
    private String name;
    private Coordinate position;

    public TidyUpRobot(String name)
    {
        this.robotId = UUID.randomUUID();
        this.name = name;
    }

    public UUID getRobotId()
    {
        return robotId;
    }

    public void setRobotId(UUID robotId)
    {
        this.robotId = robotId;
    }

    public UUID getRoomId()
    {
        return currentRoom;
    }

    public void setCurrentRoom(UUID currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Coordinate getCoordinate()
    {
        return this.position;
    }

    public void setPosition(Coordinate position)
    {
        this.position = position;
    }

    @Override
    public boolean executeCommand(Order order, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections)
    {
        switch (order.getOrderType())
        {
            case NORTH:
            case WEST:
            case EAST:
            case SOUTH:
            {
                return move(order, rooms);
            }
            case TRANSPORT:
            {
                return transport(rooms, order.getGridId());
            }
            case ENTER:
            {
                return spawnAtPosition(new Coordinate(0, 0), order.getGridId(), rooms);
            }
        }
        return false;
    }

    @Override
    public boolean move(Order order, HashMap<UUID, Room> rooms)
    {
        for (int i = 0; i < order.getNumberOfSteps(); i++)
        {
            switch (order.getOrderType())
            {
                case NORTH:
                {
                    moveUp(rooms);
                    break;
                }
                case SOUTH:
                {
                    moveDown(rooms);
                    break;
                }
                case EAST:
                {
                    moveRight(rooms);
                    break;
                }
                case WEST:
                {
                    moveLeft(rooms);
                    break;
                }
                default: {}
            }
        }
        return true;
    }

    @Override
    public boolean spawnAtPosition(Coordinate position, UUID roomToSpawnIn, HashMap<UUID, Room> rooms)
    {
        if (!rooms.get(roomToSpawnIn).getRoomGrid()[position.getX()][position.getY()].fieldOccupied)
        {
            setCurrentRoom(roomToSpawnIn);
            setPosition(position);
            occupyFieldAtPosition(position, rooms);
            return true;
        }
        return false;
    }

    @Override
    public boolean collidesAtPosition(Coordinate position, String moveDirection, HashMap<UUID, Room> rooms)
    {
        if (rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()] instanceof Barrier)
        {
            switch(moveDirection)
            {
                case "no":
                {
                    return ((Barrier) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).getHasNorthernBarrier();
                }
                case "so":
                {
                    return ((Barrier) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).getHasSouthernBarrier();
                }
                case "we":
                {
                    return ((Barrier) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).getHasWesternBarrier();
                }
                case "ea":
                {
                    return ((Barrier) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).getHasEasternBarrier();
                }
                default: {}
            }
        }

        return false;
    }

    @Override
    public boolean transport(HashMap<UUID, Room> rooms, UUID targetRoom)
    {
        if (rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()] instanceof Connection)
        {
            UUID destinationId = ((Connection) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).destinationId;
            Coordinate destinationCoordinate = ((Connection) rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()]).destinationCoordinate;
            releaseFieldAtPosition(position, rooms);
            return spawnAtPosition(new Coordinate(destinationCoordinate.getX(), destinationCoordinate.getY()), destinationId, rooms);
        }
        return false;
    }

    @Override
    public boolean collidesWithRobotAtPosition(Coordinate position, HashMap<UUID, Room> rooms)
    {
        return rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()].fieldOccupied;
    }

    private boolean moveUp(HashMap<UUID, Room> rooms)
    {
        if (position != null && position.getY() + 1 <= rooms.get(currentRoom).getHeight() - 1)
        {
            if (!collidesAtPosition(new Coordinate(position.getX(), position.getY() + 1), "so", rooms)
                    && !collidesWithRobotAtPosition(new Coordinate(position.getX(), position.getY() + 1), rooms))
            {
                Coordinate lastPosition = new Coordinate(position.getX(), position.getY());
                position.setY(position.getY() + 1);
                occupyFieldAtPosition(position, rooms);
                releaseFieldAtPosition(lastPosition, rooms);
                return true;
            }
        }
        return false;
    }

    private boolean moveDown(HashMap<UUID, Room> rooms)
    {
        if (position != null && !(position.getY() - 1 < 0))
        {
            if (!collidesAtPosition(new Coordinate(position.getX(), position.getY() - 1), "so", rooms)
                    && !collidesWithRobotAtPosition(new Coordinate(position.getX(), position.getY() - 1), rooms))
            {
                Coordinate lastPosition = new Coordinate(position.getX(), position.getY());
                position.setY(position.getY() - 1);
                occupyFieldAtPosition(position, rooms);
                releaseFieldAtPosition(lastPosition, rooms);
                return true;
            }
        }
        return false;
    }

    private boolean moveRight(HashMap<UUID, Room> rooms)
    {
        if (position != null && position.getX() + 1 <= rooms.get(currentRoom).getWidth() - 1)
        {
            if (!collidesAtPosition(new Coordinate(position.getX() + 1, position.getY()), "we", rooms)
                    && !collidesWithRobotAtPosition(new Coordinate(position.getX() + 1, position.getY()), rooms))
            {
                Coordinate lastPosition = new Coordinate(position.getX(), position.getY());
                position.setX(position.getX() + 1);
                occupyFieldAtPosition(position, rooms);
                releaseFieldAtPosition(lastPosition, rooms);
                return true;
            }
        }
        return false;
    }

    private boolean moveLeft(HashMap<UUID, Room> rooms)
    {
        if (position != null && !(position.getX() - 1 < 0))
        {
            if (!collidesAtPosition(new Coordinate(position.getX() - 1, position.getY()), "we", rooms)
                    && !collidesWithRobotAtPosition(new Coordinate(position.getX() - 1, position.getY()), rooms))
            {
                Coordinate lastPosition = new Coordinate(position.getX(), position.getY());
                position.setX(position.getX() - 1);
                occupyFieldAtPosition(position, rooms);
                releaseFieldAtPosition(lastPosition, rooms);
                return true;
            }
        }
        return false;
    }

    private void occupyFieldAtPosition(Coordinate position, HashMap<UUID, Room> rooms)
    {
        rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()].fieldOccupied = true;
    }

    private void releaseFieldAtPosition(Coordinate position, HashMap<UUID, Room> rooms)
    {
        rooms.get(currentRoom).getRoomGrid()[position.getX()][position.getY()].fieldOccupied = false;
    }
}
