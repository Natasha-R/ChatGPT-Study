package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exceptions.BarrierOutOfBoundsException;
import thkoeln.st.st2praktikum.exercise.exceptions.ConnectionOutOfBoundsException;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room
{
    @Id
    private UUID roomID;
    @ElementCollection
    public List<Field> roomGrid;
    private int width, height;

    public Room(int width, int height)
    {
        this.roomID = UUID.randomUUID();
        this.width = width;
        this.height = height;
        this.initRoomGrid();
    }

    public Room(UUID roomId, int width, int height, List<Field> roomGrid)
    {
        this.roomID = roomId;
        this.width = width;
        this.height = height;
        this.roomGrid = roomGrid;
    }

    private int convertCoordinatesToListIndex(int x, int y)
    {
        return (x * height + (y + 1)) - 1;
    }

    private void initRoomGrid()
    {
        roomGrid = new LinkedList<>();
        for (int i = 0; i < getWidth() * getHeight(); i++)
        {
            roomGrid.add(new Field());
        }
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public UUID getRoomId()
    {
        return roomID;
    }

    public void setRoomId(UUID roomID)
    {
        this.roomID = roomID;
    }

    public Field[][] getRoomGrid()
    {
        Field[][] tmpRoom = new Field[this.getHeight()][this.getWidth()];
        int index = 0;
        for (int i = 0; i < this.getWidth(); i++)
        {
            for (int j = 0; j < this.getHeight(); j++)
            {
                tmpRoom[i][j] = roomGrid.get(index++);
            }
        }
        return tmpRoom;
    }

    public void addBarrier(Field barrier)
    {
        int startX = barrier.getStart().getX();
        int startY = barrier.getStart().getY();

        int endX = barrier.getEnd().getX();
        int endY = barrier.getEnd().getY();

        //Check if any of the provided coordinates is outside of the grid
        if (startX > this.width || startY > this.height || endX > this.width || endY > this.height)
        {
            throw new BarrierOutOfBoundsException("You cannot place a barrier outside of the room grid!", new RuntimeException());
        }

        //Place Barriers inside the grid by checking their direction (vertical, horizontal)
        if (startX == endX)
        {
            //Vertical Barrier
            for (int y = startY; y < endY; y++)
            {
                roomGrid.get(convertCoordinatesToListIndex(startX, y)).setHasWesternBarrier(true);
                //roomGrid.set(convertCoordinatesToListIndex(startX, y), new Field("we", true));
            }
        }
        else
        {
            //Horizontal Barrier
            for (int x = startX; x < endX; x++)
            {
                roomGrid.get(convertCoordinatesToListIndex(x, startY)).setHasSouthernBarrier(true);
            }
        }
    }

    public void setupConnectionStartPoint(Connection connection)
    {
        //Check if the Connection that needs to be placed is outside of the grid
        if (connection.sourceCoordinate.getX() > this.width || connection.sourceCoordinate.getY() > this.height)
        {
            throw new ConnectionOutOfBoundsException("A connection must be placed inside the room grid!", new RuntimeException());
        }

        getRoomGrid()[connection.sourceCoordinate.getX()][connection.sourceCoordinate.getY()].isConnection = true; //= connection;
    }
}
