package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TidyUpRobot extends AbstractEntity implements IRobot {

    @Id
    private java.util.UUID id;
    private String name;
    private java.util.UUID currentRoom;
    private Vector2D currentVector2D;

    @ElementCollection
    private List<Task> tasks;

    public TidyUpRobot(java.util.UUID id, String name)
    {
        this.id = id;
        this.name = name;
        this.currentVector2D = null;
        tasks = new LinkedList<>();
    }


    public java.util.UUID getRoomId()
    {
        return currentRoom;
    }

    public void setCurrentRoom(java.util.UUID currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    public Vector2D getPosition()
    {
        return currentVector2D;
    }

    public List<Task> getTasks()
    {
        return tasks;
    }

    @Override
    public boolean executeCommand(Task task, HashMap<UUID, Room> rooms)
    {
        tasks.add(task);
        if(   (task.getTaskType() == TaskType.NORTH
            || task.getTaskType() == TaskType.EAST
            || task.getTaskType() == TaskType.SOUTH
            || task.getTaskType() == TaskType.WEST)
            && currentRoom != null)
        {
            return move(rooms.get(currentRoom), task.getTaskType(), task.getNumberOfSteps());
        }
        else if((task.getTaskType() == TaskType.TRANSPORT) && rooms.get(task.getGridId()) != null)
        {
            return transport(task.getGridId(), rooms);
        }
        else if((task.getTaskType() == TaskType.ENTER) && rooms.get(task.getGridId()) != null)
        {
            return enter(rooms.get(task.getGridId()), task.getGridId());
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean move(Room room, TaskType type, int steps)
    {
        boolean result = false;

        for(int i = 0; i < steps; i++)
        {
            switch ((type))
            {
                case NORTH:
                {
                    result = moveNorth(room);
                }
                break;
                case EAST:
                {
                    result = moveEast(room);
                }
                break;
                case SOUTH:
                {
                    result = moveSouth(room);
                }
                break;
                case WEST:
                {
                    result = moveWest(room);
                }
                break;
            }
            if(!result)
            {
                break;
            }
        }
        return true;
    }

    @Override
    public boolean transport(java.util.UUID destinationRoomId, HashMap<UUID, Room> rooms)
    {
        int currentPositionX = currentVector2D.getX();
        int currentPositionY = currentVector2D.getY();

        if(!rooms.get(currentRoom).getGrid()[currentVector2D.getX()][currentVector2D.getY()].getIsConnection())
        {
            return false;
        }

        Connection connection = rooms.get(currentRoom).getGrid()[currentVector2D.getX()][currentVector2D.getY()].getConnection();


        if(!rooms.get(connection.getDestinationRoom()).getRoomId().equals(destinationRoomId)
                || rooms.get(destinationRoomId).getGrid()[connection.getDestinationCoordinate().getX()][connection.getDestinationCoordinate().getY()].getIsOccupied())
        {
            return false;
        }

        this.setCurrentRoom(destinationRoomId);
        this.currentVector2D = new Vector2D(connection.getDestinationCoordinate().getX(), connection.getDestinationCoordinate().getY());

        // verändert man wirklich das element im Repo?
        rooms.get(destinationRoomId).setGridCellToOccupied(connection.getDestinationCoordinate().getX(), connection.getDestinationCoordinate().getY());
        rooms.get(connection.getSourceRoom()).setGridCellToUnoccupied(currentPositionX, currentPositionY);
        return true;
    }

    @Override
    public boolean enter(Room room, java.util.UUID destinationRoomId)
    {
        if(room.getGrid()[0][0].getIsOccupied())
        {
            return false;
        }

        this.setCurrentRoom(destinationRoomId);
        this.currentVector2D = new Vector2D(0, 0);
        room.getGrid()[0][0].setIsOccupied(true);
        return true;
    }

    private boolean moveNorth(Room room)
    {
        int currentRoomHeight;
        currentRoomHeight = room.getHeight();
        Cell[][] currentRoomGrid = room.getGrid();

        if (currentVector2D.getY() + 1 > currentRoomHeight - 1
                || currentRoomGrid[currentVector2D.getX()][currentVector2D.getY() + 1].getIsSouthWall()
                || currentRoomGrid[currentVector2D.getX()][currentVector2D.getY() + 1].getIsOccupied())
        {
            return false;
        }

        currentVector2D = new Vector2D(currentVector2D.getX(), currentVector2D.getY() + 1);
        room.setGridCellToOccupied(currentVector2D.getX(), currentVector2D.getY());
        room.setGridCellToUnoccupied(currentVector2D.getX(), currentVector2D.getY() - 1);
        return true;
    }

    private boolean moveEast(Room room)
    {
        int currentRoomWidth;
        currentRoomWidth = room.getWidth();
        Cell[][] currentRoomGrid = room.getGrid();

        if (currentVector2D.getX() + 1 > currentRoomWidth - 1
                || currentRoomGrid[currentVector2D.getX() + 1][currentVector2D.getY()].getIsWestWall()
                || currentRoomGrid[currentVector2D.getX() + 1][currentVector2D.getY()].getIsOccupied())
        {
            return false;
        }

        currentVector2D = new Vector2D(currentVector2D.getX() + 1, currentVector2D.getY());
        room.setGridCellToOccupied(currentVector2D.getX(), currentVector2D.getY());
        room.setGridCellToUnoccupied(currentVector2D.getX() - 1, currentVector2D.getY());
        return true;
    }

    private boolean moveSouth(Room room)
    {
        Cell[][] currentRoomGrid = room.getGrid();

        if (currentVector2D.getY() - 1 < 0
                || currentRoomGrid[currentVector2D.getX()][currentVector2D.getY()].getIsSouthWall()
                || currentRoomGrid[currentVector2D.getX()][currentVector2D.getY() - 1].getIsOccupied())
        {
            return false;
        }

        currentVector2D = new Vector2D(currentVector2D.getX(), currentVector2D.getY() - 1);
        room.setGridCellToOccupied(currentVector2D.getX(), currentVector2D.getY());
        room.setGridCellToUnoccupied(currentVector2D.getX(), currentVector2D.getY() + 1);
        return true;
    }

    private boolean moveWest(Room room)
    {
        Cell[][] currentRoomGrid = room.getGrid();

        if (currentVector2D.getX() - 1 < 0
                || currentRoomGrid[currentVector2D.getX()][currentVector2D.getY()].getIsWestWall()
                || currentRoomGrid[currentVector2D.getX() - 1][currentVector2D.getY()].getIsOccupied())
        {
            return false;
        }

        currentVector2D = new Vector2D(currentVector2D.getX() - 1, currentVector2D.getY());
        room.setGridCellToOccupied(currentVector2D.getX(), currentVector2D.getY());
        room.setGridCellToUnoccupied(currentVector2D.getX() + 1, currentVector2D.getY());
        return true;
    }

    public Vector2D getVector2D()
    {
        return currentVector2D;
    }
}
