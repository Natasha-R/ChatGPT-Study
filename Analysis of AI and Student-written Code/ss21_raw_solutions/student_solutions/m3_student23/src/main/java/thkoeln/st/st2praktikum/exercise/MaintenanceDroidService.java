package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.MaintenanceDroidRepository;

import java.util.UUID;


@Service
public class MaintenanceDroidService {

    private final TransportSystemManager transportSystemManager = new TransportSystemManager();
    private final DeckManager deckManager = new DeckManager();
    private final DroidManager droidManager = new DroidManager(deckManager, transportSystemManager);

    private MaintenanceDroidRepository maintenanceDroidRepository;

    @Autowired
    public MaintenanceDroidService (MaintenanceDroidRepository maintenanceDroidRepository) {
        this.maintenanceDroidRepository = maintenanceDroidRepository;
    }

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        return deckManager.addDeck(height, width);
    }

    /**
     * This method adds a barrier to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        deckManager.addBarrierToDeckByUUID(spaceShipDeckId, barrier);
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        return this.transportSystemManager.addTransportSystem(system);
    }

    /**
     * This method adds a traversable connection between two spaceship decks based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceSpaceShipDeckId the ID of the spaceship deck where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceship deck where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceSpaceShipDeckId, Point sourcePoint, UUID destinationSpaceShipDeckId, Point destinationPoint) {
        return this.transportSystemManager.addConnectionToTransportSystemByUUID(transportSystemId, sourceSpaceShipDeckId, sourcePoint, destinationSpaceShipDeckId, destinationPoint);
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        UUID droidId = this.droidManager.addDroid(name);
        MaintenanceDroid droid = this.droidManager.getMaintenanceDroidByUUID(droidId);
        this.maintenanceDroidRepository.save(droid);
        return droidId;
    }

    /**
     * This method lets the maintenance droid execute a task.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another spaceship deck
     * ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a barrier or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        Boolean commandResult = droidManager.executeRequestedCommandOnDroid(maintenanceDroidId, task);
        if (commandResult) {
            MaintenanceDroid droid = this.droidManager.getMaintenanceDroidByUUID(maintenanceDroidId);
            this.maintenanceDroidRepository.save(droid);
        }

        return commandResult;
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        MaintenanceDroid droid = this.droidManager.getMaintenanceDroidByUUID(maintenanceDroidId);
        return droid.getSpaceShipDeckId();
    }

    /**
     * This method returns the point a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the point of the maintenance droid
     */
    public Point getPoint(UUID maintenanceDroidId) {
        return this.droidManager.getMaintenanceDroidByUUID(maintenanceDroidId).getPoint();
    }
}
