package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.Coordinate;

import java.util.UUID;

public class Connection {
    private final UUID uuid;
    private final Coordinate sourceCoordinate;
    private final Coordinate destinationCoordinate;
    private final UUID sourceRoomId;
    private final UUID destinationRoomId;

    public UUID getId() { return uuid; }
    public Coordinate getSourceCoordinate() { return sourceCoordinate; }
    public Coordinate getDestinationCoordinate() { return destinationCoordinate; }
    public UUID getSourceRoomId() { return sourceRoomId; }
    public UUID getDestinationRoomId() { return destinationRoomId; }

    public Connection(UUID _sourceRoomId, Coordinate _sourceCoordinate, UUID _destinationRoomId, Coordinate _destinationCoordinate) {
        this.uuid = UUID.randomUUID();
        this.sourceCoordinate = _sourceCoordinate;
        this.destinationRoomId = _destinationRoomId;
        this.destinationCoordinate = _destinationCoordinate;
        this.sourceRoomId = _sourceRoomId;
    }
}
