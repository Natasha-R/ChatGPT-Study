package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

class Connection
{
    private UUID sourceRoomId;
    private UUID destinationRoomId;

    private Position sourceCoordinate;
    private Position destinationCoordinate;

    public Connection(UUID sourceRoomId, Position sourceCoordinate, UUID destinationRoomId, Position destinationCoordinate)
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

    public Position getSourceCoordinate()
    {
        return sourceCoordinate;
    }

    public Position getDestinationCoordinate()
    {
        return destinationCoordinate;
    }

}
