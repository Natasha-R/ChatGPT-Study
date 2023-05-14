package thkoeln.st.st2praktikum.exercise;

import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
public class FieldConnection {
    @Id
    private UUID id;
    private UUID sourceFieldId;
    private Coordinate sourceCoordinate;
    private UUID destinationFieldId;
    private Coordinate destinationCoordinate;

    public FieldConnection(UUID id, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        this.id = id;
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.destinationFieldId = destinationFieldId;
    }

    public UUID getId() {
        return id;
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

    public UUID getSourceFieldId() {
        return sourceFieldId;
    }
}
