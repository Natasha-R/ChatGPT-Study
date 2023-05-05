package thkoeln.st.st2praktikum.exercise.world2.connection;

import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.world2.services.CoordinateService;
import thkoeln.st.st2praktikum.exercise.world2.services.UUidable;

import java.util.UUID;

public class Connection implements UUidable, Connected {

    private final UUID connectionId;

    private final UUID sourceFieldId;
    private final Coordinate sourceCoordinate;
    private final UUID destinationFieldId;
    private final Coordinate destinationCoordinate;

    public Connection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        this.connectionId = UUID.randomUUID();
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = CoordinateService.stringToCoordinate(sourceCoordinate);
        this.destinationFieldId = destinationFieldId;
        this.destinationCoordinate = CoordinateService.stringToCoordinate(destinationCoordinate);
    }

    @Override
    public Connection getConnection() {
        return this;
    }

    @Override
    public boolean connectable(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId) {
        return false;
    }

    @Override
    public UUID getId() {
        return this.connectionId;
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
