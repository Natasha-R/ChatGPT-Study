package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private UUID connectionId;
    private UUID spaceShipDeckId;
    private UUID destinationSpaceShipDeckId;
    private Vector2D sourceCoordinate;
    private Vector2D destinationCoordinate;


    public Connection(UUID spaceShipDeckId, Vector2D sourceCoordinate, UUID destinationSpaceShipDeckId, Vector2D destinationCoordinate, UUID connectionId) {

        this.connectionId = connectionId;
        this.spaceShipDeckId = spaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate= destinationCoordinate;

    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    public Vector2D getSourceCoordinate() {
        return sourceCoordinate;
    }

    public Vector2D getDestinationCoordinate() {
        return destinationCoordinate;
    }
}
