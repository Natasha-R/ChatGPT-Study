package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements ConnectionInterface{
    private UUID connectionId;
    private UUID sourceRoomId;
    private UUID destinationRoomId;
    private String sourceCoordinate;
    private String destinationCoordinate;

    public Connection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate){
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
    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    @Override
    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }
}
