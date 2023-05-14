package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class CleaningDeviceService {

    private final HashMap<UUID, Space> spaceCollection = new HashMap();
    private final HashMap<UUID, Connection> connectionCollection = new HashMap();
    private final HashMap<UUID, CleaningDevice> cleaningDeviceCollection = new HashMap();

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
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        if (spaceCollection.get(spaceId) == null)
            throw new IllegalArgumentException("There is no space existing with the given spaceId: " + spaceId);

        this.spaceCollection.get(spaceId).setWall(wall);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceVector2D the vector2Ds of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationVector2D the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, Vector2D sourceVector2D, UUID destinationSpaceId, Vector2D destinationVector2D) {
        if (spaceCollection.get(sourceSpaceId) == null)
            throw new IllegalArgumentException("There is no space existing with the given sourceSpaceId: " + sourceSpaceId);
        if (spaceCollection.get(destinationSpaceId) == null)
            throw new IllegalArgumentException("There is no space existing with the given destinationSpaceId: " + destinationSpaceId);
        if (spaceCollection.get(sourceSpaceId).getSpaceHeight() < sourceVector2D.getY() ||
            spaceCollection.get(destinationSpaceId).getSpaceHeight() < destinationVector2D.getX())
            throw new IllegalArgumentException("Given Vectors are out of bounds for the given Spaces.");

        Connection newConnection = new Connection(sourceSpaceId, sourceVector2D, destinationSpaceId, destinationVector2D);
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
     * This method lets the cleaning device execute a task.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        if (this.cleaningDeviceCollection.get(cleaningDeviceId) == null)
            throw new IllegalArgumentException("The given cleaning device doesn't exist: " + cleaningDeviceId);

        CleaningDevice device = this.cleaningDeviceCollection.get(cleaningDeviceId);
        Space space = this.spaceCollection.get(device.getCurrentSpaceId());
        var success = false;

        switch (task.getTaskType()) {
            case TRANSPORT:
                success = device.traverseDevice(this.connectionCollection, this.cleaningDeviceCollection, task.getGridId());
                break;
            case ENTER:
                success = device.placeDevice(this.spaceCollection, this.cleaningDeviceCollection, task.getGridId());
                break;
            case NORTH:
                success = device.moveNorth(space, task.getNumberOfSteps(), this.cleaningDeviceCollection);
                break;
            case EAST:
                success = device.moveEast(space, task.getNumberOfSteps(), this.cleaningDeviceCollection);
                break;
            case SOUTH:
                success = device.moveSouth(space, task.getNumberOfSteps(), this.cleaningDeviceCollection);
                break;
            case WEST:
                success = device.moveWest(space, task.getNumberOfSteps(), this.cleaningDeviceCollection);
                break;
        }

        return success;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
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
     * This method returns the vector2Ds a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector2Ds of the cleaning device
     */
    public Vector2D getVector2D(UUID cleaningDeviceId){
        if (this.cleaningDeviceCollection.get(cleaningDeviceId) == null)
            throw new IllegalArgumentException("The given cleaning device doesn't exist: " + cleaningDeviceId);

        return this.cleaningDeviceCollection.get(cleaningDeviceId).getCoordinates();
    }
}
