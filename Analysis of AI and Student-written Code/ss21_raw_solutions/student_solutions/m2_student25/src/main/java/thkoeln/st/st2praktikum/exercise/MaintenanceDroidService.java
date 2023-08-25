package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.universe.Universe;

import java.util.UUID;

public class MaintenanceDroidService {

    /**
     * This method creates a new spaceShipDeck.
     *
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */

    Universe universe = new Universe();

    public UUID addSpaceShipDeck(Integer height, Integer width) {
        return universe.addSpaceShipDeck(height, width);
    }

    /**
     * This method adds a obstacle to a given spaceShipDeck.
     *
     * @param spaceShipDeckId the ID of the spaceShipDeck the obstacle shall be placed on
     * @param obstacle        the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {
        universe.addObstacle(spaceShipDeckId, obstacle);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        return universe.addConnection(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        return universe.addMaintenanceDroid(name);
    }

    /**
     * This method lets the maintenance droid execute a order.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another spaceShipDeck
     * ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        return universe.executeCommand(maintenanceDroidId, order);
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        return universe.getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId);
    }
    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinates of the maintenance droid
     */
    public Coordinate getCoordinate(UUID maintenanceDroidId) {
        return universe.getCoordinates(maintenanceDroidId);
    }
}
