package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.UUID;

public class MaintenanceDroidService {

    private final SpaceShipDeckRepository spaceShipDeckRepository = new SpaceShipDeckRepository();
    private final MaintenanceDroidRepository maintenanceDroidRepository = new MaintenanceDroidRepository();

    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck();
        spaceShipDeck.setId(UUID.randomUUID());
        spaceShipDeck.setHeight(height);
        spaceShipDeck.setWidth(width);

        List<SpaceShipDeck> spaceShipDecks = spaceShipDeckRepository.getSpaceShipDecks();
        spaceShipDecks.add(spaceShipDeck);
        spaceShipDeckRepository.setSpaceShipDecks(spaceShipDecks);

        return spaceShipDeck.getId();
    }

    /**
     * This method adds a wall to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        List<Wall> walls = spaceShipDeckRepository.findSpaceShipDeckById(spaceShipDeckId.toString()).getWalls();
        walls.add(wall);
        spaceShipDeckRepository.findSpaceShipDeckById(spaceShipDeckId.toString()).setWalls(walls);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceVector2D the vector2Ds of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationVector2D the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, Vector2D sourceVector2D, UUID destinationSpaceShipDeckId, Vector2D destinationVector2D) {
        Connection connection = new Connection();
        connection.setId(UUID.randomUUID());
        connection.setSourceSpaceShipDeckId(sourceSpaceShipDeckId);
        connection.setSourceVector2D(sourceVector2D);
        connection.setDestinationSpaceShipDeckId(destinationSpaceShipDeckId);
        connection.setDestinationVector2D(destinationVector2D);

        SpaceShipDeck spaceShipDeck = spaceShipDeckRepository.findSpaceShipDeckById(sourceSpaceShipDeckId.toString());
        List<Connection> connections = spaceShipDeck.getConnections();
        connections.add(connection);
        spaceShipDeck.setConnections(connections);

        return connection.getId();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid();
        maintenanceDroid.setId(UUID.randomUUID());
        maintenanceDroid.setName(name);
        maintenanceDroid.setVector2D(new Vector2D(99, 99));

        List<MaintenanceDroid> maintenanceDroids = maintenanceDroidRepository.getMaintenanceDroids();
        maintenanceDroids.add(maintenanceDroid);
        maintenanceDroidRepository.setMaintenanceDroids(maintenanceDroids);

        return maintenanceDroid.getId();
    }

    /**
     * This method lets the maintenance droid execute a task.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another spaceShipDeck
     * ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a wall or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        Movement movement = new Movement();
        return movement.executeCommand(maintenanceDroidId, task, spaceShipDeckRepository, maintenanceDroidRepository, getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId));
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getSpaceShipDeckId();
    }

    /**
     * This method returns the vector2Ds a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector2Ds of the maintenance droid
     */
    public Vector2D getVector2D(UUID maintenanceDroidId){
        return maintenanceDroidRepository.findMaintenanceDroidById(maintenanceDroidId.toString()).getVector2D();
    }
}
