package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.DroidMovementService;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.repositories.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.repositories.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class MaintenanceDroidService {

    private final Boolean verboseOutput = true;

    private final MaintenanceDroidRepository droidRepository;
    private final SpaceShipDeckRepository shipDeckRepository;
    private final TransportSystemRepository transportSystemRepository;
    private final DroidMovementService movementService;

    private final MaintenanceDroidDtoMapper maintenanceDroidDtoMapper = new MaintenanceDroidDtoMapper();

    @Autowired
    public MaintenanceDroidService(MaintenanceDroidRepository droidRepository, SpaceShipDeckRepository shipDeckRepository, TransportSystemRepository transportSystemRepository) {
        this.droidRepository = droidRepository;
        this.shipDeckRepository = shipDeckRepository;
        this.transportSystemRepository = transportSystemRepository;
        this.movementService = new DroidMovementService(shipDeckRepository);
    }

    public MaintenanceDroid createDroidFromDto( MaintenanceDroidDto dungeonDto ) {
        MaintenanceDroid droid = this.maintenanceDroidDtoMapper.mapToEntity( dungeonDto );
        this.droidRepository.save( droid );
        return droid;
    }

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid newDroid = new MaintenanceDroid(name);
        droidRepository.save(newDroid);
        return newDroid.getUuid();
    }

    /**
     * This method lets the maintenance droid execute a task.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param task               the given task
     *                           NORTH, WEST, SOUTH, EAST for movement
     *                           TRANSPORT for transport - only works on grid cells with a connection to another spaceship deck
     *                           ENTER for setting the initial spaceship deck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceship deck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a barrier or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, Task task) {
        Boolean commandResult = this.executeRequestedCommandOnDroid(maintenanceDroidId, task);
        if (commandResult) {
            MaintenanceDroid droid = this.getMaintenanceDroidByUUID(maintenanceDroidId);
            this.droidRepository.save(droid);
        }

        return commandResult;
    }

    public Boolean executeRequestedCommandOnDroid(UUID maintenanceDroidId, Task task) {
        MaintenanceDroid droid = this.getMaintenanceDroidByUUID(maintenanceDroidId);

        droid.addTaskToList(task);

        switch (task.getTaskType()) {
            case TRANSPORT:
                return this.useConnection(droid, task.getGridId());
            case ENTER:
                return this.spawnDroidOnDeck(droid, task.getGridId());
            default:
                return this.moveDroid(droid, task);
        }
    }

    public boolean moveDroid(MaintenanceDroid droid, Task task) {
        return this.movementService.moveDroid(droid, task, this.verboseOutput);
    }

    public Boolean useConnection(MaintenanceDroid droid, UUID newDeckId) {
        ArrayList<Connection> deckConnections = this.transportSystemRepository.getConnectionsBySourceDeckID(droid.getSpaceShipDeckId());

        for (Connection connection : deckConnections) {
            if (connection.getSourceCoordinate().equals(droid.getPoint()) && connection.getDestinationSpaceShipDeckId().equals(newDeckId)) {
                return this.traverseConnection(droid, connection);
            }
        }

        return false;
    }

    private Boolean traverseConnection(MaintenanceDroid droid, Connection connection) {
        SpaceShipDeck newDeck = this.shipDeckRepository.getSpaceShipDeckByUUID(connection.getDestinationSpaceShipDeckId());

        if (verboseOutput) {
            System.out.println("Droid " + droid.getName() + " is traversing a connection to " + newDeck.getUuid());
        }

        return this.spawnDroidOnDeck(droid, newDeck, connection.getDestinationCoordinate());
    }

    public Boolean spawnDroidOnDeck(MaintenanceDroid droid, UUID deckId) {
        SpaceShipDeck deck = this.shipDeckRepository.getSpaceShipDeckByUUID(deckId);
        return this.spawnDroidOnDeck(droid, deck, new Point(0, 0));
    }

    public Boolean spawnDroidOnDeck(MaintenanceDroid droid, SpaceShipDeck deck, Point position) {
        if (!this.checkIfCurrentSpaceOnDeckIsFree(deck, position)) {
            if (verboseOutput) {
                System.out.println("Droid " + droid.getName() +
                        " cant traverse to deck " + deck.getUuid() +
                        " because point " + position + " is not free");
                System.out.println("--------");
            }
            return false;
        }

        if (verboseOutput) {
            System.out.println("Droid " + droid.getName() + " is spawning on deck " + deck.getUuid() + " with Position " + position);
            System.out.println("--------");
        }

        droid.spawnOnDeck(deck.getUuid(), position);

        return true;
    }

    private Boolean checkIfCurrentSpaceOnDeckIsFree(SpaceShipDeck deck, Point point) {
        List<MaintenanceDroid> droids = this.droidRepository.findBySpaceShipDeckId(deck.getUuid());
        for (MaintenanceDroid droid : droids) {
            if (droid.getPoint().equals(point)) return false;
        }

        return true;
    }

    /**
     * This method returns the spaceship deck-ID a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceship deck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        return this.getMaintenanceDroidByUUID(maintenanceDroidId).getSpaceShipDeckId();
    }

    /**
     * This method returns the point a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the point of the maintenance droid
     */
    public Point getMaintenanceDroidPoint(UUID maintenanceDroidId) {
        return this.getMaintenanceDroidByUUID(maintenanceDroidId).getPoint();
    }

    public MaintenanceDroid getMaintenanceDroidByUUID(UUID maintenanceDroidId) {
        return this.droidRepository.findById(maintenanceDroidId).orElseThrow(() ->
                new InvalidParameterException("No Droid Matches the given UUID"));
    }

    public Iterable<MaintenanceDroid> findAll() {
        return this.droidRepository.findAll();
    }

    public void deleteMaintenanceDroidByUUID(UUID maintenanceDroidId) {
        this.droidRepository.deleteById(maintenanceDroidId);
    }

    public void wipeTaskListForDroidByUUID(UUID droidId) {
        this.getMaintenanceDroidByUUID(droidId).deleteTaskList();
    }
}
