package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MaintenanceDroidService {
    Universe universe;
    public MaintenanceDroidService(Universe universe1){
        universe = universe1;
    };

    /**
     * This method creates a new spaceship deck.
     *
     * @param height the height of the spaceship deck
     * @param width  the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        return universe.addSpaceShipDeck(height, width);
    }

    /**
     * This method adds a obstacle to a given spaceship deck.
     *
     * @param spaceShipDeckId the ID of the spaceship deck the obstacle shall be placed on
     * @param obstacle        the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {
        universe.addObstacle(spaceShipDeckId, obstacle);
    }

    /**
     * This method adds a transport category
     *
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        return universe.addTransportCategory(category);
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport category. Connections only work in one direction.
     *
     * @param transportCategoryId        the transport category which is used by the connection
     * @param sourceSpaceShipDeckId      the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceCoordinate           the coordinate of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationCoordinate      the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate) {
        return universe.addConnection(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate, transportCategoryId);
    }

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        return universe.addMaintenanceDroid(name);
    }

    /**
     * This method lets the maintenance droid execute a order.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order              the given order
     *                           NORTH, WEST, SOUTH, EAST for movement
     *                           TRANSPORT for transport - only works on squares with a connection to another spaceship deck
     *                           ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a obstacle or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        return universe.executeCommand(maintenanceDroidId, order);
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        return universe.getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId);
    }

    /**
     * This method returns the coordinate a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinate of the maintenance droid
     */
    public Coordinate getMaintenanceDroidCoordinate(UUID maintenanceDroidId) {
        return universe.getCoordinates(maintenanceDroidId);
    }
}
