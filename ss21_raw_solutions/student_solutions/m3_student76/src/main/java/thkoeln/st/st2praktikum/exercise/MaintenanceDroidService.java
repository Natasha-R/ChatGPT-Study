package thkoeln.st.st2praktikum.exercise;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MaintenanceDroidService {

    @Autowired
    private World world;

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        return world.createSpaceShipDeck(height,width);
    }

    /**
     * This method adds a barrier to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
         world.createBarrier(spaceShipDeckId,barrier);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return world.createTransportTechnology(technology);
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        return world.createConnection(sourceSpaceShipDeckId,sourcePoint,destinationSpaceShipDeckId,destinationPoint);
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        return world.createMaintenanceDroid(name);
    }

    /**
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Command command) {
        return world.canCommandBeExecuted(maintenanceDroidId,command);
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        return world.retrieveOccupiedSpaceShipDeckId(maintenanceDroidId);
    }

    /**
     * This method returns the point a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the point of the maintenance droid
     */
    public Point getMaintenanceDroidPoint(UUID maintenanceDroidId){
        return world.retrievePoint(maintenanceDroidId);
    }
}
