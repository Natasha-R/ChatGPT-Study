package thkoeln.st.st2praktikum.exercise;

import lombok.SneakyThrows;
import thkoeln.st.st2praktikum.exercise.environment.Barrier;
import thkoeln.st.st2praktikum.exercise.environment.Environment;
import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;
import thkoeln.st.st2praktikum.exercise.parser.CommandParser;

import java.util.UUID;

public class CleaningDeviceService {

    private final Environment environment = new Environment();

    /**
     * This method creates a new space.
     *
     * @param height the height of the space
     * @param width  the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        return environment.addSpace(height, width);
    }

    /**
     * This method adds a barrier to a given space.
     * @param spaceId the ID of the space the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    @SneakyThrows
    public void addBarrier(UUID spaceId, String barrierString) {
        environment.getSpace(spaceId).addBlockedTransitions(Barrier.of(barrierString));
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    @SneakyThrows
    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        return environment.addConnection(EnvironmentPosition.of(environment.getSpace(sourceSpaceId), sourceCoordinate),
                EnvironmentPosition.of(environment.getSpace(destinationSpaceId), destinationCoordinate));
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        return environment.addCleaningDevice(name);
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a barrier or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    @SneakyThrows
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {
        return environment.getCleaningDevice(cleaningDeviceId).executeCommand(new CommandParser().parse(commandString));
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return environment.getCleaningDevice(cleaningDeviceId).getCurrentPosition().getSpace().getUuid();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId){
        return environment.getCleaningDevice(cleaningDeviceId).getCurrentPosition().toPositionString();
    }
}
