package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.support.ManagedMap;
import thkoeln.st.st2praktikum.exercise.space.ISpace;
import thkoeln.st.st2praktikum.exercise.space.Space;
import thkoeln.st.st2praktikum.exercise.space.SpaceConnection;

import java.util.Map;
import java.util.UUID;

public class CleaningDeviceService {

    private final IWalkService walkService = new WalkService();
    private final ITransportService transportService = new TransportService();
    private final SetInitialSpaceService setInitialSpaceService = new SetInitialSpaceService();

    private final Map<UUID, ISpace> spaces = new ManagedMap<>();
    private final Map<UUID, CleaningDevice> devices = new ManagedMap<>();

    /**
     * This method creates a new space.
     *
     * @param height the height of the space
     * @param width  the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        final Space space = new Space(UUID.randomUUID(), height, width);
        spaces.put(space.getUuid(), space);
        return space.getUuid();
    }

    /**
     * This method adds a barrier to a given space.
     *
     * @param spaceId the ID of the space the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceId, Barrier barrier) {
        final ISpace space = getSpace(spaceId);
        if (barrier.getStart().getX() > space.getWidth() ||
                barrier.getEnd().getX() > space.getWidth() ||
                barrier.getStart().getY() > space.getHeight() ||
                barrier.getEnd().getY() > space.getHeight()
        ) {
            throw new IllegalArgumentException("barrier is out of bound!");
        }
        space.addBarrier(barrier);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     *
     * @param sourceSpaceId      the ID of the space where the entry point of the connection is located
     * @param sourcePoint        the points of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationPoint   the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, Point sourcePoint, UUID destinationSpaceId, Point destinationPoint) {
        final ISpace source = getSpace(sourceSpaceId);
        final ISpace destination = getSpace(destinationSpaceId);

        if (sourcePoint.getX() > source.getWidth() || sourcePoint.getY() > source.getHeight())
            throw new IllegalArgumentException("sourcePoint is out of bounds!");

        if (destinationPoint.getX() > destination.getWidth() || destinationPoint.getY() > destination.getHeight())
            throw new IllegalArgumentException("destinationPoint is out of bounds!");

        final SpaceConnection connection = new SpaceConnection(UUID.randomUUID(), source, sourcePoint, destination, destinationPoint);
        source.addConnection(connection);
        destination.addConnection(connection);

        return connection.getUuid();
    }

    /**
     * This method adds a new cleaning device
     *
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        final CleaningDevice device = new CleaningDevice(UUID.randomUUID(), name);
        devices.put(device.getUuid(), device);
        return device.getUuid();
    }

    /**
     * This method lets the cleaning device execute a command.
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @param command          the given command
     *                         NORTH, WEST, SOUTH, EAST for movement
     *                         TRANSPORT for transport - only works on gridcells with a connection to another space
     *                         ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the cleaning device hits a barrier or
     * another cleaning device, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) {
        final CleaningDevice device = devices.get(cleaningDeviceId);

        switch (command.getCommandType()) {
            case EAST:
            case WEST:
            case NORTH:
            case SOUTH:
                return walkService.walk(command.getCommandType(), command.getNumberOfSteps(), device, device.getSpace());
            case TRANSPORT:
                // todo why do we need the destination space id? it was already provided in the addConnection method
                return transportService.transport(device);
            case ENTER:
                final ISpace space = getSpace(command.getGridId());
                return setInitialSpaceService.setSpace(space, device);
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        final CleaningDevice device = devices.get(cleaningDeviceId);
        return device.getSpace().getUuid();
    }

    /**
     * This method returns the points a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the points of the cleaning device
     */
    public Point getPoint(UUID cleaningDeviceId) {
        final CleaningDevice device = devices.get(cleaningDeviceId);
        return device.getCoordinates();
    }

    private ISpace getSpace(UUID spaceId) {
        return spaces.get(spaceId);
    }
}
