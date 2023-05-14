package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class MaintenanceDroidServiceImpl implements MaintenanceDroidService {
    private final Map<UUID, SpaceShipDeck> spaceShipDecks;
    private final Map<UUID, Obstacle> obstacles;
    private final Map<UUID, Connection> connections;
    private final Map<UUID, MaintenanceDroid> maintenanceDroids;

    private final SpaceShipDeck spaceShipDeckService;
    private final Obstacle obstacleService;
    private final Connection connectionService;

    public MaintenanceDroidServiceImpl(
            SpaceShipDeck spaceShipDeckService,
            Obstacle obstacleService,
            Connection connectionService) {
        this.spaceShipDecks = new HashMap<>();
        this.obstacles = new HashMap<>();
        this.connections = new HashMap<>();
        this.maintenanceDroids = new HashMap<>();

        this.spaceShipDeckService = spaceShipDeckService;
        this.obstacleService = obstacleService;
        this.connectionService = connectionService;
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
            int startX = obstacle.getStartX();
            int startY = obstacle.getStartY();
            int endX = obstacle.getEndX();
            int endY = obstacle.getEndY();
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