package thkoeln.st.st2praktikum.exercise;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class MaintenanceDroidService {

    private MaintenanceDroidRepository maintenanceDroidRepository;
    ArrayList<MaintenanceDroid> unplacedMaintenanceDroids = new ArrayList<>();
    private List<SpaceShipDeck> spaceShipDecks = new ArrayList<>();
    private List<TransportTechnology> transportTechnologies = new ArrayList<>();

    @Autowired
    public MaintenanceDroidService(MaintenanceDroidRepository maintenanceDroidRepository) {
        this.maintenanceDroidRepository = maintenanceDroidRepository;
    }

    /**
     * This method creates a new spaceShipDeck.
     *
     * @param height the height of the spaceShipDeck
     * @param width  the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height, width);
        this.spaceShipDecks.add(spaceShipDeck);
        return spaceShipDeck.getUuid();
    }

    /**
     * This method adds a wall to a given spaceShipDeck.
     *
     * @param spaceShipDeckId the ID of the spaceShipDeck the wall shall be placed on
     * @param wall            the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        SpaceShipDeck spaceShipDeck = this.getSpaceShipDeckByUUID(spaceShipDeckId);
        spaceShipDeck.addWall(wall);
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologies.add(transportTechnology);
        return transportTechnology.getUuid();
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     *
     * @param sourceSpaceShipDeckId      the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceVector2D             the vector2Ds of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationVector2D        the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnology, UUID sourceSpaceShipDeckId, Vector2D sourceVector2D, UUID destinationSpaceShipDeckId, Vector2D destinationVector2D) {
        Connection connection = new Connection(sourceSpaceShipDeckId, sourceVector2D, destinationSpaceShipDeckId, destinationVector2D);
        SpaceShipDeck spaceShipDeck = this.getSpaceShipDeckByUUID(sourceSpaceShipDeckId);
        spaceShipDeck.addConnection(connection);

        return connection.getId();
    }

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(name);
        this.unplacedMaintenanceDroids.add(maintenanceDroid);
        this.maintenanceDroidRepository.save(maintenanceDroid);
        return maintenanceDroid.getUuid();
    }

    /**
     * This method lets the maintenance droid execute a order.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param order              the given order
     *                           NORTH, WEST, SOUTH, EAST for movement
     *                           TRANSPORT for transport - only works on squares with a connection to another spaceShipDeck
     *                           ENTER for setting the initial spaceShipDeck where a maintenance droid is placed. The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a wall or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    @SneakyThrows
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        if (order.isInitialisationCommand()) {
            if (initialiseDroid(maintenanceDroidId, order)) {
                MaintenanceDroid maintenanceDroid = this.getDroidByUUID(maintenanceDroidId);
                this.maintenanceDroidRepository.save(maintenanceDroid);
                return true;
            }
        }

        if (order.isTransportCommand()) {
            if (transportDroid(maintenanceDroidId)) {
                MaintenanceDroid maintenanceDroid = this.getDroidByUUID(maintenanceDroidId);
                this.maintenanceDroidRepository.save(maintenanceDroid);
                return true;
            }
        }

        if (order.isMoveCommand()) {
            if (moveDroid(maintenanceDroidId, order)) {
                MaintenanceDroid maintenanceDroid = this.getDroidByUUID(maintenanceDroidId);
                this.maintenanceDroidRepository.save(maintenanceDroid);
                return true;
            }
        }

        return false;
    }

    private Boolean moveDroid(UUID maintenanceDroidId, Order command) throws Exception {
        SpaceShipDeck spaceShipDeck = this.getSpaceShipByDroidUUID(maintenanceDroidId);
        return spaceShipDeck.moveDroid(maintenanceDroidId, command);
    }

    private Boolean transportDroid(UUID maintenanceDroidId) {
        try {
            SpaceShipDeck source = this.getSpaceShipByDroidUUID(maintenanceDroidId);
            MaintenanceDroid maintenanceDroid = this.getDroidByUUID(maintenanceDroidId);
            Connection connection = source.transportDroid(maintenanceDroid);
            SpaceShipDeck destination = this.getSpaceShipDeckByUUID(connection.getDestinationSpaceShipDeckId());
            maintenanceDroid.setSpaceShipDeckId(destination.getUuid());
            return destination.addDroid(maintenanceDroid, connection.getDestinationCoordinate());
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean initialiseDroid(UUID maintenanceDroidId, Order command) {
        SpaceShipDeck spaceShipDeck = this.getSpaceShipDeckByUUID(command.getGridId());
        if (spaceShipDeck.canPlaceDroid()) {
            MaintenanceDroid maintenanceDroid = this.getAndRemoveUnplacedDroid(maintenanceDroidId);
            spaceShipDeck.addDroid(maintenanceDroid);
            maintenanceDroid.setSpaceShipDeckId(spaceShipDeck.getUuid());
            return true;
        }
        return false;
    }

    /**
     * This method returns the vector2Ds a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector2Ds of the maintenance droid
     */
    public Vector2D getVector2D(UUID maintenanceDroidId) {
        MaintenanceDroid maintenanceDroid = this.getDroidByUUID(maintenanceDroidId);
        return maintenanceDroid.getCoordinates();
    }

    private SpaceShipDeck getSpaceShipDeckByUUID(UUID uuid) {
        return this.spaceShipDecks.stream()
                .filter(deck -> uuid.equals(deck.getUuid()))
                .findFirst()
                .orElseThrow();
    }

    private SpaceShipDeck getSpaceShipDeckByUUID(String uuid) {
        return this.getSpaceShipDeckByUUID(UUID.fromString(uuid));
    }

    private SpaceShipDeck getSpaceShipByDroidUUID(UUID droidUuid) throws Exception {
        for (SpaceShipDeck spaceShipDeck : this.spaceShipDecks) {
            for (MaintenanceDroid maintenanceDroid : spaceShipDeck.getMaintenanceDroids()) {
                if (maintenanceDroid.getUuid().equals(droidUuid)) {
                    return spaceShipDeck;
                }
            }
        }

        throw new Exception("SpaceShipDeck not found");
    }

    private SpaceShipDeck getSpaceShipByDroidUUID(String uuid) throws Exception {
        return this.getSpaceShipByDroidUUID(UUID.fromString(uuid));
    }

    private MaintenanceDroid getAndRemoveUnplacedDroid(UUID uuid) {
        MaintenanceDroid maintenanceDroid = this.getUnplacedDroid(uuid);
        this.unplacedMaintenanceDroids.remove(maintenanceDroid);
        return maintenanceDroid;
    }

    private MaintenanceDroid getUnplacedDroid(UUID uuid) {
        for (MaintenanceDroid maintenanceDroid :
                this.unplacedMaintenanceDroids) {
            if (maintenanceDroid.getUuid() == uuid) {
                return maintenanceDroid;
            }
        }
        return null;
    }

    private MaintenanceDroid getDroidByUUID(UUID droidUUID) {
        for (SpaceShipDeck spaceShipDeck :
                this.spaceShipDecks) {
            for (MaintenanceDroid maintenanceDroid :
                    spaceShipDeck.getMaintenanceDroids()) {
                if (maintenanceDroid.getUuid().equals(droidUUID)) {
                    return maintenanceDroid;
                }
            }
        }

        throw new RuntimeException("DROID NOT FOUND");
    }
}
