package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Connection {

    @Id
    public final UUID id;

    @Embedded
    private Point sourceCoordinate;

    @Embedded
    private Point destinationCoordinate;

    private final UUID sourceFieldId;
    private final UUID destinationFieldId;

    private UUID transportTechnology;

    public Connection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldId = destinationFieldId;
        this.sourceCoordinate = Point.fromString(sourceCoordinate);
        this.destinationCoordinate = Point.fromString(destinationCoordinate);
        this.id = UUID.randomUUID();
    }

    public Connection(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate, UUID transportTechnology) {
        this.sourceFieldId = sourceFieldId;
        this.destinationFieldId = destinationFieldId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.transportTechnology = transportTechnology;
        this.id = UUID.randomUUID();
    }

    public Connection() {
        sourceFieldId = null;
        sourceCoordinate = null;
        destinationFieldId = null;
        destinationCoordinate = null;
        id = UUID.randomUUID();
    }
}
