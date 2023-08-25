package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class Connection {

    @Id
    private final UUID id;

    @Embedded
    private Vector2D sourceCoordinate;

    @Embedded
    private Vector2D destinationCoordinate;

    private final UUID sourceSpaceShipDeckId;
    private final UUID destinationSpaceShipDeckId;

    public Connection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = new Vector2D(sourceCoordinate);
        this.destinationCoordinate = new Vector2D(destinationCoordinate);
        this.id = UUID.randomUUID();
    }

    public Connection(UUID sourceSpaceShipDeckId, Vector2D sourceCoordinate, UUID destinationSpaceShipDeckId, Vector2D destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
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
