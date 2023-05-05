package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class CleaningDeviceService {

    private HashMap<UUID, CleaningDevice> cleaningDevices;
    private HashMap<UUID, Space> spaces;

    public CleaningDeviceService(){
        cleaningDevices = new HashMap<UUID, CleaningDevice>();
        spaces = new HashMap<UUID, Space>();
    }
    
    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */

    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height, width);
        UUID id = space.getId();

        spaces.put(id, space);

        return id;
    }
    
    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */

    public void addWall(UUID spaceId, String wallString) {
        Space space = spaces.get(spaceId);
        space.addWall(wallString);
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
        Connection connection = new Connection(sourceSpaceId, sourceCoordinate, destinationSpaceId, destinationCoordinate);
        Space sourceSpace = spaces.get(sourceSpaceId);
        Space destinationSpace = spaces.get(destinationSpaceId);

        sourceSpace.addConnection(connection);
        destinationSpace.addConnection(connection);

        return connection.getId();
    }
    
    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */

    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name, spaces);
        UUID id = cleaningDevice.getId();

        cleaningDevices.put(id, cleaningDevice);

        return id;
    }
    
    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on cells with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */

    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {
        CleaningDevice cleaningDevice = cleaningDevices.get(cleaningDeviceId);
        return cleaningDevice.execute(commandString);
    }
    
    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */

    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        CleaningDevice cleaningDevice = cleaningDevices.get(cleaningDeviceId);
        return cleaningDevice.getCleaningDeviceSpaceId();
    }
    
    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */

    public String getCoordinates(UUID cleaningDeviceId){
        CleaningDevice cleaningDevice = cleaningDevices.get(cleaningDeviceId);
        return cleaningDevice.getCoordinates();
    }
}
