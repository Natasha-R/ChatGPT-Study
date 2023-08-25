package thkoeln.st.st2praktikum.exercise.transportsystem.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class Connection {
    @Id
    private final UUID connectionID = UUID.randomUUID();
    private UUID sourceFieldId;
    private UUID destinationFieldID;
    @Embedded
    private Coordinate sourceCoordinate;
    @Embedded
    private Coordinate destinationCoordinate;
    private UUID transportSystemId;

    public Connection(UUID transportSystemId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate){
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldID = destinationFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.transportSystemId = transportSystemId;
    }

    public Connection() {
    }
}
