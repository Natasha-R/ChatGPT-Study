package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection implements IConnection {
    private UUID id;
    private UUID sourceSpaceShipDeckId;
    private String sourceCoordinate;
    private UUID destinationSpaceShipDeckId;
    private String destinationCoordinate;

    public Connection(UUID id, UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.id = id;
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSourceSpaceShipDeckId() {
        return sourceSpaceShipDeckId;
    }

    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }

    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }
}
