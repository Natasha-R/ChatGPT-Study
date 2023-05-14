package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class MaintenanceDroidService {
    private Map<UUID, SpaceShipDeck> spaceShipDecks;
    private Map<UUID, Obstacle> obstacles;
    private Map<UUID, Connection> connections;
    private Map<UUID, MaintenanceDroid> maintenanceDroids;

    public MaintenanceDroidService() {
        this.spaceShipDecks = new HashMap<>();
        this.obstacles = new HashMap<>();
        this.connections = new HashMap<>();
        this.maintenanceDroids = new HashMap<>();
    }

    public UUID addSpaceShipDeck(Integer height, Integer width) {
        UUID spaceShipDeckId = UUID.randomUUID();
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(spaceShipDeckId, height, width);
        spaceShipDecks.put(spaceShipDeckId, spaceShipDeck);
        return spaceShipDeckId;
    }

    public void addObstacle(UUID spaceShipDeckId, String obstacleString) {
        if (spaceShipDecks.containsKey(spaceShipDeckId)) {
            SpaceShipDeck spaceShipDeck = spaceShipDecks.get(spaceShipDeckId);
            Obstacle obstacle = Obstacle.parseObstacle(obstacleString);
            if (obstacle != null) {
                obstacles.put(obstacle.getId(), obstacle);
                spaceShipDeck.addObstacle(obstacle);
            }
        }
    }

    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        if (spaceShipDecks.containsKey(sourceSpaceShipDeckId) && spaceShipDecks.containsKey(destinationSpaceShipDeckId)) {
            SpaceShipDeck sourceSpaceShipDeck = spaceShipDecks.get(sourceSpaceShipDeckId);
            SpaceShipDeck destinationSpaceShipDeck = spaceShipDecks.get(destinationSpaceShipDeckId);
            Connection connection = Connection.parseConnection(sourceSpaceShipDeck, sourceCoordinate, destinationSpaceShipDeck, destinationCoordinate);
            if (connection != null) {
                connections.put(connection.getId(), connection);
                return connection.getId();
            }
        }
        return null;
    }

    public UUID addMaintenanceDroid(String name) {
        UUID maintenanceDroidId = UUID.randomUUID();
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(maintenanceDroidId, name);
        maintenanceDroids.put(maintenanceDroidId, maintenanceDroid);
        return maintenanceDroidId;
    }


    public boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        if (maintenanceDroids.containsKey(maintenanceDroidId)) {
            MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);
            return maintenanceDroid.executeCommand(commandString, spaceShipDecks, obstacles, connections, maintenanceDroids, spaceshipDeckId);
        }
        return false;
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        // Check if the maintenance droid exists
        if (!maintenanceDroids.containsKey(maintenanceDroidId)) {
            throw new IllegalArgumentException("MaintenanceDroid with ID " + maintenanceDroidId + " does not exist.");
        }

        // Get the maintenance droid
        MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);

        // Get the spaceShipDeckId of the space ship deck the maintenance droid is located on
        UUID spaceShipDeckId = null;
        for (Map.Entry<UUID, SpaceShipDeck> entry : spaceShipDecks.entrySet()) {
            SpaceShipDeck spaceShipDeck = entry.getValue();
            if (maintenanceDroid.getX() >= spaceShipDeck.getWidth() && maintenanceDroid.getX() <= spaceShipDeck.getWidth() + spaceShipDeck.getWidth() - 1 &&
                    maintenanceDroid.getY() >= spaceShipDeck.getHeight() && maintenanceDroid.getY() <= spaceShipDeck.getHeight() + spaceShipDeck.getHeight() - 1) {
                spaceShipDeckId = entry.getKey();
                break;
            }
        }

        return spaceShipDeckId;
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinates of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId){
        // Check if the maintenance droid exists
        if (!maintenanceDroids.containsKey(maintenanceDroidId)) {
            throw new IllegalArgumentException("MaintenanceDroid with ID " + maintenanceDroidId + " does not exist.");
        }

        // Get the maintenance droid
        MaintenanceDroid maintenanceDroid = maintenanceDroids.get(maintenanceDroidId);

        // Get the coordinates of the maintenance droid
        int x = maintenanceDroid.getX();
        int y = maintenanceDroid.getY();

        // Encode the coordinates as a String and return
        return "(" + x + "," + y + ")";
    }
}
