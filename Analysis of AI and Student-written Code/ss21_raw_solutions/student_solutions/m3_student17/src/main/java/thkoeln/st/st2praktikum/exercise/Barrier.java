package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exception.InvalidBarrierException;
import thkoeln.st.st2praktikum.exercise.utility.WallOrientation;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Barrier
{
    @Id
    private UUID uuid;

    @Embedded
    private Vector2D start;

    @Embedded
    private Vector2D end;
    private WallOrientation orientation;

    public Barrier(Vector2D pos1, Vector2D pos2)
    {
        set(pos1, pos2);
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString)
    {
        final int splitttoken = barrierString.indexOf('-');
        final String aText = barrierString.substring(0,splitttoken);
        final String bText = barrierString.substring(splitttoken+1);

        uuid = UUID.randomUUID();

        set(new Vector2D(aText), new Vector2D(bText));
    }

    public void set(Vector2D a, Vector2D b)
    {
        boolean isXSameHeight = a.getX().equals(b.getX());
        boolean isYSameHeight = a.getY().equals(b.getY());
        boolean isDiagonal = !isXSameHeight && !isYSameHeight;

        if(isDiagonal)
        {
            throw new InvalidBarrierException("Barrier can't be diagonal.");
        }

        setOrientation(isXSameHeight ? WallOrientation.VERTICAL : WallOrientation.HORIZONTAL);

        boolean isABigger = false;

        switch (getOrientation())
        {
            case HORIZONTAL:
                isABigger = a.getX() > b.getX();
                break;

            case VERTICAL:
                isABigger = a.getY() > b.getY();
                break;
        }

        if(isABigger)
        {
            setStart(b);
            setEnd(a);
        }
        else
        {
            setStart(a);
            setEnd(b);
        }
    }

    public int getWallOrientationValue()
    {
        switch (getOrientation())
        {
            case HORIZONTAL:
                return getStart().getY();

            default:
            case VERTICAL:
                return getStart().getX();
        }
    }

    public boolean collide(final Vector2D position, final WallOrientation axis)
    {
        final int x = position.getX();
        final int y = position.getY();

        final Vector2D startPos = getStart();
        final Vector2D endPos = getEnd();

        switch (axis)
        {
            case VERTICAL:
            {
                final int xMax = Math.max(startPos.getX(), endPos.getX());
                final int xMin = Math.min(startPos.getX(), endPos.getX());

                return  x < xMax && x >= xMin;
            }

            default:
            case HORIZONTAL:
            {
                final int yMax = Math.max(startPos.getY(), endPos.getY());
                final int yMin = Math.min(startPos.getY(), endPos.getY());

                return y < yMax && y >= yMin;
            }
        }
    }

    @Override
    public String toString()
    {
        return getStart().toString() + "-" + getEnd().toString();
    }

    private void setStart(final Vector2D startPosition)
    {
        start = startPosition;
    }

    public Vector2D getStart()
    {
        return start;
    }

    private void setEnd(final Vector2D endPosition)
    {
        end = endPosition;
    }

    public Vector2D getEnd()
    {
        return end;
    }

    public WallOrientation getOrientation()
    {
        return orientation;
    }

    private void setOrientation(WallOrientation wallOrientation)
    {
        orientation = wallOrientation;
    }
}