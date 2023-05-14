package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Connection {

    private final UUID sourceSpaceShipDeckId;
    private final Coordinates sourceCoordinate;
    private final UUID destinationSpaceShipDeckId;
    private final Coordinates destinationCoordinate;
    private final UUID uuid;

    public Connection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.sourceCoordinate = new Coordinates(sourceCoordinate);
        this.destinationCoordinate = new Coordinates(destinationCoordinate);
        this.uuid = UUID.randomUUID();
    }
}
