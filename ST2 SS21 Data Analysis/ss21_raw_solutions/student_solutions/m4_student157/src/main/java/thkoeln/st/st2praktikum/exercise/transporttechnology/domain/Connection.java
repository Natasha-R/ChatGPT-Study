package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

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
    private Vector2D sourceCoordinate;

    @Embedded
    private Vector2D destinationCoordinate;

    private final UUID sourceSpaceShipDeckId;
    private final UUID destinationSpaceShipDeckId;

    private UUID transportTechnology;

    public Connection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = Vector2D.fromString(sourceCoordinate);
        this.destinationCoordinate = Vector2D.fromString(destinationCoordinate);
        this.id = UUID.randomUUID();
    }

    public Connection(UUID sourceSpaceShipDeckId, Vector2D sourceCoordinate, UUID destinationSpaceShipDeckId, Vector2D destinationCoordinate, UUID transportTechnology) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.transportTechnology = transportTechnology;
        this.id = UUID.randomUUID();
    }

    public Connection() {
        sourceSpaceShipDeckId = null;
        sourceCoordinate = null;
        destinationSpaceShipDeckId = null;
        destinationCoordinate = null;
        id = UUID.randomUUID();
    }
}
