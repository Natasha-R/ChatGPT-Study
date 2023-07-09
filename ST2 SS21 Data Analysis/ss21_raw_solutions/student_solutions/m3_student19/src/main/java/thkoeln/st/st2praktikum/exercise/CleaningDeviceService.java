package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportCategoryRepository;

import java.util.UUID;


@Service
public class CleaningDeviceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space newSpace = new Space(height, width);
        this.spaceRepository.save(newSpace);
        return newSpace.getId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        if (this.spaceRepository.findById(spaceId).isEmpty())
            throw new IllegalArgumentException("There is no space existing with the given spaceId: " + spaceId);

        this.spaceRepository.findById(spaceId).get().setWall(wall);
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory newCategory = new TransportCategory(category);
        this.transportCategoryRepository.save(newCategory);
        return newCategory.getId();
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
        if (this.transportCategoryRepository.findById(transportCategoryId).isEmpty())
            throw new IllegalArgumentException("There is no transportCategory existing with the given transportCategoryId: " + transportCategoryId);
        if (this.spaceRepository.findById(sourceSpaceId).isEmpty())
            throw new IllegalArgumentException("There is no space existing with the given sourceSpaceId: " + sourceSpaceId);
        if (this.spaceRepository.findById(destinationSpaceId).isEmpty())
            throw new IllegalArgumentException("There is no space existing with the given destinationSpaceId: " + destinationSpaceId);
        if (this.spaceRepository.findById(sourceSpaceId).get().getSpaceHeight() < sourceVector2D.getY() ||
            this.spaceRepository.findById(sourceSpaceId).get().getSpaceWidth() < sourceVector2D.getX() ||
            this.spaceRepository.findById(destinationSpaceId).get().getSpaceHeight() < destinationVector2D.getY() ||
            this.spaceRepository.findById(destinationSpaceId).get().getSpaceWidth() < destinationVector2D.getX())
            throw new IllegalArgumentException("Given Vectors are out of bounds for the given Spaces.");

        Connection newConnection = new Connection(sourceSpaceId, sourceVector2D, destinationSpaceId, destinationVector2D);
        this.connectionRepository.save(newConnection);
        this.transportCategoryRepository.findById(transportCategoryId).get().addConnection(newConnection);
        return newConnection.getId();
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice newCleaningDevice = new CleaningDevice(name);
        this.cleaningDeviceRepository.save(newCleaningDevice);
        return newCleaningDevice.getId();
    }

    /**
     * This method lets the cleaning device execute a task.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        if (this.cleaningDeviceRepository.findById(cleaningDeviceId).isEmpty())
            throw new IllegalArgumentException("The given cleaning device doesn't exist: " + cleaningDeviceId);

        CleaningDevice device = this.cleaningDeviceRepository.findById(cleaningDeviceId).get();
        Space space = null;
        if (device.getSpaceId() != null) {
            space = this.spaceRepository.findById(device.getSpaceId()).get();
        }

        var success = false;

        switch (task.getTaskType()) {
            case NORTH:
                success = device.moveNorth(space, task.getNumberOfSteps(), this.cleaningDeviceRepository);
                break;
            case WEST:
                success = device.moveWest(space, task.getNumberOfSteps(), this.cleaningDeviceRepository);
                break;
            case SOUTH:
                success = device.moveSouth(space, task.getNumberOfSteps(), this.cleaningDeviceRepository);
                break;
            case EAST:
                success = device.moveEast(space, task.getNumberOfSteps(), this.cleaningDeviceRepository);
                break;
            case TRANSPORT:
                success = device.traverseDevice(this.transportCategoryRepository, this.cleaningDeviceRepository, task.getGridId());
                break;
            case ENTER:
                success = device.placeDevice(this.spaceRepository, this.cleaningDeviceRepository, task.getGridId());
                break;
        }

        return success;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        if (this.cleaningDeviceRepository.findById(cleaningDeviceId).isEmpty())
            throw new IllegalArgumentException("The given cleaning device doesn't exist: " + cleaningDeviceId);
        if (this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId() == null)
            throw new IllegalArgumentException("The given device hasn't been placed: " + cleaningDeviceId);

        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId();
    }

    /**
     * This method returns the vector-2D a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector-2D of the cleaning device
     */
    public Vector2D getCleaningDeviceVector2D(UUID cleaningDeviceId){
        if (this.cleaningDeviceRepository.findById(cleaningDeviceId).isEmpty())
            throw new IllegalArgumentException("The given cleaning device doesn't exist: " + cleaningDeviceId);

        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getVector2D();
    }
}
