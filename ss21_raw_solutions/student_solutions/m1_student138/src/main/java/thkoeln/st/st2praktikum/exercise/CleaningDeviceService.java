package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class CleaningDeviceService {
    HashMap<UUID, Connection> connectionHashMap = new HashMap<UUID, Connection>();
    HashMap<UUID, Space> spaceHashMap = new HashMap<UUID, Space>();
    HashMap<UUID, Cleaner> cleanerHashMap = new HashMap<UUID, Cleaner>();

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height, width);
        spaceHashMap.put(space.getUuid(), space);
        return space.getUuid();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID spaceId, String obstacleString) {
        Space space = spaceHashMap.get(spaceId);
        space.addObstacle(obstacleString);
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

        String source[] = sourceCoordinate.replace("(", "").replace(")", "").split(",");
        String destination[] = destinationCoordinate.replace("(", "").replace(")", "").split(",");

        int sourceCoordinateX = Integer.parseInt(source[0]);
        int sourceCoordinateY = Integer.parseInt(source[1]);

        int destinationCoordinateX = Integer.parseInt(destination[0]);
        int destinationCoordinateY = Integer.parseInt(destination[1]);

        Space sourceSpace = spaceHashMap.get(sourceSpaceId);
        Space destinationSpace = spaceHashMap.get(destinationSpaceId);
        if (sourceSpace.fieldExists(sourceCoordinateX, sourceCoordinateY) && destinationSpace.fieldExists(destinationCoordinateX, destinationCoordinateY))
        {
            Connection connection = new Connection(sourceSpace, sourceCoordinateX, sourceCoordinateY, destinationSpace, destinationCoordinateX, destinationCoordinateY);
            connectionHashMap.put(connection.getUuid(), connection);
            sourceSpace.getField(sourceCoordinateX, sourceCoordinateY).setConnection(connection);
            return connection.getUuid();
        }
        else
        {
            throw new IndexOutOfBoundsException("There is no Field with this coordinates");
        }
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        Cleaner cleaner = new Cleaner(name);
        cleanerHashMap.put(cleaner.getUuid(), cleaner);
        return cleaner.getUuid();
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
        Cleaner cleaner = cleanerHashMap.get(cleaningDeviceId);
        Space destinationSpace;
        String[] commands = commandString.replace("[", "").replace("]", "").split(",");

        switch (commands[0])
        {
            case "tr":
                destinationSpace = spaceHashMap.get(UUID.fromString(commands[1]));
                return cleaner.transport(destinationSpace, connectionHashMap);

            case "en":
                destinationSpace = spaceHashMap.get(UUID.fromString(commands[1]));
                return cleaner.enterSpace(destinationSpace);

            case "no":
            case "ea":
            case "so":
            case "we":
                return cleaner.move(commands[0], Integer.parseInt(commands[1]));

            default:
                throw new UnsupportedOperationException("This is not a valid command");
        }
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        Cleaner cleaner = cleanerHashMap.get(cleaningDeviceId);
        return cleaner.getCurrentSpace().getUuid();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordiantes of the cleaning device encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID cleaningDeviceId){
        Cleaner cleaner = cleanerHashMap.get(cleaningDeviceId);
        return "(" + cleaner.getX() + "," + cleaner.getY() + ")";
    }

    public void printSpace(UUID space)
    {
        Space currentSpace = spaceHashMap.get(space);
        currentSpace.printSpace();
    }
}
