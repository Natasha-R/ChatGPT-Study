package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Obstacle.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.Obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.Obstacle.Wall;
import thkoeln.st.st2praktikum.exercise.Space.CleaningDeviceTransporter;
import thkoeln.st.st2praktikum.exercise.Space.Connection;
import thkoeln.st.st2praktikum.exercise.Space.RectangularSpace;
import thkoeln.st.st2praktikum.exercise.Space.Space;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CleaningDeviceService {
    private final Map<UUID, Space> spaceRepo = new HashMap<>();
    private final Map<UUID, Obstacle> obstacleRepo = new HashMap<>();
    private final Map<UUID, Connection> connectionRepo = new HashMap<>();
    private final Map<UUID, CleaningDevice> cleaningDeviceRepo = new HashMap<>();

    private CleaningDeviceTransporter transporter = new CleaningDeviceTransporter();
    private Command command = new Command(spaceRepo, cleaningDeviceRepo, transporter);


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
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     * @return the UUID of the created obstacle
     */
    public UUID addObstacle(UUID spaceId, String obstacleString) {
        Coordinate[] path = Command.parsePath(obstacleString);

        Wall wall = new Wall(path[0], path[1]);

        UUID wallId = wall.getId();
        spaceRepo.get(spaceId).addObstacle(wall);
        obstacleRepo.put(wallId, wall);

        return wallId;
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
        Space sourceSpace = spaceRepo.get(sourceSpaceId);
        Space destinationSpace = spaceRepo.get(destinationSpaceId);
        Coordinate entry = Command.parseCoordinate(sourceCoordinate);
        Coordinate exit = Command.parseCoordinate(destinationCoordinate);

        Connection connection = new Connection(sourceSpace, entry, destinationSpace, exit);

        UUID connectionId = connection.getId();
        connectionRepo.put(connectionId, connection);
        transporter.addConnection(connection);

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
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,space-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another space
     * "[en,<space-id>]" for setting the initial space where a cleaning device is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, String commandString) {
        return command.execute(cleaningDeviceId, commandString);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        CleaningDevice device = cleaningDeviceRepo.get(cleaningDeviceId);
        if (device == null) throw new RuntimeException(
                "CleaningDevice " + cleaningDeviceId.toString() + " existiert nicht");
        return device.getSpace().getId();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId){
        CleaningDevice device = cleaningDeviceRepo.get(cleaningDeviceId);
        if (device == null) throw new RuntimeException(
                "CleaningDevice " + cleaningDeviceId.toString() + " existiert nicht");
        return device.getPosition().toString();
    }
}
