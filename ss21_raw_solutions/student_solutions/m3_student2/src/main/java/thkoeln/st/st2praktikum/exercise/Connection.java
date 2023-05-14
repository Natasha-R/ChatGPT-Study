package thkoeln.st.st2praktikum.exercise;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Connection extends AbstractEntity implements ConnectionInterface{
    private UUID transportSystemId;
    private UUID sourceRoomId;
    private UUID destinationRoomId;

    @Embedded
    private Vector2D sourceCoordinate;

    @Embedded
    private Vector2D destinationCoordinate;

    public Connection(UUID transportSystemId, UUID sourceRoomId, Vector2D sourceCoordinate, UUID destinationRoomId, Vector2D destinationCoordinate){
        this.transportSystemId = transportSystemId;
        this.sourceRoomId = sourceRoomId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationRoomId = destinationRoomId;
        this.destinationCoordinate = destinationCoordinate;
    }

    @Override
    public UUID getTransportSystemId(){
        return transportSystemId;
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
