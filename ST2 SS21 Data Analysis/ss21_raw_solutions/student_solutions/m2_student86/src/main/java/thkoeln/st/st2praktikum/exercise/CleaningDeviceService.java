package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CleaningDeviceService {
    private final Map<UUID, Space> spaceRepo = new HashMap<>();
    private final Map<UUID, Obstacle> obstacleRepo = new HashMap<>();
    private final Map<UUID, Connection> connectionRepo = new HashMap<>();
    private final Map<UUID, CleaningDevice> cleaningDeviceRepo = new HashMap<>();
    private ConnectionNetwork network = new ConnectionNetwork();

    public Space getSpace (UUID spaceId) {
        Space space = spaceRepo.get(spaceId);
        if (space == null) throw new RuntimeException(
                "Space " + spaceId.toString() + " existiert nicht");
        return space;
    }

    public CleaningDevice getCleaningDevice (UUID cleaningDeviceId) {
        CleaningDevice device = cleaningDeviceRepo.get(cleaningDeviceId);
        if (device == null) throw new RuntimeException(
                "CleaningDevice " + cleaningDeviceId.toString() + " existiert nicht");
        return device;
    }

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new RectangularSpace(width, height);
        UUID spaceId = space.getId();
        spaceRepo.put(spaceId, space);
        return spaceId;
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        getSpace(spaceId).addBlocker(obstacle);
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
        Space sourceSpace = getSpace(sourceSpaceId);
        Space destinationSpace = getSpace(destinationSpaceId);

        Connection connection = new Connection(sourceSpace, sourceVector2D,
                                                destinationSpace, destinationVector2D);

        UUID connectionId = connection.getId();
        connectionRepo.put(connectionId, connection);
        network.addConnection(connection);

        return connectionId;
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        UUID connectionId = cleaningDevice.getId();
        cleaningDeviceRepo.put(connectionId, cleaningDevice);
        return connectionId;
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
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        CleaningDevice cleaningDevice = getCleaningDevice(cleaningDeviceId);

        switch (task.getTaskType()) {
            case TRANSPORT: {
                Space space = getSpace(task.getGridId());
                try { cleaningDevice.transportToSpace(network, space);
                } catch (Exception e) { return false; }
                break;
            }
            case ENTER: {
                Space space = getSpace(task.getGridId());
                try { cleaningDevice.enterSpace(space);
                } catch (Exception e) { return false; }
                break;
            }
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                Tile.Direction direction = task.getTaskType().convertToDirection();
                cleaningDevice.move(direction, task.getNumberOfSteps());
                break;
        }
        return true;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        CleaningDevice device = getCleaningDevice(cleaningDeviceId);
        Space space = device.getSpace();
        if (space == null) throw new RuntimeException(
                "CleaningDevice  " + cleaningDeviceId.toString() + " steht in keinem Space");
        return space.getId();
    }

    /**
     * This method returns the vector2Ds a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector2Ds of the cleaning device
     */
    public Vector2D getVector2D(UUID cleaningDeviceId){
        return getCleaningDevice(cleaningDeviceId).getPosition();
    }
}
