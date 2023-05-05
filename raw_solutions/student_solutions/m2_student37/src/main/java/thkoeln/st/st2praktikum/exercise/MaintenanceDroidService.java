package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MaintenanceDroidService {
    World world = new World();

    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height,width);
        world.addSpace(spaceShipDeck);
        return spaceShipDeck.getId();
    }

    /**
     * This method adds a obstacle to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {
        world.addObstacletoSpace(spaceShipDeckId,obstacle);
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
        Connector connector = new Connector(sourceVector2D,destinationSpaceShipDeckId,destinationVector2D);
        Connector reverse = new Connector(destinationVector2D,sourceSpaceShipDeckId,sourceVector2D);
        world.addConnectors(sourceSpaceShipDeckId,connector);
        world.addConnectors(destinationSpaceShipDeckId,reverse);
        return connector.getId();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        Droid droid = new Droid(name);
        world.addDroids(droid);
        return droid.getDroidId();
    }

    /**
     * This method lets the maintenance droid execute a task.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another spaceShipDeck
     * ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        return world.sendcommand(maintenanceDroidId,task);
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return world.getMaintenanceDroidId(maintenanceDroidId);
    }

    /**
     * This method returns the vector2Ds a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector2Ds of the maintenance droid
     */
    public Vector2D getVector2D(UUID maintenanceDroidId){
        Droid droid= world.getMaintenanceDroid(maintenanceDroidId);
        return droid.getPosition();
    }
}
