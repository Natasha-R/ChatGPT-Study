package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private final UUID connectionID;
    private final UUID sourceFieldId;
    private final UUID destinationFieldID;
    private final Coordinate sourceCoordinate;
    private final Coordinate destinationCoordinate;

    public Connection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate){
        this.connectionID = UUID.randomUUID();
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldID = destinationFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getConnectionID() {
        return connectionID;
    }

    public UUID getSourceFieldId() { return sourceFieldId;}

    public UUID getDestinationFieldID() {
        return destinationFieldID;
    }

    public Coordinate getSourceCoordinate(){ return sourceCoordinate;}

    public Coordinate getDestinationCoordinate() { return destinationCoordinate;}
}
