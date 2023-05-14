package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection{
    private final UUID connectionId;
    private final UUID sourceRoomId;
    private final Vector2D sourceCoordinate;
    private final UUID destinationRoomId;
    private final Vector2D destinationCoordinate;

    public Connection(UUID sourceRoomId, Vector2D sourceCoordinate, UUID destinationRoomId, Vector2D destinationCoordinate) {
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

    public Vector2D getSourceCoordinate() {
        return this.sourceCoordinate;
    }

    public UUID getDestinationRoomId() {
        return this.destinationRoomId;
    }

    public Vector2D getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
}
