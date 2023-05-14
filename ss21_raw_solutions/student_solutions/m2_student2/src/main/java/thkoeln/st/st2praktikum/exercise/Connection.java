package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements ConnectionInterface {
    private UUID connectionId;
    private UUID sourceRoomId;
    private UUID destinationRoomId;
    private Vector2D sourceCoordinate;
    private Vector2D destinationCoordinate;

    public Connection(UUID sourceRoomId, Vector2D sourceCoordinate, UUID destinationRoomId, Vector2D destinationCoordinate){
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
        this.connectionId = UUID.randomUUID();
    }

    @Override
    public UUID getConnectionId() {
        return connectionId;
    }

    @Override
    public UUID getSourceRoomId() {
        return sourceRoomId;
    }

    @Override
    public UUID getDestinationRoomId() {
        return destinationRoomId;
    }

    @Override
    public Vector2D getSourceCoordinate() {
        return sourceCoordinate;
    }

    @Override
    public Vector2D getDestinationCoordinate() {
        return destinationCoordinate;
    }
}
