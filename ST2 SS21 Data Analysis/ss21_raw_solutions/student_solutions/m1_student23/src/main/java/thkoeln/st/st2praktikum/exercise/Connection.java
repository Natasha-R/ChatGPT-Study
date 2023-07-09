package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private final UUID uuid;
    private final Coordinate sourceCoordinate;
    private final UUID destinationSpaceShipDeckId;
    private final Coordinate destinationCoordinate;


    public Connection(String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.sourceCoordinate = new Coordinate(sourceCoordinate);
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = new Coordinate(destinationCoordinate);
        this.uuid = UUID.randomUUID();
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }
}
