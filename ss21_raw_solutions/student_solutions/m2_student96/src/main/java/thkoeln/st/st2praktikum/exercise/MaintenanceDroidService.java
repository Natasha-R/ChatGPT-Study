package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.control.Controller;

import java.util.UUID;

public class MaintenanceDroidService {
    public Controller controller = new Controller();

    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        return controller.addSpaceShipDeck(height, width);
    }

    /**
     * This method adds a barrier to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        controller.addBarrier(spaceShipDeckId, barrier);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourcePoint the points of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        return controller.addConnection(sourceSpaceShipDeckId, sourcePoint, destinationSpaceShipDeckId, destinationPoint);
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        return controller.addMaintenanceDroid(name);
    }

    /**
     * This method lets the maintenance droid execute a order.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another spaceShipDeck
     * ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        return controller.executeCommand(maintenanceDroidId, order);
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return controller.getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId);
    }

    /**
     * This method returns the points a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the points of the maintenance droid
     */
    public Point getPoint(UUID maintenanceDroidId){
        return controller.getPoint(maintenanceDroidId);
    }
}
