package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Room
{
    private UUID roomID;
    private Field[][] roomGrid;
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

    public UUID getRoomID()
    {
        return roomID;
    }

    public void setRoomID(UUID roomID)
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

    public void setupBarrierAt(String barrierString)
    {
        String from = barrierString.split("-")[0];
        String to = barrierString.split("-")[1];

        int fromX = Integer.parseInt(from.split(",")[0].replace("(", ""));
        int fromY = Integer.parseInt(from.split(",")[1].replace(")", ""));

        int toX = Integer.parseInt(to.split(",")[0].replace("(", ""));
        int toY = Integer.parseInt(to.split(",")[1].replace(")", ""));

        if (fromX == toX)
        {
            for (int y = fromY; y < toY; y++)
            {
                roomGrid[fromX][y] = new Barrier("we");
            }
        }
        else
        {
            for (int x = fromX; x < toX; x++)
            {
                roomGrid[x][fromY] = new Barrier("so");
            }
        }
    }

    public void setupConnectionStartpoint(Connection connection)
    {
        String[] sourceCoordinates = extractCoordinates(connection.sourceCoordinate);
        roomGrid[Integer.parseInt(sourceCoordinates[0])][Integer.parseInt(sourceCoordinates[1])] = connection;
    }

    public String[] extractCoordinates(String coordinateString)
    {
        String[] result = new String[2];

        result[0] = coordinateString.split(",")[0].replace("(", "");
        result[1] = coordinateString.split(",")[1].replace(")", "");

        return result;
    }
}
