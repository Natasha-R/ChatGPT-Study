package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Connection{
    @Id
    private final UUID connectionId;

    private UUID sourceRoomId;
    private Vector2D sourceCoordinate;
    private UUID destinationRoomId;
    private Vector2D destinationCoordinate;
    private UUID transportTeechnologyId;

    public Connection(UUID transportTeechnologyId, UUID sourceRoomId, Vector2D sourceCoordinate, UUID destinationRoomId, Vector2D destinationCoordinate) {
        this.connectionId = UUID.randomUUID();
        this.transportTeechnologyId = transportTeechnologyId;
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }

    protected Connection() {
        this.connectionId = UUID.randomUUID();
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

    public UUID getTransportTeechnologyId() {
        return transportTeechnologyId;
    }
}