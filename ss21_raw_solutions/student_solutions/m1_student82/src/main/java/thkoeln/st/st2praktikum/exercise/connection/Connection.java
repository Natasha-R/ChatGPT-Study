package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.UUidable;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;

import java.util.UUID;

public class Connection implements Jumpable, UUidable {

    private final UUID connectionId = UUID.randomUUID();

    private final UUID sourceFieldId;
    private final Coordinate sourceCoordinate;
    private final UUID destinationFieldId;
    private final Coordinate destinationCoordinate;

    public Connection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = destinationCoordinate;
    }


    @Override
    public boolean connectable(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId) {
        return false;
    }

    @Override
    public UUID getID() {
        return this.connectionId;
    }

    public UUID getConnectionId() {
        return connectionId;
    }

    public UUID getSourceFieldId() {
        return sourceFieldId;
    }

    public Coordinate getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public Coordinate getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationFieldId() {
        return destinationFieldId;
    }
}
