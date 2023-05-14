package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Field;

import javax.persistence.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Space {

    @Id
    private UUID uuid;

    @ElementCollection
    private List<Field> fieldsList;

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

    public Field[][] improvedList()
    {
        Field[][] fieldsArray = new Field[this.getHeight()][this.getWidth()];
        int index = 0;
        for (int i = 0; i < this.getHeight(); i++){
            for (int j = 0; j < this.getWidth(); j++){
                fieldsArray[i][j] = fieldsList.get(index++);
            }
        }
        return fieldsArray;
    }

    public Space(int _height, int _width/*, FieldRepository fieldRepository*/)
    {
        if(_height < 1 || _width < 1)
        {
            throw new IndexOutOfBoundsException("height and width must be at least 1");
        }
        uuid = UUID.randomUUID();
        fieldsList = new ArrayList<Field>();
        height = _height;
        width = _width;
        for(int i = 0; i < _height; i++)
        {
            for(int u = 0; u < _width; u++)
            {
                fieldsList.add(new Field());
            }
        }

    }

    public Field getField(int x, int y) {
        if(fieldExists(x, y))
        {
            return improvedList()[y][x];
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
                if (improvedList()[u][i].isWestBarrier())
                    System.out.print("1;");
                else
                    System.out.print("0;");
                if (improvedList()[u][i].isSouthBarrier())
                    System.out.print("1;");
                else
                    System.out.print("0;");
                if (improvedList()[u][i].isHasCleaner())
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

    public void addObstacle(Obstacle obstacle/*, FieldRepository fieldRepository*/)
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
                    improvedList()[i][u].setWestBarrier(true);
                }
                else if (i == startY && startY == endY && startX <= u && u <= endX -1)
                {
                    improvedList()[i][u].setSouthBarrier(true);
                }
            }
        }
    }
}
