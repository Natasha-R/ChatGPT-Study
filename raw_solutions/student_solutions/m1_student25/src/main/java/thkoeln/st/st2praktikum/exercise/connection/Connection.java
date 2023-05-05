package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.coordinate.CoordinateInterface;
import thkoeln.st.st2praktikum.exercise.services.StringService;

import java.util.UUID;

public class Connection {

    private final UUID sourceSpaceShipDeckId;
    private final CoordinateInterface sourceCoordinate;
    private final UUID destinationSpaceShipDeckId;
    private final CoordinateInterface destinationCoordinate;
    private final UUID connectionID = UUID.randomUUID();

    public Connection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.sourceCoordinate = StringService.translateToCoordinate(sourceCoordinate);
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = StringService.translateToCoordinate(destinationCoordinate);
    }

    public UUID getSourceSpaceShipDeckId() {
        return sourceSpaceShipDeckId;
    }

    public CoordinateInterface getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }

    public CoordinateInterface getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getConnectionID() {
        return connectionID;
    }
}
