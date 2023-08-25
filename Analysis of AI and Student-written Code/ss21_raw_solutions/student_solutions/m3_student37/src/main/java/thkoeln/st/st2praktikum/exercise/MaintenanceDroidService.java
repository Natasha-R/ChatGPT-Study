package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MaintenanceDroidService {
    World world = new World();

    @Autowired
    private DroidRepository droidRepository;

    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;

    @Autowired ConnectorRepository connectorRepository;

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height,width);
        world.addSpace(spaceShipDeck);
        spaceShipDeckRepository.save(spaceShipDeck);
        return spaceShipDeck.getId();
    }

    /**
     * This method adds a obstacle to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {
        world.addObstacletoSpace(spaceShipDeckId,obstacle);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        world.addTransportTechnology(transportTechnology);
        return transportTechnology.getTransportId();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceShipDeckId, Vector2D sourceVector2D, UUID destinationSpaceShipDeckId, Vector2D destinationVector2D) {
        Connector connector = new Connector(sourceVector2D,destinationSpaceShipDeckId,destinationVector2D);
        world.getTechnology(transportTechnologyId).addConnector(connector);
        world.addConnectors(sourceSpaceShipDeckId,connector);
        connectorRepository.save(connector);
        return connector.getId();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        world.addDroids(maintenanceDroid);
        droidRepository.save(maintenanceDroid);
        return maintenanceDroid.getDroidId();
    }

    /**
     * This method lets the maintenance droid execute a task.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        boolean result = world.sendcommand(maintenanceDroidId,task);
        droidRepository.save(world.getMaintenanceDroid(maintenanceDroidId));
        return result;
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return world.getMaintenanceDroidId(maintenanceDroidId);
    }

    /**
     * This method returns the vector-2D a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector-2D of the maintenance droid
     */
    public Vector2D getMaintenanceDroidVector2D(UUID maintenanceDroidId){
        return world.getMaintenanceDroid(maintenanceDroidId).getVector2D();
    }
}
