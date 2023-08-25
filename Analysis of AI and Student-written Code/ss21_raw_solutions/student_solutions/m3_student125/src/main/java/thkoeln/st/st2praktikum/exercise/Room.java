package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exceptions.BarrierOutOfBoundsException;
import thkoeln.st.st2praktikum.exercise.exceptions.ConnectionOutOfBoundsException;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room
{
    @Id
    private UUID roomID;

    private transient Field[][] roomGrid;
    private int width, height;

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
        return roomGrid;
    }

    public void setRoomGrid(Field[][] roomGrid)
    {
        this.roomGrid = roomGrid;
    }

    public Room(int width, int height)
    {
        this.roomID = UUID.randomUUID();
        this.width = width;
        this.height = height;
        this.roomGrid = new Field[width][height];

        for(int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                roomGrid[i][j] = new Field();
            }
        }
    }

    public void addBarrier(Barrier barrier)
    {
        int startX = barrier.getStart().getX();
        int startY = barrier.getStart().getY();

        int endX = barrier.getEnd().getX();
        int endY = barrier.getEnd().getY();

        if (startX > this.width || startY > this.height || endX > this.width || endY > this.height)
        {
            throw new BarrierOutOfBoundsException("You cannot place a barrier outside of the room grid!", new RuntimeException());
        }

        if (startX == endX)
        {
            for (int y = startY; y < endY; y++)
            {
                roomGrid[startX][y] = new Barrier("we", true);
            }
        }
        else
        {
            for (int x = startX; x < endX; x++)
            {
                roomGrid[x][startY] = new Barrier("so", true);
            }
        }
    }

    public void setupConnectionStartpoint(Connection connection)
    {
        if (connection.sourceCoordinate.getX() > this.width || connection.sourceCoordinate.getY() > this.height)
        {
            throw new ConnectionOutOfBoundsException("A connection must be placed inside the room grid!", new RuntimeException());
        }

        roomGrid[connection.sourceCoordinate.getX()][connection.sourceCoordinate.getY()] = connection;
    }
}
