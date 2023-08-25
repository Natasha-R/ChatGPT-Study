package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CleaningDeviceService {

    private final World world;

    @Autowired
    public CleaningDeviceService(World world) {
        this.world = world;
    }


    /**
     * This method creates a new space.
     *
     * @param height the height of the space
     * @param width  the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        return this.world.addSpace(height, width);
    }

    /**
     * This method adds a obstacle to a given space.
     *
     * @param fieldId  the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID fieldId, Obstacle obstacle) {
        this.world.addObstacle(fieldId, obstacle);
    }

    /**
     * This method adds a transport transporttechnology
     *
     * @param transportTechnology the type of the transport transporttechnology
     * @return the UUID of the created transport transporttechnology
     */
    public UUID addTransportTechnology(String transportTechnology) {
        return this.world.addTransportTechnology(transportTechnology);
    }

    /**
     * This method adds a traversable connection between two fields based on a transport transporttechnology. Connections only work in one direction.
     *
     * @param transportTechnologyId the transport transporttechnology which is used by the connection
     * @param sourceFieldId         the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate      the point of the entry point
     * @param destinationFieldId    the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        return this.world.addConnection(transportTechnologyId, sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
    }

    /**
     * This method adds a new cleaning device
     *
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        return this.world.addCleaningDevice(name);
    }

    /**
     * This method lets the cleaning device execute a order.
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @param order            the given order
     *                         NORTH, WEST, SOUTH, EAST for movement
     *                         TRANSPORT for transport - only works on tiles with a connection to another space
     *                         ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the cleaning device hits a barrier or
     * another cleaning device, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {
        return this.world.executeCommand(cleaningDeviceId, order);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        return this.world.getCleaningDeviceSpaceId(cleaningDeviceId);
    }

    /**
     * This method returns the point a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the point of the cleaning device
     */
    public Coordinate getCoordinate(UUID cleaningDeviceId) {
        return this.world.getCoordinate(cleaningDeviceId);
    }
}
