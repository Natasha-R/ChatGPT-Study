package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    HashMap<UUID, Connection> connectionHashMap = new HashMap<UUID, Connection>();
    HashMap<UUID, Space> spaceHashMap = new HashMap<UUID, Space>();
    HashMap<UUID, CleaningDevice> cleanerHashMap = new HashMap<UUID, CleaningDevice>();
    HashMap<UUID, TransportCategory> transportCategoryHashMap = new HashMap<UUID, TransportCategory>();

    @Autowired
    CleanerRepository cleanerRepository;
    //@Autowired
    //FieldRepository fieldRepository;
    @Autowired
    SpaceRepository spaceRepository;

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height, width/*, fieldRepository*/);
        spaceHashMap.put(space.getUuid(), space);
        spaceRepository.save(space);
        return space.getUuid();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        Space space = spaceHashMap.get(spaceId);
        space.addObstacle(obstacle/*, fieldRepository*/);
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategoryHashMap.put(transportCategory.getId(), transportCategory);
        return transportCategory.getId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceId, Vector2D sourceVector2D, UUID destinationSpaceId, Vector2D destinationVector2D) {
        Space sourceSpace = spaceHashMap.get(sourceSpaceId);
        Space destinationSpace = spaceHashMap.get(destinationSpaceId);
        if (sourceSpace.fieldExists(sourceVector2D.getX(), sourceVector2D.getY()) && destinationSpace.fieldExists(destinationVector2D.getX(), destinationVector2D.getY()))
        {
            Connection connection = new Connection(transportCategoryId, sourceSpaceId, sourceVector2D, destinationSpaceId, destinationVector2D);
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
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        cleanerHashMap.put(cleaningDevice.getUuid(), cleaningDevice);
        cleanerRepository.save(cleaningDevice);
        return cleaningDevice.getUuid();
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
        CleaningDevice cleaningDevice = cleanerHashMap.get(cleaningDeviceId);
        Space destinationSpace;
        Space currentSpace = spaceHashMap.get(cleaningDevice.getSpaceId());
        Boolean operationSuccess = false;

        switch (command.getCommandType())
        {
            case TRANSPORT:
                destinationSpace = spaceHashMap.get(command.getGridId());
                operationSuccess = cleaningDevice.transport(currentSpace, destinationSpace, connectionHashMap);
                break;
            case ENTER:
                destinationSpace = spaceHashMap.get(command.getGridId());
                operationSuccess = cleaningDevice.enterSpace(destinationSpace);
                break;
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                operationSuccess = cleaningDevice.move(currentSpace, command.getCommandType(), command.getNumberOfSteps());
                break;

            default:
                throw new UnsupportedOperationException("This is not a valid command");
        }
        cleanerRepository.deleteById(cleaningDevice.getUuid());
        cleanerRepository.save(cleaningDevice);
        return operationSuccess;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        CleaningDevice cleaningDevice = cleanerHashMap.get(cleaningDeviceId);
        return cleaningDevice.getSpaceId();
    }

    /**
     * This method returns the vector-2D a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector-2D of the cleaning device
     */
    public Vector2D getCleaningDeviceVector2D(UUID cleaningDeviceId){
        CleaningDevice cleaningDevice = cleanerHashMap.get(cleaningDeviceId);
        return cleaningDevice.getVector2D();
    }

    public String getCoordinates(UUID cleaningDeviceId){
        CleaningDevice cleaningDevice = cleanerHashMap.get(cleaningDeviceId);
        return "(" + cleaningDevice.getX() + "," + cleaningDevice.getY() + ")";
    }

    public void printSpace(UUID space)
    {
        Space currentSpace = spaceHashMap.get(space);
        currentSpace.printField();
    }
}
