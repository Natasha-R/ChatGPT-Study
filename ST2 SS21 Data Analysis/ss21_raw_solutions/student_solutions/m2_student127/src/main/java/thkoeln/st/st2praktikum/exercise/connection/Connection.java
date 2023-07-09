package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.Point;

import java.util.UUID;

public class Connection {
    private final UUID id;
    private final Point sourcePoint;
    private final Point destinationPoint;
    private final UUID sourceRoomId;
    private final UUID destinationRoomId;

    public UUID getId() { return id; }
    public Point getSourceCoordinate() { return sourcePoint; }
    public Point getDestinationCoordinate() { return destinationPoint; }
    public UUID getSourceRoomId() { return sourceRoomId; }
    public UUID getDestinationRoomId() { return destinationRoomId; }

    public Connection(UUID _sourceRoomId, Point _sourcePoint, UUID _destinationRoomId, Point _destinationPoint) {
        this.id = UUID.randomUUID();
        this.sourcePoint = _sourcePoint;
        this.destinationRoomId = _destinationRoomId;
        this.destinationPoint = _destinationPoint;
        this.sourceRoomId = _sourceRoomId;
    }
}
