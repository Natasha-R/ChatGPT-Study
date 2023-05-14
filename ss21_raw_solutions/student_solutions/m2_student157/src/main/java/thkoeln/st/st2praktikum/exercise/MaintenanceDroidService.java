package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.UUID;

public class MaintenanceDroidService {

    @Getter
    ArrayList<SpaceShipDeck> spaceShipDecks = new ArrayList<>();
    ArrayList<Droid> unplacedDroids = new ArrayList<>();

    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height, width);
        this.spaceShipDecks.add(spaceShipDeck);
        return spaceShipDeck.getUuid();
    }

    /**
     * This method adds a wall to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        SpaceShipDeck spaceShipDeck = this.getSpaceShipDeckByUUID(spaceShipDeckId);
        spaceShipDeck.addWall(wall);
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
        Connection connection = new Connection(sourceSpaceShipDeckId, sourceVector2D, destinationSpaceShipDeckId, destinationVector2D);
        SpaceShipDeck spaceShipDeck = this.getSpaceShipDeckByUUID(sourceSpaceShipDeckId);
        spaceShipDeck.addConnection(connection);

        return connection.getUuid();
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        Droid droid = new Droid(name);
        this.unplacedDroids.add(droid);
        return droid.getUuid();
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
     *      (Movement commands are always successful, even if the maintenance droid hits a wall or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    @SneakyThrows
    public Boolean executeCommand(UUID maintenanceDroidId, Order order) {
        if(order.isInitialisationCommand()) {
            return initialiseDroid(maintenanceDroidId, order);
        }

        if(order.isTransportCommand()) {
            return transportDroid(maintenanceDroidId);
        }

        if(order.isMoveCommand()) {
            return moveDroid(maintenanceDroidId, order);
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
            Droid droid = this.getDroidByUUID(maintenanceDroidId);
            Connection connection = source.transportDroid(droid);
            SpaceShipDeck destination = this.getSpaceShipDeckByUUID(connection.getDestinationSpaceShipDeckId());
            return destination.addDroid(droid, connection.getDestinationCoordinate());
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean initialiseDroid(UUID maintenanceDroidId, Order command) {
        SpaceShipDeck spaceShipDeck = this.getSpaceShipDeckByUUID(command.getGridId());

        if(spaceShipDeck.canPlaceDroid()){
            Droid droid = this.getAndRemoveUnplacedDroid(maintenanceDroidId);
            spaceShipDeck.addDroid(droid);
            return true;
        }
        return false;
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        for (SpaceShipDeck spaceShipDeck: this.spaceShipDecks) {
            if(spaceShipDeck.hasDroidWithId(maintenanceDroidId)) {
                return spaceShipDeck.getUuid();
            }
        }
        return null;
    }

    /**
     * This method returns the vector2Ds a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the vector2Ds of the maintenance droid
     */
    public Vector2D getVector2D(UUID maintenanceDroidId){
        Droid droid = this.getDroidByUUID(maintenanceDroidId);
        return droid.getCoordinates();
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
        for (SpaceShipDeck spaceShipDeck: this.spaceShipDecks) {
            for (Droid droid: spaceShipDeck.getDroids()) {
                if(droid.getUuid().equals(droidUuid)) {
                    return spaceShipDeck;
                }
            }
        }

        throw new Exception("Spaceshipdeck not found");
    }

    private SpaceShipDeck getSpaceShipByDroidUUID(String uuid) throws Exception {
        return this.getSpaceShipByDroidUUID(UUID.fromString(uuid));
    }

    private Droid getAndRemoveUnplacedDroid(UUID uuid) {
        Droid droid = this.getUnplacedDroid(uuid);
        this.unplacedDroids.remove(droid);
        return droid;
    }

    private Droid getUnplacedDroid(UUID uuid) {
        for (Droid droid:
                this.unplacedDroids) {
            if(droid.getUuid() == uuid) {
                return droid;
            }
        }
        return null;
    }

    private Droid getDroidByUUID(UUID droidUUID) {
        for (SpaceShipDeck spaceShipDeck:
                this.spaceShipDecks) {
            for (Droid droid:
                    spaceShipDeck.getDroids()) {
                if(droid.getUuid().equals(droidUUID)) {
                    return droid;
                }
            }
        }

        return null;
    }
}
