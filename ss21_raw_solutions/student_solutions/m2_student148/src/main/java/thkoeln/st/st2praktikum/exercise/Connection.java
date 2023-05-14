package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID connectionId;
    private UUID sourceFieldId, destinationFieldId;
    private Coordinate sourceCoordinate;
    private Coordinate destinationCoordinate;

    public Connection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        this.connectionId = UUID.randomUUID();
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = destinationCoordinate;
    }

    public Connection() {
    }

    public UUID getId() {
        return this.connectionId;
    }

    public UUID getSourceFieldId() {
        return sourceFieldId;
    }

    public UUID getDestinationFieldId() {
        return destinationFieldId;
    }

    public Coordinate getSourceCoordinate() {
        return this.sourceCoordinate;
    }

    public Coordinate getDestinationCoordinate() {
        return this.destinationCoordinate;
    }
}