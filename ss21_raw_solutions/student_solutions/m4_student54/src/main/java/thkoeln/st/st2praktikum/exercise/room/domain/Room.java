package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dom4j.tree.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Cell;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;


import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room  extends AbstractEntity
{
    @Id
    private UUID id;
    private Integer width;
    private Integer height;

    @ElementCollection
    private List<Cell> grid;


    public Room(UUID id, int width, int height)
    {
        this.id = id;
        this.width = width;
        this.height = height;
        grid = new ArrayList<Cell>();
        fillWithCells();
    }

    public List<Cell> getGridAsList()
    {
        return grid;
    }

    public UUID getRoomId()
    {
        return id;
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
        Cell [][] gridArray = new Cell[width][height];
        int index = 0;
        for(int i=0; i<width; i++)
        {
            for(int j=0; j<height; j++)
            {
                gridArray[i][j] = grid.get(index++);
            }
        }
        return gridArray;
    }

    public void setGridCellToOccupied(int x, int y)
    {
        grid.get(convertCoordinatesToListIndex(x, y)).setIsOccupied(true);

    }

    public void setGridCellToUnoccupied(int x, int y)
    {
        grid.get(convertCoordinatesToListIndex(x, y)).setIsOccupied(false);
    }

    public void setGridCellToConnection(int x, int y, Connection connection)
    {
        grid.get(convertCoordinatesToListIndex(x, y)).setIsConnection(true);
        grid.get(convertCoordinatesToListIndex(x, y)).setConnection(connection);
    }



    public void addWall(Wall wall)
    {
        if(wall.getStart().getX().equals(wall.getEnd().getX())
                && !wall.getStart().getY().equals(wall.getEnd().getY()))
        {
            for (int y = wall.getStart().getY(); y < wall.getEnd().getY(); y++)
            {
                grid.get(convertCoordinatesToListIndex(wall.getStart().getX(), y)).setIsWestWall(true);
            }
        }
        else
        {
            for (int x = wall.getStart().getX(); x < wall.getEnd().getX(); x++)
            {
                grid.get(convertCoordinatesToListIndex(x, wall.getStart().getY())).setIsSouthWall(true);
            }
        }
    }

    private void fillWithCells()
    {
        for(int i=0; i<width*height; i++)
        {
            grid.add(new Cell());
        }
    }



    private int convertCoordinatesToListIndex(int x, int y)
    {
        return (x * height + (y + 1))-1;
    }
}
