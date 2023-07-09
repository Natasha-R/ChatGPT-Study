package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class CleaningDeviceService {

    private HashMap<UUID,Space> world = new HashMap<UUID, Space>();
    private HashMap<UUID,CleaningDevice> cleaningDeviceHashMap = new HashMap<UUID,CleaningDevice>() ;
    private HashMap<UUID,Connection> connectionHashMap = new HashMap<UUID, Connection>();


    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height, width);
        this.world.put(space.getID(), space );
        return space.getID();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        this.world.get(spaceId).addObstacle(obstacle);
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
        Connection connection = new Connection(this.world.get(sourceSpaceId),sourceCoordinate,this.world.get(destinationSpaceId),destinationCoordinate);
        this.connectionHashMap.put(connection.getID(),connection);
        return connection.getID();
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        cleaningDeviceHashMap.put(cleaningDevice.getID(),cleaningDevice);
        return cleaningDevice.getID();
    }

    /**
     * This method lets the cleaning device execute a order.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {
        CleaningDevice cleaningDevice =this.cleaningDeviceHashMap.get(cleaningDeviceId);
        return cleaningDevice.command(order,this.connectionHashMap , this.world);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return this.cleaningDeviceHashMap.get(cleaningDeviceId).getSpaceID();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinates of the cleaning device
     */
    public Coordinate getCoordinate(UUID cleaningDeviceId){
        return this.cleaningDeviceHashMap.get(cleaningDeviceId).getCleaningDeviceCoordinate();
    }
}
