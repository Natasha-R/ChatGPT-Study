package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.support.ManagedMap;
import thkoeln.st.st2praktikum.exercise.barrier.Barrier;
import thkoeln.st.st2praktikum.exercise.barrier.BarrierStringParser;
import thkoeln.st.st2praktikum.exercise.command.Command;
import thkoeln.st.st2praktikum.exercise.command.CommandParser;
import thkoeln.st.st2praktikum.exercise.command.SetInitialSpaceCommand;
import thkoeln.st.st2praktikum.exercise.command.WalkCommand;
import thkoeln.st.st2praktikum.exercise.space.ISpace;
import thkoeln.st.st2praktikum.exercise.space.Space;
import thkoeln.st.st2praktikum.exercise.space.SpaceConnection;

import java.awt.*;
import java.util.*;

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
     * @param spaceId       the ID of the space the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID spaceId, String barrierString) {
        final ISpace space = getSpace(spaceId);
        final Barrier barrier = BarrierStringParser.parse(barrierString);
        space.addBarrier(barrier);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     *
     * @param sourceSpaceId         the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate      the coordinates of the entry point
     * @param destinationSpaceId    the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        final ISpace source = getSpace(sourceSpaceId);
        final ISpace destination = getSpace(destinationSpaceId);
        final Point sourcePoint = CoordinateParser.parse(sourceCoordinate);
        final Point destinationPoint = CoordinateParser.parse(destinationCoordinate);

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
     * @param commandString    the given command, encoded as a String:
     *                         "[direction, steps]" for movement, e.g. "[we,2]"
     *                         "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another space
     *                         "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the cleaning device hits a barrier or
     * another cleaning device, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {
        final CleaningDevice device = devices.get(cleaningDeviceId);
        final Command command = CommandParser.parse(commandString);

        switch (command.getCommandType()) {
            case walk:
                final WalkCommand walkCommand = (WalkCommand)command;
                return walkService.walk(walkCommand.getDirection(), walkCommand.getSteps(), device, device.getSpace());
            case transport:
                // todo why do we need the destination space id? it was already provided in the addConnection method
                // final TransportCommand transportCommand = (TransportCommand) command;
                // final Space destinationSpace = getSpace(transportCommand.spaceId);
                return transportService.transport(device);
            case setInitialSpace:
                final SetInitialSpaceCommand setInitialSpaceCommand = (SetInitialSpaceCommand) command;
                final ISpace space = getSpace(setInitialSpaceCommand.getSpaceId());
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
     * This method returns the coordinates a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId) {
        final CleaningDevice device = devices.get(cleaningDeviceId);
        return device.getCoordinates();
    }

    private ISpace getSpace(UUID spaceId) {
        return spaces.get(spaceId);
    }
}
