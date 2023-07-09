package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private final UUID uuid;
    private final Point sourceCoordinate;
    private final UUID destinationSpaceShipDeckId;
    private final Point destinationCoordinate;


    public Connection(Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        this.sourceCoordinate = sourcePoint;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = destinationPoint;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return this.uuid;
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
