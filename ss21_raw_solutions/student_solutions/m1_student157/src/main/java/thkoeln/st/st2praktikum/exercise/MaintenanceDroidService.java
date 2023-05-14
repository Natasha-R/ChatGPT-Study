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
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID spaceShipDeckId, String wallString) {
        SpaceShipDeck spaceShipDeck = this.getSpaceShipDeckByUUID(spaceShipDeckId);
        spaceShipDeck.addWall(wallString);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     * @param sourceSpaceShipDeckId the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        Connection connection = new Connection(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
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
     * This method lets the maintenance droid execute a command.
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another spaceShipDeck
     * "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the maintenance droid hits a wall or
     *      another maintenance droid, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    @SneakyThrows
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        Command command = new Command(commandString);
        if(command.isInitialisationCommand()) {
            return initialiseDroid(maintenanceDroidId, command);
        }

        if(command.isTransportCommand()) {
            return transportDroid(maintenanceDroidId);
        }

        if(command.isMoveCommand()) {
            return moveDroid(maintenanceDroidId, command);
        }

        return false;
    }

    private Boolean moveDroid(UUID maintenanceDroidId, Command command) throws Exception {
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

    private Boolean initialiseDroid(UUID maintenanceDroidId, Command command) {
        SpaceShipDeck spaceShipDeck = this.getSpaceShipDeckByUUID(command.getOption());

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
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinates of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId) {
        Droid droid = this.getDroidByUUID(maintenanceDroidId);
        return droid.getCoordinates().toString();
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
