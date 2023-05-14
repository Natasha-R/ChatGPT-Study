package thkoeln.st.st2praktikum.exercise.room.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.UUID;


@Embeddable
@NoArgsConstructor
@Getter
@Access(AccessType.FIELD)
public class Wall implements FieldBlockable {

    private Coordinate start;
    private Coordinate end;


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


    private void isNotDiagonal(Coordinate pos1, Coordinate pos2) throws RuntimeException
    {
        if (pos1.getX()!=pos2.getX() && pos1.getY()!=pos2.getY())
            throw new RuntimeException("Wall is Diagonal");
    }


    public Wall(String wallsDescription) throws RuntimeException
    {
        extractCoordinatesFromDescription(wallsDescription);
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

    /**
     * @param wallString the wall in form of a string e.g. (1,2)-(1,4)
     */
    public static Wall fromString(String wallString) {
            return new Wall(wallString);
    }

    @Override
    public UUID identify()
    {
        return this.identify();
    }

    @Override
    public Coordinate getBeginOfBlocker()
    {
        return start;
    }

    @Override
    public Coordinate getEndOfBlocker()
    {
        return end;
    }

    public int getBeginPositionY()
    {
        return start.getCoordinateY();
    }

    public int getBeginPositionX()
    {
        return start.getCoordinateX();
    }

    public int getEndPositionY()
    {
        return end.getCoordinateY();
    }

    public int getEndPositionX()
    {
        return end.getCoordinateX();
    }

}
