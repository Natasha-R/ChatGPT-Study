package thkoeln.st.st2praktikum.exercise;


import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversion;
import org.xmlunit.util.Convert;

import java.util.ArrayList;
import java.util.List;

public class Exercise0 implements GoAble {

    class Position
    {
        int x;
        int y;

        public Position(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    class Wall
    {
        Position start;
        Position end;

        // h - horizontally
        // v - vertically
        char orientation;

        public Wall(Position start, Position end, char orientation)
        {
            this.start = start;
            this.end = end;
            this.orientation = orientation;
        }
    }

    class Cell
    {
        boolean south;
        boolean west;

        public Cell()
        {
            south = false;
            west = false;
        }
    }

    public class Grid
    {
        int width;
        int height;
        List<Wall> walls;
        Cell [][] grid;

        public Grid(int width, int height, List<Wall> walls)
        {
            this.width = width;
            this.height = height;
            this.walls = walls;
            grid = new Cell[width][height];
        }

        public void fill_with_cells()
        {
            for (int x = 0; x < width; x++)
            {
                for(int y = 0; y < height; y++)
                {
                    grid[x][y] = new Cell();
                }
            }
        }

        public void buildWalls()
        {
            for(int i=0; i<walls.size(); i++)
            {
                if(walls.get(i).orientation == 'v')
                {
                    for (int y = walls.get(i).start.y; y < walls.get(i).end.y; y++)
                    {
                        grid[walls.get(i).start.x][y].west = true;
                    }
                }
                else
                {
                    for (int x = walls.get(i).start.x; x < walls.get(i).end.x; x++)
                    {
                        grid[x][walls.get(i).start.y].south = true;
                    }
                }
            }
        }
    }





    int grid_width = 12;
    int grid_height = 9;

    List<Wall> walls;

    public Position current_position = new Position(1,7);
    public Grid grid;

    public Exercise0()
    {
        walls = new ArrayList<>();
        walls.add(new Wall(new Position(5,0), new Position(5,2), 'v'));
        walls.add(new Wall(new Position(6,0), new Position(6,4), 'v'));
        walls.add(new Wall(new Position(3,3), new Position(3,9), 'v'));
        walls.add(new Wall(new Position(3,3), new Position(5,3), 'h'));

        grid = new Grid(grid_width, grid_height, walls);
        grid.fill_with_cells();
        grid.buildWalls();

    }

    String build_return_string(Position position)
    {
        return "(" + position.x + "," + position.y + ")";
    }


    @Override
    public String go(String goCommandString) {

        String direction = goCommandString.substring(1,3);
        int steps = Integer.parseInt(goCommandString.substring(4,goCommandString.length()-1));


        for(int i = 0; i < steps; i++)
        {
            switch ((direction))
            {
                case "no":
                {
                    if(current_position.y + 1 <= grid_height)
                    {
                        if(!grid.grid[current_position.x][current_position.y+1].south)
                        {
                            current_position.y += 1;
                        }
                        else
                        {
                            return build_return_string(current_position);
                        }
                    }
                    else
                    {
                        return build_return_string(current_position);
                    }
                }
                break;
                case "ea":
                {
                    if(current_position.x + 1 <= grid_width)
                    {
                        if(!grid.grid[current_position.x+1][current_position.y].west)
                        {
                            current_position.x += 1;
                        }
                        else
                        {
                            return build_return_string(current_position);
                        }
                    }
                    else
                    {
                        return build_return_string(current_position);
                    }
                }
                break;
                case "so":
                {
                    if(current_position.y - 1 >= 0)
                    {
                        if(!grid.grid[current_position.x][current_position.y].south)
                        {
                            current_position.y -= 1;
                        }
                        else
                        {
                            return build_return_string(current_position);
                        }
                    }
                    else
                    {
                        return build_return_string(current_position);
                    }
                }
                break;
                case "we":
                {
                    if(current_position.x - 1 >= 0)
                    {
                        if(!grid.grid[current_position.x][current_position.y].west)
                        {
                            current_position.x -= 1;
                        }
                        else
                        {
                            return build_return_string(current_position);
                        }
                    }
                    else
                    {
                        return build_return_string(current_position);
                    }
                }
                break;
            }
        }
        return build_return_string(current_position);
    }
}
