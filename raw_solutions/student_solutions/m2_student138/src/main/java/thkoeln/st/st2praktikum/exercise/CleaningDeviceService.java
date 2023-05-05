package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class CleaningDeviceService {
    HashMap<UUID, Connection> connectionHashMap = new HashMap<UUID, Connection>();
    HashMap<UUID, Space> spaceHashMap = new HashMap<UUID, Space>();
    HashMap<UUID, Cleaner> cleanerHashMap = new HashMap<UUID, Cleaner>();

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        Space space = spaceHashMap.get(spaceId);
        space.addObstacle(obstacle);
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

        Space sourceSpace = spaceHashMap.get(sourceSpaceId);
        Space destinationSpace = spaceHashMap.get(destinationSpaceId);
        if (sourceSpace.fieldExists(sourceVector2D.getX(), sourceVector2D.getY()) && destinationSpace.fieldExists(destinationVector2D.getX(), destinationVector2D.getY()))
        {
            Connection connection = new Connection(sourceSpaceId, sourceVector2D, destinationSpaceId, destinationVector2D);
            connectionHashMap.put(connection.getUuid(), connection);
            sourceSpace.getField(sourceVector2D.getX(), sourceVector2D.getY()).setConnection(connection.getUuid());
            return connection.getUuid();
        }
        else
        {
            throw new IndexOutOfBoundsException("There is no Field with this coordinates");
        }
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) {
        Cleaner cleaner = cleanerHashMap.get(cleaningDeviceId);
        Space destinationSpace;
        Space currentSpace = spaceHashMap.get(cleaner.getCurrentSpace());

        switch (command.getCommandType())
        {
            case TRANSPORT:
                destinationSpace = spaceHashMap.get(command.getGridId());
                return cleaner.transport(currentSpace, destinationSpace, connectionHashMap);
            case ENTER:
                destinationSpace = spaceHashMap.get(command.getGridId());
                return cleaner.enterSpace(destinationSpace);
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                return cleaner.move(currentSpace, command.getCommandType(), command.getNumberOfSteps());

            default:
                throw new UnsupportedOperationException("This is not a valid command");
        }
    }

    /**
     * This method returns the vector2Ds a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector2Ds of the cleaning device
     */
    public Vector2D getVector2D(UUID cleaningDeviceId){
        Cleaner cleaner = cleanerHashMap.get(cleaningDeviceId);
        return cleaner.getPosition();
    }

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
            Connection connection = new Connection(sourceSpaceId, sourceCoordinateX, sourceCoordinateY, destinationSpaceId, destinationCoordinateX, destinationCoordinateY);
            connectionHashMap.put(connection.getUuid(), connection);
            sourceSpace.getField(sourceCoordinateX, sourceCoordinateY).setConnection(connection.getUuid());
            //destinationSpace.getField(destinationCoordinateX, destinationCoordinateY).setConnection(connection.getUuid(), connection.getSourceSpaceId());
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
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        Cleaner cleaner = cleanerHashMap.get(cleaningDeviceId);
        return cleaner.getCurrentSpace();
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
        currentSpace.printField();
    }
}
