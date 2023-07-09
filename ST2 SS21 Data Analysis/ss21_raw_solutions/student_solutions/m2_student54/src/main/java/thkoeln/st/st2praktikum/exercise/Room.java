package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Room
{
    private Integer width;
    private Integer height;
    private Cell [][] grid;


    public Room(int width, int height)
    {
        this.width = width;
        this.height = height;
        grid = new Cell[width][height];
        fillWithCells();
    }

    public Integer getWidth()
    {
        return width;
    }

    public Integer getHeight()
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



    public void addWall(Wall wall)
    {
        if(wall.getStart().getX() == wall.getEnd().getX()
                && wall.getStart().getY() != wall.getEnd().getY())
        {
            for (int y = wall.getStart().getY(); y < wall.getEnd().getY(); y++)
            {
                grid[wall.getStart().getX()][y].setIsWestWall(true);
            }
        }
        else
        {
            for (int x = wall.getStart().getX(); x < wall.getEnd().getX(); x++)
            {
                grid[x][wall.getStart().getY()].setIsSouthWall(true);
            }
        }
    }

    private void fillWithCells()
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
