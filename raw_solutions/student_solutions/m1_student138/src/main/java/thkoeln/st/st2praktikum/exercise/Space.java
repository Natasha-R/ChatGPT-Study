package thkoeln.st.st2praktikum.exercise;

import java.text.MessageFormat;
import java.util.UUID;

public class Space {
    private Field[][] fieldsArray;

    private UUID uuid;
    public UUID getUuid() { return uuid; }

    private Integer height;
    public Integer getWidth() {
        return width;
    }

    private Integer width;
    public Integer getHeight() { return height; }


    public Space(Integer _height, Integer _width)
    {
        if(_height < 1 || _width < 1)
        {
            throw new IndexOutOfBoundsException("height and width must be at least 1");
        }
        uuid = UUID.randomUUID();
        fieldsArray = new Field[_height][_width];
        height = _height;
        width = _width;
        for(int i = 0; i < _height; i++)
        {
            for(int u = 0; u < _width; u++)
            {
                fieldsArray[i][u] = new Field();
            }
        }
    }

    public void addObstacle(String obstacleString)
    {
        String[] obstacles = obstacleString.split("-");
        String obstacleStart[] = obstacles[0].replace("(", "").replace(")", "").split(",");
        String obstacleEnd[] = obstacles[1].replace("(", "").replace(")", "").split(",");

        int obstacleStartX = Integer.parseInt(obstacleStart[0]);
        int obstacleStartY = Integer.parseInt(obstacleStart[1]);

        int obstacleEndX = Integer.parseInt(obstacleEnd[0]);
        int obstacleEndY = Integer.parseInt(obstacleEnd[1]);


        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (x == obstacleStartX && obstacleStartX == obstacleEndX && obstacleStartY <= y && y <= obstacleEndY -1)
                {
                    fieldsArray[y][x].setWestBarrier(true);
                }
                else if (y == obstacleStartY && obstacleStartY == obstacleEndY && obstacleStartX <= x && x <= obstacleEndX -1)
                {
                    fieldsArray[y][x].setSouthBarrier(true);
                }
            }
        }
    }

    public Field getField(Integer x, Integer y) {
        if(fieldExists(x, y))
        {
            return fieldsArray[y][x];
        }
        else
        {
            throw new IndexOutOfBoundsException(MessageFormat.format("You tried to access field {0}|{1} in a {2}|{3} matrix", x, y, width, height));
        }
    }

    public boolean fieldExists(Integer x, Integer y) {
        if(y >= 0 && y < height && x >= 0 && x < width)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void printSpace()
    {
        System.out.println("---------------------------------------------------------");
        for(int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                System.out.print("{");
                if (fieldsArray[y][x].isWestBarrier())
                    System.out.print("w;");
                else
                    System.out.print("-;");
                if (fieldsArray[y][x].isSouthBarrier())
                    System.out.print("s;");
                else
                    System.out.print("-;");
                if (fieldsArray[y][x].isOccupiedByCleaner())
                    System.out.print("c");
                else
                    System.out.print("-");
                System.out.print("(" + x + ";" + y + ")");
                System.out.print("}");
            }
            System.out.println("");
        }

        System.out.println("---------------------------------------------------------");
    }
}
