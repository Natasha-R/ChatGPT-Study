package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private UUID ConnectionID;
    private UUID sourceFieldID;
    private Coordinate sourceCoordinate;
    private UUID destinationFieldId;
    private Coordinate destCoordinate;

    public Connection(UUID connectionID, UUID sourceFieldID, Coordinate sourceCoordinate, UUID destFieldID, Coordinate destCoordinate) {
        this.ConnectionID = connectionID;
        this.sourceFieldID = sourceFieldID;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldId = destFieldID;
        this.destCoordinate = destCoordinate;
    }

    public Coordinate getDestCoordinate() {
        return destCoordinate;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getConnectionID() {
        return ConnectionID;
    }

    public UUID getConnection_ID() {
        return ConnectionID;
    }

    public UUID getDestinationFieldId() {
        return destinationFieldId;
    }

    public UUID getSourceFieldID() {
        return sourceFieldID;
    }
}
