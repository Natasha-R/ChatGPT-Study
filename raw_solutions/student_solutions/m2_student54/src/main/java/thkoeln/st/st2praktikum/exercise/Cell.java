package thkoeln.st.st2praktikum.exercise;
import java.util.UUID;


public class Cell
{
    private boolean isSouthWall;
    private boolean isWestWall;
    private boolean isOccupied;
    private boolean isConnection;
    private UUID connectionID;

    public Cell()
    {
        setIsSouthWall(false);
        setIsWestWall(false);
        setIsOccupied(false);
        setIsConnection(false);
    }

    public boolean getIsSouthWall()
    {
        return isSouthWall;
    }

    public void setIsSouthWall(boolean isSouthWall)
    {
        this.isSouthWall = isSouthWall;
    }

    public boolean getIsWestWall()
    {
        return isWestWall;
    }

    public void setIsWestWall(boolean isWestWall)
    {
        this.isWestWall = isWestWall;
    }

    public boolean getIsOccupied()
    {
        return isOccupied;
    }

    public void setIsOccupied(boolean isOccupied)
    {
        this.isOccupied = isOccupied;
    }

    public boolean getIsConnection()
    {
        return isConnection;
    }

    public void setIsConnection(boolean isConnection)
    {
        this.isConnection = isConnection;
    }

    public UUID getConnectionID()
    {
        return connectionID;
    }

    public void setConnectionID(UUID connectionID)
    {
        this.connectionID = connectionID;
    }
}
