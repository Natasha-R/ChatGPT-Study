package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaintenanceDroidService {

    private final DeckManager deckManager = new DeckManager();
    private final DroidManager droidManager = new DroidManager(deckManager);

    /**
     * This method creates a new spaceShipDeck.
     *
     * @param height the height of the spaceShipDeck
     * @param width  the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        return deckManager.addDeck(height, width);
    }

    /**
     * This method adds a barrier to a given spaceShipDeck.
     *
     * @param spaceShipDeckId the ID of the spaceShipDeck the barrier shall be placed on
     * @param barrierString   the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID spaceShipDeckId, String barrierString) {
        deckManager.addBarrierToDeckByUUID(spaceShipDeckId, barrierString);
    }

    /**
     * This method adds a traversable connection between two spaceship decks. Connections only work in one direction.
     *
     * @param sourceSpaceShipDeckId      the ID of the spaceShipDeck where the entry point of the connection is located
     * @param sourceCoordinate           the coordinates of the entry point
     * @param destinationSpaceShipDeckId the ID of the spaceShipDeck where the exit point of the connection is located
     * @param destinationCoordinate      the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        return deckManager.addConnectionToDeckByUUID(sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
    }

    /**
     * This method adds a new maintenance droid
     *
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        return this.droidManager.addDroid(name);
    }

    /**
     * This method lets the maintenance droid execute a command.
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @param commandString      the given command, encoded as a String:
     *                           "[direction, steps]" for movement, e.g. "[we,2]"
     *                           "[tr,spaceShipDeck-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another spaceShipDeck
     *                           "[en,<spaceShipDeck-id>]" for setting the initial spaceShipDeck where a maintenance droid is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The maintenance droid will always spawn at (0,0) of the given spaceShipDeck.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the maintenance droid hits a barrier or
     * another maintenance droid, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        String command = "";
        String parameter = "";

        // generate command and parameter from string with layout [command, parameter]
        Pattern commandStringPattern = Pattern.compile("\\[(.*),(.*)]");
        Matcher commandStringMatcher = commandStringPattern.matcher(commandString);
        while (commandStringMatcher.find()) {
            command = commandStringMatcher.group(1);
            parameter = commandStringMatcher.group(2);
        }

        return droidManager.executeRequestedCommandOnDroid(maintenanceDroidId, command, parameter, commandString);
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId) {
        MaintenanceDroid droid = this.droidManager.getMaintenanceDroidByUUID(maintenanceDroidId);
        return droid.getCurrentDeckUuid();
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     *
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordinates of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId) {
        return this.droidManager.getMaintenanceDroidByUUID(maintenanceDroidId).getCoordinates();
    }
}
