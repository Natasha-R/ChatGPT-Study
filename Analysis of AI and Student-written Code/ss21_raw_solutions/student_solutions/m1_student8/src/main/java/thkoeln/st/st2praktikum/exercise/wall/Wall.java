package thkoeln.st.st2praktikum.exercise.wall;

import thkoeln.st.st2praktikum.exercise.position.Position;
import thkoeln.st.st2praktikum.exercise.position.Positionable;

import java.util.UUID;

public class Wall implements FieldBlockable
{
    private UUID wallID;

    private Position beginPosition;

    private Position endPosition;

    public Wall(String wallsDescription)
    {
        this.wallID = UUID.randomUUID();
        extractCoordinatesFromDescription(wallsDescription);
    }


    private void extractCoordinatesFromDescription(String wallDescription)
    {
        wallDescription.replaceAll("\\(","").replaceAll("\\)","");
        String splitWallDescription[] = wallDescription.split("-");
        this.beginPosition = new Position(splitWallDescription[0]);
        this.endPosition = new Position(splitWallDescription[1]);
     }

     public int getBeginPositionX()
     {
         return beginPosition.getCoordinateX();
     }

    public int getBeginPositionY()
    {
        return beginPosition.getCoordinateY();
    }

    public int getEndPositionX()
    {
        return endPosition.getCoordinateX();
    }

    public int getEndPositionY()
    {
        return endPosition.getCoordinateY();
    }


    @Override
    public Positionable getBeginOfBlocker()
    {
        return beginPosition;
    }

    @Override
    public Positionable getEndOfBlocker()
    {
        return endPosition;
    }

    @Override
    public UUID identify() {
        return wallID;
    }
}
