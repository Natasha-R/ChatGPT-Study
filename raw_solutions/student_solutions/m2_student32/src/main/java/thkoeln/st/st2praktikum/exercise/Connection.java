package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private final UUID connectionId;
    private final UUID sourceFieldId;
    private final Coordinate sourceCoordinate;
    private final UUID destinationFieldId;
    private final Coordinate destinationCoordinate;

    public Connection(UUID connectionId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        this.connectionId = connectionId;
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    public UUID getSourceFieldId() {
        return sourceFieldId;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationFieldId() {
        return destinationFieldId;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }


}
