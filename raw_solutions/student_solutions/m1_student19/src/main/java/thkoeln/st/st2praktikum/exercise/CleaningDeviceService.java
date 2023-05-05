package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class CleaningDeviceService {

    private final HashMap<UUID, Space> spaceCollection = new HashMap();
    private final HashMap<UUID, CleaningDevice> cleaningDeviceCollection = new HashMap();
    private final HashMap<UUID, Connection> connectionCollection = new HashMap();

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space newSpace = new Space(width, height);
        spaceCollection.put(newSpace.getSpaceId(), newSpace);
        return newSpace.getSpaceId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID spaceId, String wallString) {
        if (spaceCollection.get(spaceId) == null)
            throw new IllegalArgumentException("There is no space existing with the given spaceId: " + spaceId);

        this.spaceCollection.get(spaceId).setWall(wallString);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        if (spaceCollection.get(sourceSpaceId) == null)
            throw new IllegalArgumentException("There is no space existing with the given sourceSpaceId: " + sourceSpaceId);
        if (spaceCollection.get(destinationSpaceId) == null)
            throw new IllegalArgumentException("There is no space existing with the given destinationSpaceId: " + destinationSpaceId);

        Connection newConnection = new Connection(sourceSpaceId, sourceCoordinate, destinationSpaceId, destinationCoordinate);
        connectionCollection.put(newConnection.getConnectionId(), newConnection);
        return newConnection.getConnectionId();
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice newCleaningDevice = new CleaningDevice(name);
        cleaningDeviceCollection.put(newCleaningDevice.getCleaningDeviceId(), newCleaningDevice);
        return newCleaningDevice.getCleaningDeviceId();
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {
        if (this.cleaningDeviceCollection.get(cleaningDeviceId) == null)
            throw new IllegalArgumentException("The given cleaning device doesn't exist: " + cleaningDeviceId);

        CleaningDevice device = this.cleaningDeviceCollection.get(cleaningDeviceId);
        Space space = this.spaceCollection.get(device.getCurrentSpaceId());

        String[] commandValues = commandString.split(",");
        String commandType = commandValues[0].substring(1);
        String commandParam = commandValues[1].substring(0, commandValues[1].length() - 1);
        var success = false;

        switch (commandType) {
            case "tr":
                success = device.traverseDevice(this.connectionCollection, UUID.fromString(commandParam));
                break;
            case "en":
                success = device.placeDevice(this.spaceCollection, this.cleaningDeviceCollection, UUID.fromString(commandParam));
                break;
            case "no":
                success = device.moveNorth(space, Integer.parseInt(commandParam));
                break;
            case "ea":
                success = device.moveEast(space, Integer.parseInt(commandParam));
                break;
            case "so":
                success = device.moveSouth(space, Integer.parseInt(commandParam));
                break;
            case "we":
                success = device.moveWest(space, Integer.parseInt(commandParam));
                break;
            default:
                throw new UnsupportedOperationException("The given command isn't possible: " + commandType);
        }

        return success;
    }

    /**
     * This method returns the space-ID a given cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        if (this.cleaningDeviceCollection.get(cleaningDeviceId) == null)
            throw new IllegalArgumentException("The given cleaning device doesn't exist: " + cleaningDeviceId);
        if (this.cleaningDeviceCollection.get(cleaningDeviceId).getCurrentSpaceId() == null)
            throw new IllegalArgumentException("The given device hasn't been placed: " + cleaningDeviceId);

        return this.cleaningDeviceCollection.get(cleaningDeviceId).getCurrentSpaceId();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId){
        if (this.cleaningDeviceCollection.get(cleaningDeviceId) == null)
            throw new IllegalArgumentException("The given cleaning device doesn't exist: " + cleaningDeviceId);

        return this.cleaningDeviceCollection.get(cleaningDeviceId).getCoordinateString();
    }
}
