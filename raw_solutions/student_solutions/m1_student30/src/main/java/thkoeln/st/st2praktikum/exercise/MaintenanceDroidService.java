package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MaintenanceDroidService {
    private HashMap<UUID,MaintanceDroid> DroidHashMap = new HashMap<>();
    private HashMap<UUID, ShipDeck> ShipDeckHashMap = new HashMap<>();

    /**
     * This method creates a new spaceShipDeck.
     * @param height the height of the spaceShipDeck
     * @param width the width of the spaceShipDeck
     * @return the UUID of the created spaceShipDeck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {

        ShipDeck shipDeck=new ShipDeck(height,width);
        UUID id = UUID.randomUUID();
        ShipDeckHashMap.put(id,shipDeck);
        return id;
    }

    /**
     * This method adds a wall to a given spaceShipDeck.
     * @param spaceShipDeckId the ID of the spaceShipDeck the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID spaceShipDeckId, String wallString) {
        ShipDeck shipDeck= ShipDeckHashMap.get(spaceShipDeckId);
        shipDeck.buildBarrier(wallString);
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
        UUID connectionID;

        connectionID= ShipDeckHashMap.get(sourceSpaceShipDeckId).addConnection(sourceSpaceShipDeckId,sourceCoordinate,destinationSpaceShipDeckId,destinationCoordinate);
        if (connectionID == null)
        {
            throw new UnsupportedOperationException();
        }
        else
            return connectionID;
    }

    /**
     * This method adds a new maintenance droid
     * @param name the name of the maintenance droid
     * @return the UUID of the created maintenance droid
     */
    public UUID addMaintenanceDroid(String name) {
        MaintanceDroid MaintanceDroid = new MaintanceDroid(name);
        UUID id = UUID.randomUUID();
        DroidHashMap.put(id,MaintanceDroid);

        return id;
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
    public Boolean executeCommand(UUID maintenanceDroidId, String commandString) {
        //Deklaration
        CommandEncoder commandEncoder=new CommandEncoder(commandString);
        String[] resultTemp = commandEncoder.encoding();
        String command=resultTemp[0];
        String commandValue=resultTemp[1];
        //Methodenaufrufe

        switch (command) {
            case "tr":
                return DroidHashMap.get(maintenanceDroidId).traverse(UUID.fromString(commandValue), ShipDeckHashMap.get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
            case "en":
                if (spaceIsFree(UUID.fromString(commandValue))) {
                    DroidHashMap.get(maintenanceDroidId).en(UUID.fromString(commandValue));
                    return true;
                }
                else

                    return false;
            case "no":
                DroidHashMap.get(maintenanceDroidId).moveNorth(Integer.parseInt(commandValue), ShipDeckHashMap.get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case "ea":
                DroidHashMap.get(maintenanceDroidId).moveEast(Integer.parseInt(commandValue), ShipDeckHashMap.get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case "so":
                DroidHashMap.get(maintenanceDroidId).moveSouth(Integer.parseInt(commandValue), ShipDeckHashMap.get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
            case "we":
                DroidHashMap.get(maintenanceDroidId).moveWest(Integer.parseInt(commandValue), ShipDeckHashMap.get(getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId)));
                return true;
        }

        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the spaceShipDeck-ID a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the UUID of the spaceShipDeck the maintenance droid is located on
     */
    public UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId){
        if (DroidHashMap.get(maintenanceDroidId).getNowInShipDeck()!= null) {
            return DroidHashMap.get(maintenanceDroidId).getNowInShipDeck();
        }
        else
            throw new UnsupportedOperationException();
    }

    /**
     * This method returns the coordinates a maintenance droid is standing on
     * @param maintenanceDroidId the ID of the maintenance droid
     * @return the coordiantes of the maintenance droid encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID maintenanceDroidId){
        if (maintenanceDroidId!=null) {
            return DroidHashMap.get(maintenanceDroidId).getCoordinates();
        }
        else
            throw new UnsupportedOperationException();
    }
    public boolean spaceIsFree(UUID SpaceShipID ){
        for (Map.Entry<UUID, MaintanceDroid> entry : DroidHashMap.entrySet()) {
            MaintanceDroid value = entry.getValue();
            if (value.posx == 0&& value.posy == 0&&value.getNowInShipDeck().equals( SpaceShipID)) {
                return false;
            }
        }
        return true;


    }
}
