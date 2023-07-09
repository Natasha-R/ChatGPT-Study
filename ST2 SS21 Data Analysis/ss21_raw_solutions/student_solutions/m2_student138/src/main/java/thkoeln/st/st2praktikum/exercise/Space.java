package thkoeln.st.st2praktikum.exercise;

import java.text.MessageFormat;
import java.util.UUID;

public class Space {
    private Field[][] fieldsArray;
    private UUID uuid;
    private int height;
    private int width;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Space(int _height, int _width)
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

    public Field getField(int x, int y) {
        if(fieldExists(x, y))
        {
            return fieldsArray[y][x];
        }
        else
        {
            throw new IndexOutOfBoundsException(MessageFormat.format("You tried to access field {0}|{1} in a {2}|{3} matrix", x, y, width, height));
        }
    }

    public boolean fieldExists(int x, int y) {
        if(y >= 0 && y < height && x >= 0 && x < width)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public void printField()
    {
        System.out.println("---------------------------------------------------------");
        for(int u = 0; u < height; u++)
        {
            for (int i = 0; i < width; i++)
            {
                System.out.print("{");
                if (fieldsArray[u][i].isWestBarrier())
                    System.out.print("1;");
                else
                    System.out.print("0;");
                if (fieldsArray[u][i].isSouthBarrier())
                    System.out.print("1;");
                else
                    System.out.print("0;");
                if (fieldsArray[u][i].isHasCleaner())
                    System.out.print("1");
                else
                    System.out.print("0");
                System.out.print("(" + i + ";" + u + ")");
                System.out.print("}");
            }
            System.out.println("");
        }

        System.out.println("---------------------------------------------------------");
    }

    public void addObstacle(Obstacle obstacle)
    {
        int startX = obstacle.getStart().getX();
        int startY = obstacle.getStart().getY();

        int endX = obstacle.getEnd().getX();
        int endY = obstacle.getEnd().getY();

        if (!fieldExists(startX, startY))
            throw new IndexOutOfBoundsException("The start of the obstacle in not within the space");
        if (!fieldExists(endX, endY))
            throw new IndexOutOfBoundsException("The end of the obstacle in not within the space");

        // TODO
        for (int i = 0; i < height; i++)
        {
            for (int u = 0; u < width; u++)
            {
                if (u == startX && startX == endX && startY <= i && i <= endY -1)
                {
                    fieldsArray[i][u].setWestBarrier(true);
                }
                else if (i == startY && startY == endY && startX <= u && u <= endX -1)
                {
                    fieldsArray[i][u].setSouthBarrier(true);
                }
            }
        }
    }
}
