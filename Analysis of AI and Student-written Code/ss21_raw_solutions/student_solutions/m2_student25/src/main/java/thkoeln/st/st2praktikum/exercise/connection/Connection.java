package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.coordinate.MyCoordinateInterface;
import thkoeln.st.st2praktikum.exercise.services.StringService;

import java.util.UUID;

public class Connection {

    private final UUID sourceSpaceShipDeckId;
    private final MyCoordinateInterface sourceCoordinate;
    private final UUID destinationSpaceShipDeckId;
    private final MyCoordinateInterface destinationCoordinate;
    private final UUID connectionID = UUID.randomUUID();

    public Connection(UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.sourceCoordinate = StringService.translateCoordinateToMyCoordinate(sourceCoordinate);
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = StringService.translateCoordinateToMyCoordinate(destinationCoordinate);
    }

    public UUID getSourceSpaceShipDeckId() {
        return sourceSpaceShipDeckId;
    }

    public MyCoordinateInterface getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }

    public MyCoordinateInterface getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public UUID getConnectionID() {
        return connectionID;
    }
}
