package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MaintenanceDroidService {

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method adds a wall to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceShipDeckId, Vector2D sourceVector2D, UUID destinationSpaceShipDeckId, Vector2D destinationVector2D) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method lets the maintenance droid execute a order.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a wall or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the vector-2D a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector-2D of the maintenance droid
     */
    public Vector2D getMaintenanceDroidVector2D(UUID maintenanceDroidId){
        throw new UnsupportedOperationException();
    }
}
