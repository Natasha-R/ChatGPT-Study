package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection{
    private final UUID connectionId;
    private final UUID sourceRoomId;
    private final String sourceCoordinate;
    private final UUID destinationRoomId;
    private final String destinationCoordinate;

    public Connection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate) {
        this.connectionId = UUID.randomUUID();
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getConnectionId() {
        return this.connectionId;
    }

    public UUID getSourceRoomId() {
        return this.sourceRoomId;
    }

    public String getSourceCoordinate() {
        return this.sourceCoordinate;
    }

    public UUID getDestinationRoomId() {
        return this.destinationRoomId;
    }

    public String getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
}
