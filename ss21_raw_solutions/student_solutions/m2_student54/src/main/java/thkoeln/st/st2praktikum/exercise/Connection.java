package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection
{
    private UUID sourceRoomId;
    private UUID destinationRoomId;
    private Vector2D sourceCoordinate;
    private Vector2D destinationCoordinate;

    public Connection(UUID sourceRoomId, Vector2D sourceCoordinate, UUID destinationRoomId, Vector2D destinationCoordinate)
    {
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getSourceRoomId()
    {
        return sourceRoomId;
    }

    public UUID getDestinationRoomId()
    {
        return destinationRoomId;
    }

    public Vector2D getSourceCoordinate()
    {
        return sourceCoordinate;
    }

    public Vector2D getDestinationCoordinate()
    {
        return destinationCoordinate;
    }

}
