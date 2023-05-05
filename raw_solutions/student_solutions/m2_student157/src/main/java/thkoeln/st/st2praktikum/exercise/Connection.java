package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Connection {

    private final UUID sourceSpaceShipDeckId;
    private final Vector2D sourceCoordinate;
    private final UUID destinationSpaceShipDeckId;
    private final Vector2D destinationCoordinate;
    private final UUID uuid;

    public Connection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = new Vector2D(sourceCoordinate);
        this.destinationCoordinate = new Vector2D(destinationCoordinate);
        this.uuid = UUID.randomUUID();
    }

    public Connection(UUID sourceSpaceShipDeckId, Vector2D sourceCoordinate, UUID destinationSpaceShipDeckId, Vector2D destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationCoordinate = destinationCoordinate;
        this.uuid = UUID.randomUUID();
    }
}
