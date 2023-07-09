package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection extends Barrier
{
    UUID connectionId;
    UUID sourceId, destinationId;
    String sourceCoordinate, destinationCoordinate;

    public Connection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate)
    {
        this.connectionId = UUID.randomUUID();
        this.sourceId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }
}
