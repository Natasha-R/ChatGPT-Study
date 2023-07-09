package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class CleaningDeviceService {

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        return SpaceFactory.CreateSpace(height, width).getId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        SpaceInterface iSpace = SpaceFactory.spaces.get(spaceId);
        iSpace.addWall(wall);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        Connection connection = new Connection(sourceSpaceId, destinationSpaceId, sourceCoordinate, destinationCoordinate);
        SpaceInterface iSpace = SpaceFactory.spaces.get(sourceSpaceId);
        iSpace.addConnection(connection);
        return connection.getId();
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        DeviceInterface iDevice = DeviceFactory.CreateDevice(name);
        return iDevice.getId();
    }

    /**
     * This method lets the cleaning device execute a task.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        return DeviceFactory.getDevices().get(cleaningDeviceId).executeCommand(task);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return DeviceFactory.getDevices().get(cleaningDeviceId).getSpaceId();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinates of the cleaning device
     */
    public Coordinate getCoordinate(UUID cleaningDeviceId){
        return DeviceFactory.getDevices().get(cleaningDeviceId).getCoordinate();
    }
}
