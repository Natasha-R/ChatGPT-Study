package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface MaintenanceDroidService {
    UUID addSpaceShipDeck(Integer height, Integer width);

    void addObstacle(UUID spaceShipDeckId, String obstacleString);

    UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate);

    UUID addMaintenanceDroid(String name);

    Boolean executeCommand(UUID maintenanceDroidId, String commandString);

    UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId);

    String getCoordinates(UUID maintenanceDroidId);
}
