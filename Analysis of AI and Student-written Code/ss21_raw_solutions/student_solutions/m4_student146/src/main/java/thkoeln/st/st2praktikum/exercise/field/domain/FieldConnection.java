package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Embeddable
@Setter
@NoArgsConstructor
public class FieldConnection {
    private UUID sourceFieldId;
    private Coordinate sourceCoordinate;
    private UUID destinationFieldId;
    private Coordinate destinationCoordinate;

    public FieldConnection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        this.sourceFieldId = sourceFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.destinationFieldId = destinationFieldId;
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
