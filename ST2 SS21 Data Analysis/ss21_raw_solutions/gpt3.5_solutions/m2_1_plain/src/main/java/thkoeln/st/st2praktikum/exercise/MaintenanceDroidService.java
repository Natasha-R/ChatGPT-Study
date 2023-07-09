package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class MaintenanceDroidService {
    private Map<UUID, SpaceShipDeck> spaceShipDecks;
    private Map<UUID, Obstacle> obstacles;
    private Map<UUID, Connection> connections;
    private Map<UUID, MaintenanceDroid> maintenanceDroids;

    public MaintenanceDroidService() {
        spaceShipDecks = new HashMap<>();
        obstacles = new HashMap<>();
        connections = new HashMap<>();
        maintenanceDroids = new HashMap<>();
    }

    public UUID addSpaceShipDeck(Integer height, Integer width) {
        UUID spaceShipDeckId = UUID.randomUUID();
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(spaceShipDeckId, height, width);
        spaceShipDecks.put(spaceShipDeckId, spaceShipDeck);
        return spaceShipDeckId;
    }

    public void addObstacle(UUID spaceShipDeckId, String obstacleString) {
        Obstacle obstacle = Obstacle.parseObstacleString(obstacleString);
        obstacles.put(obstacle.getId(), obstacle);
        SpaceShipDeck spaceShipDeck = spaceShipDecks.get(spaceShipDeckId);
        if (spaceShipDeck != null) {
            // Extract obstacle coordinates
            int startX = obstacle.getStartX();
            int startY = obstacle.getStartY();
            int endX = obstacle.getEndX();
            int endY = obstacle.getEndY();
            // Add obstacle to SpaceShipDeck
            spaceShipDeck.addObstacle(startX, startY, endX, endY);
        } else {
            throw new IllegalArgumentException("SpaceShipDeck not found with ID: " + spaceShipDeckId);
        }
    }


    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        UUID connectionId = UUID.randomUUID();
        Connection connection = new Connection(connectionId, sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
        connections.put(connectionId, connection);
        return connectionId;
    }

    public UUID addMaintenanceDroid(String name) {
        UUID maintenanceDroidId = UUID.randomUUID();
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(maintenanceDroidId, name);
        maintenanceDroids.put(maintenanceDroidId, maintenanceDroid);
        return maintenanceDroidId;
    }

    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);
        if (maintenanceDroid != null) {
            return maintenanceDroid.executeCommand(commandString, spaceShipDecks, obstacles, connections);
        } else {
            throw new IllegalArgumentException("MaintenanceDroid not found with ID: " + maintenanceDroidId);
        }
    }

    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);
        if (maintenanceDroid != null) {
            return maintenanceDroid.getSpaceShipDeckId();
        } else {
            throw new IllegalArgumentException("MaintenanceDroid not found with ID: " + maintenanceDroidId);
        }
    }

    public String getCoordinates(UUID maintenanceDroidId) {
        MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);
        if (maintenanceDroid != null) {
            return maintenanceDroid.getCoordinates();
        } else {
            throw new IllegalArgumentException("MaintenanceDroid not found with ID: " + maintenanceDroidId);
        }
    }
}