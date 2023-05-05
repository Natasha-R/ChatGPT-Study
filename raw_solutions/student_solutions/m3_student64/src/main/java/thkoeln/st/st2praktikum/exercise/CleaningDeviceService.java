package thkoeln.st.st2praktikum.exercise;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repository.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.repository.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.repository.TransportCategoryRepository;

import java.util.*;

@Service
public class CleaningDeviceService {
    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    TransportCategoryRepository transportCategoryRepository;

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
     * This method adds a transport category
     * @param categoryName the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String categoryName) {
        TransportCategory transportCategory = new TransportCategory(categoryName);
        this.transportCategoryRepository.save(transportCategory);

        return transportCategory.getId();
    }


    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        Space optionalFetchedSpace = this.spaceRepository.findById(spaceId).orElseThrow();

        optionalFetchedSpace.addObstacleToSpace(obstacle);
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourcePoint the points of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryUUID, UUID sourceSpaceId, Point sourcePoint, UUID destinationSpaceId, Point destinationPoint) {
        Space sourceSpace = this.spaceRepository.findById(sourceSpaceId).orElseThrow();
        Space destinationSpace = this.spaceRepository.findById(destinationSpaceId).orElseThrow();
        TransportCategory transportCategory = this.transportCategoryRepository.findById(transportCategoryUUID).orElseThrow();

        return sourceSpace.addConnectionToSpace(transportCategory, sourcePoint, destinationSpace, destinationPoint);
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        this.cleaningDeviceRepository.save(cleaningDevice);

        return cleaningDevice.getId();
    }

    /**
     * This method lets the cleaning device execute a task.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        IMovable cleaningDevice = this.cleaningDeviceRepository.findById(cleaningDeviceId).orElseThrow();

        // Als Sonderfall betrachten, da sonst zirkuläre Abh. bestehen würde
        if(task.getTaskType().equals(TaskType.ENTER))
        {
            UUID spaceId = task.getGridId();
            Space targetSpace = this.spaceRepository.findById(spaceId).orElseThrow();
            List<CleaningDevice> cleaningDevicesOnTargetSpace = this.cleaningDeviceRepository.findBySpace(targetSpace);
            Iterator<CleaningDevice> cleaningDeviceIterator = cleaningDevicesOnTargetSpace.iterator();
            IMovable someDevice;

            while(cleaningDeviceIterator.hasNext()) {
                someDevice = cleaningDeviceIterator.next();

                if(null == someDevice.getSpace() || !someDevice.getSpace().getId().equals(spaceId))
                {
                    continue;
                }

                if(!someDevice.getPosition().equals(new Point(0, 0)))
                {
                    continue;
                }

                return false;
            }

            Space enterSpace = this.spaceRepository.findById(spaceId).orElseThrow();
            cleaningDevice.setSpace(enterSpace);

            return true;
        }


        return cleaningDevice.executeTask(task);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).orElseThrow().getSpace().getId();
    }

    /**
     * This method returns the points a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the points of the cleaning device
     */
    public Point getPoint(UUID cleaningDeviceId){
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).orElseThrow().getPosition();
    }
}
