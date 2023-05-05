package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Wall implements FieldBlockable
{
    @Id
    private UUID wallID;

    @OneToOne
    private Coordinate start;

    @OneToOne
    private Coordinate end;

    public Wall(String wallsDescription) throws RuntimeException
    {
        this.wallID = UUID.randomUUID();
        extractCoordinatesFromDescription(wallsDescription);
    }

    public Wall(Coordinate pos1, Coordinate pos2) throws RuntimeException
    {
        isNotDiagonal(pos1,pos2);
        if (pos1.getX() > pos2.getX() || pos1.getY() > pos2.getY())
        {
            this.start = pos2;
            this.end = pos1;
        }else
        {
            this.start = pos1;
            this.end = pos2;
        }
    }

    private void extractCoordinatesFromDescription(String wallDescription) throws RuntimeException
    {
        if(!wallDescription.matches("\\(\\d+,\\d+\\)-\\(\\d+,\\d+\\)"))
            throw new RuntimeException("Invalid Wall description");
        wallDescription = wallDescription.replaceAll("\\(\\)","");
        String[] splitWallDescription = wallDescription.split("-");
        this.start = new Coordinate(splitWallDescription[0]);
        this.end = new Coordinate(splitWallDescription[1]);
    }

    private void isNotDiagonal(Coordinate pos1, Coordinate pos2) throws RuntimeException
    {
        if (pos1.getX()!=pos2.getX() && pos1.getY()!=pos2.getY())
            throw new RuntimeException("Wall is Diagonal");
    }



    public int getBeginPositionX()
    {
        return start.getCoordinateX();
    }

    public int getBeginPositionY()
    {
        return start.getCoordinateY();
    }

    public int getEndPositionX()
    {
        return end.getCoordinateX();
    }

    public int getEndPositionY()
    {
        return end.getCoordinateY();
    }


    @Override
    public Positionable getBeginOfBlocker()
    {
        return start;
    }

    @Override
    public Positionable getEndOfBlocker()
    {
        return end;
    }

    @Override
    public UUID identify() {
        return wallID;
    }
}
