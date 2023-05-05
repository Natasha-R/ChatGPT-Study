package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Setter;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.exceptions.BarrierCoordinatesUnorderedException;
import thkoeln.st.st2praktikum.exercise.exceptions.DiagonalBarrierException;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidCoordinateFormatException;
import thkoeln.st.st2praktikum.exercise.interfaces.ICoordinateExtraction;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import javax.persistence.Embeddable;

@Embeddable
@Setter
@Getter
public class Barrier extends Field implements ICoordinateExtraction
{
    private Coordinate start;
    private Coordinate end;
    private String direction;
    private boolean hasNorthernBarrier, hasSouthernBarrier, hasWesternBarrier, hasEasternBarrier;

    public Barrier() { }

    public Barrier(String direction, boolean active)
    {
        setDirection(direction);
    }

    public Barrier(Coordinate pos1, Coordinate pos2)
    {
        this.start = pos1;
        this.end = pos2;

        //Check if the two coordinates are in the right order and swap them if not
        if (start.getX() > end.getX() || start.getY() > end.getY())
        {
            Coordinate tmp = start;
            start = end;
            end = tmp;
        }

        //Check if barrier coordinates are arranged diagonally
        if (start.getX() != end.getX() && start.getY() != end.getY())
        {
            throw new DiagonalBarrierException("You cannot create Diagonal Barriers!", new RuntimeException());
        }
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString)
    {
        String[] barrierCoordinates = new String[4];
        Integer x1, x2, y1, y2;

        //Check if the Barrier-String provides two valid coordinates
        try
        {
            if (barrierString.split("-").length > 1)
            {
                barrierCoordinates = extractCoordinates(barrierString);
            }
        }
        catch(NullPointerException ex)
        {
            throw new InvalidCoordinateFormatException("Invalid Barrier coordinates, you need to match the pattern (x1,y1)-(x2,y2) in order to create a Barrier!", new RuntimeException());
        }

        //Check if the extracted values from the Barrier-String are valid numbers/ints
        try
        {
            x1 = Integer.parseInt(barrierCoordinates[0]);
            y1 = Integer.parseInt(barrierCoordinates[1]);

            x2 = Integer.parseInt(barrierCoordinates[2]);
            y2 = Integer.parseInt(barrierCoordinates[3]);
        }
        catch (NumberFormatException ex)
        {
            throw new InvalidCoordinateFormatException("Invalid Barrier coordinates, you need to match the pattern (x1,y1)-(x2,y2) in order to create a Barrier!", new RuntimeException());
        }

        //Check if Coordinates are ordered correctly
        if (x1 > x2 || y1 > y2)
        {
            throw new BarrierCoordinatesUnorderedException("Barrier coordinates are in the wrong order, the end has to be larger than the start!", new RuntimeException());
        }

        this.start = new Coordinate(x1, y1);
        this.end = new Coordinate(x2, y2);
    }

    public Coordinate getStart()
    {
        return start;
    }

    public Coordinate getEnd()
    {
        return end;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;

        switch (direction)
        {
            case "no":
            {
                this.hasNorthernBarrier = true;
                break;
            }
            case "so":
            {
                this.hasSouthernBarrier = true;
                break;
            }
            case "we":
            {
                this.hasWesternBarrier = true;
                break;
            }
            case "ea":
            {
                this.hasEasternBarrier = true;
                break;
            }
        }
    }

    public void setHasNorthernBarrier(boolean hasNorthernBarrier)
    {
        this.hasNorthernBarrier = hasNorthernBarrier;
    }

    public void setHasSouthernBarrier(boolean hasSouthernBarrier)
    {
        this.hasSouthernBarrier = hasSouthernBarrier;
    }

    public void setHasWesternBarrier(boolean hasWesternBarrier)
    {
        this.hasWesternBarrier = hasWesternBarrier;
    }

    public void setHasEasternBarrier(boolean hasEasternBarrier)
    {
        this.hasEasternBarrier = hasEasternBarrier;
    }

    public String getDirection()
    {
        return direction;
    }

    public boolean getHasNorthernBarrier()
    {
        return hasNorthernBarrier;
    }

    public boolean getHasSouthernBarrier()
    {
        return hasSouthernBarrier;
    }

    public boolean getHasWesternBarrier()
    {
        return hasWesternBarrier;
    }

    public boolean getHasEasternBarrier()
    {
        return hasEasternBarrier;
    }

    public static Barrier fromString(String barrierString)
    {
        return new Barrier(barrierString);
    }

    @Override
    public String[] extractCoordinates(String coordinateString)
    {
        //Extract start and endpoint
        String[] result = new String[4];
        String start = coordinateString.split("-")[0];
        String end = coordinateString.split("-")[1];

        //Check if start and endpoint match the string pattern (x1,y1)-(x2,y2)
        if (!(start.startsWith("(") && start.endsWith(")") && end.startsWith("(") && end.endsWith(")")))
        {
            throw new InvalidCoordinateFormatException("Invalid Coordinate format to build a barrier!", new RuntimeException());
        }

        //Extract Startpoint Coordinates
        result[0] = start.split(",")[0].replace("(", "");
        result[1] = start.split(",")[1].replace(")", "");

        //Extract Endpoint Coordinates
        result[2] = end.split(",")[0].replace("(", "");
        result[3] = end.split(",")[1].replace(")", "");

        return result;
    }
}
