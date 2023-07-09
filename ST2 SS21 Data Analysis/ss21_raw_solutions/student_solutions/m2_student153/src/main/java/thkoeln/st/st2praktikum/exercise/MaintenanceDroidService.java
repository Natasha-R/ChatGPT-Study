package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MaintenanceDroidService {
    int countDeck=0;
    GameInstance gi=new GameInstance();

    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {

        UUID spaceShipDeckID=UUID.randomUUID();
        SpaceShipDeck spd=new SpaceShipDeck(spaceShipDeckID,height,width);
        gi.createDeck(spd);
        gi.createBorder(countDeck);
        countDeck++;
        return spaceShipDeckID;
    }
    /**
     * This method adds a wall to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        gi.addWall(spaceShipDeckId,wall);

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
        UUID portalID=UUID.randomUUID();
        Portal portal = new Portal(portalID,sourceSpaceShipDeckId,destinationSpaceShipDeckId,sourceVector2D,destinationVector2D);
        gi.createPortal(portal);
        return portalID;
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        UUID uuid = UUID.randomUUID();
        Droid droid = new Droid(name,uuid);
        gi.createDroid(droid);

        return uuid;
    }

    /**
     * This method lets the maintenance droid execute a order.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another spaceShipDeck
     * ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a wall or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        return gi.moveDroid(maintenanceDroidId,order);
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return  gi.getSpaceShipIDOfDroid(maintenanceDroidId);
    }

    /**
     * This method returns the vector2Ds a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector2Ds of the maintenance droid
     */
    public Vector2D getVector2D(UUID maintenanceDroidId){
           new Vector2D(gi.getCoordx(maintenanceDroidId),gi.getCoordy(maintenanceDroidId));
        return new Vector2D(gi.getCoordx(maintenanceDroidId),gi.getCoordy(maintenanceDroidId));
    }
}
