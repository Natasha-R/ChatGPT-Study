package thkoeln.st.st2praktikum.exercise.domainprimitives;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.UUID;

@Embeddable
public class Connection {
    private UUID sourceFieldID;
    private Coordinate sourceCoordinate;
    private UUID destinationFieldId;
    private Coordinate destCoordinate;

    public Connection() {
    }

    public Connection(UUID sourceFieldID, Coordinate sourceCoordinate, UUID destFieldID, Coordinate destCoordinate) {
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

    public UUID getDestinationFieldId() {
        return destinationFieldId;
    }

    public UUID getSourceFieldID() {
        return sourceFieldID;
    }
}
