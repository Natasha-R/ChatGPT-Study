package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Room
{
    private int width;
    private int height;
    private Cell [][] grid;


    public Room(int width, int height)
    {
        this.width = width;
        this.height = height;
        grid = new Cell[width][height];
        fillWithCells();
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Cell[][] getGrid()
    {
        return grid;
    }

    public void setGridCellToOccupied(int x, int y)
    {
        grid[x][y].setIsOccupied(true);
    }

    public void setGridCellToUnoccupied(int x, int y)
    {
        grid[x][y].setIsOccupied(false);
    }

    public void setGridCellToConnection(int x, int y, UUID connectionId)
    {
        grid[x][y].setIsConnection(true);
        grid[x][y].setConnectionID(connectionId);
    }

    private Position extractStartPositionFromWallString(String wallString)
    {
        String startPosition = wallString.substring(1, wallString.indexOf('-') - 1);
        int startPositionX = Integer.parseInt(startPosition.substring(0, startPosition.indexOf(',')));
        int startPositionY = Integer.parseInt(startPosition.substring(startPosition.indexOf(',') + 1));

        return new Position(startPositionX, startPositionY);
    }

    private Position extractEndPositionFromWallString(String wallString)
    {
        String endPosition = wallString.substring(wallString.indexOf('-') + 2, wallString.length() - 1);
        int endPositionX = Integer.parseInt(endPosition.substring(0, endPosition.indexOf(',')));
        int endPositionY = Integer.parseInt(endPosition.substring(endPosition.indexOf(',') + 1));

        return new Position(endPositionX, endPositionY);
    }

    public void addWall(String wallString)
    {
        Position startPosition = extractStartPositionFromWallString(wallString);
        Position endPosition = extractEndPositionFromWallString(wallString);

        if(startPosition.getX() == endPosition.getX()
                && startPosition.getY() != endPosition.getY())
        {
            for (int y = startPosition.getY(); y < endPosition.getY(); y++)
            {
                grid[startPosition.getX()][y].setIsWestWall(true);
            }
        }
        else
        {
            for (int x = startPosition.getX(); x < endPosition.getX(); x++)
            {
                grid[x][startPosition.getY()].setIsSouthWall(true);
            }
        }
    }

    public void fillWithCells()
    {
        for (int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                grid[x][y] = new Cell();
            }
        }
    }
}
