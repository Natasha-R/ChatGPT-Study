package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Connection {

    private UUID uuid;
    private UUID sourceSpaceShipDeckId;
    private Point sourceCoordinate;
    private UUID destinationSpaceShipDeckId;
    private Point destinationCoordinate;

    protected Connection() {}

    public Connection(UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.sourceCoordinate = sourcePoint;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = destinationPoint;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public UUID getSourceSpaceShipDeckId() {
        return sourceSpaceShipDeckId;
    }

    public Point getSourceCoordinate() {
        return sourceCoordinate;
    }

    public Point getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }
}
