package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.environment.Environment;
import thkoeln.st.st2praktikum.exercise.environment.position.EnvironmentPosition;

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
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceId, Barrier barrier) {
        environment.getSpace(spaceId).addBlockedTransitions(barrier.getMyBarrier());
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
        return environment.addConnection(EnvironmentPosition.of(environment.getSpace(sourceSpaceId), sourceVector2D.getPosition()),
                EnvironmentPosition.of(environment.getSpace(destinationSpaceId), destinationVector2D.getPosition()));
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
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a barrier or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) {
        return environment.getCleaningDevice(cleaningDeviceId).executeCommand(command.getMyCommand());
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
     * This method returns the vector2Ds a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector2Ds of the cleaning device
     */
    public Vector2D getVector2D(UUID cleaningDeviceId){
        return new Vector2D(environment.getCleaningDevice(cleaningDeviceId).getCurrentPosition());
    }
}
