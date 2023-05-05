package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Service
public class CleaningDeviceService {
    @Autowired
    TransportCategoryService transportCategoryService;

    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    SpaceRepository spaceRepository;

    public CleaningDevice findById(UUID id)
    {
        return this.cleaningDeviceRepository.findById(id).orElseThrow();
    }

    public Iterable<CleaningDevice> findAll()
    {
        return this.cleaningDeviceRepository.findAll();
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
     * TRANSPORT for transport - only works on grid cells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        CleaningDevice someCleaningDevice = this.getCleaningDeviceById(cleaningDeviceId);
        someCleaningDevice.getTasks().add(task);

        if(task.getTaskType().equals(TaskType.ENTER))
        {
            Space enterSpace = this.spaceRepository.findById(task.getGridId()).orElseThrow();

            if(!this.canCleaningDeviceEnterSpace(enterSpace))
            {
                return false;
            }

            someCleaningDevice.setPosition(new Point(0, 0));
            someCleaningDevice.setSpace(enterSpace);

            this.cleaningDeviceRepository.save(someCleaningDevice);

            return true;
        }


        if(task.getTaskType().equals(TaskType.TRANSPORT))
        {
            Iterator<Connection> connectionIterator = this.transportCategoryService.getConnectionByStartSpaceId(someCleaningDevice.getSpace().getId());
            Connection currentConnection;

            while(connectionIterator.hasNext())
            {
                currentConnection = connectionIterator.next();

                if(currentConnection.getStartPoint().equals(someCleaningDevice.getPoint()))
                {
                    System.out.println("Moving Robot " + someCleaningDevice.getName() + " from Space " + someCleaningDevice.getSpace().getId() + " to Space " + currentConnection.getEndSpaceId());

                    Space cleaningDeviceSpace = this.spaceRepository.findById(currentConnection.getEndSpaceId()).orElseThrow();
                    someCleaningDevice.setSpace(cleaningDeviceSpace);
                    someCleaningDevice.setPosition(currentConnection.getEndPoint());

                    this.cleaningDeviceRepository.save(someCleaningDevice);

                    return true;
                }
            }

            return false;
        }

        if(!someCleaningDevice.executeTask(task))
        {
            return false;
        }

        this.cleaningDeviceRepository.save(someCleaningDevice);

        return true;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        CleaningDevice someCleaningDevice = this.getCleaningDeviceById(cleaningDeviceId);

        return someCleaningDevice.getSpaceId();
    }

    /**
     * This method returns the point a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the point of the cleaning device
     */
    public Point getCleaningDevicePoint(UUID cleaningDeviceId) {

        return this.getCleaningDeviceById(cleaningDeviceId).getPoint();
    }

    private CleaningDevice getCleaningDeviceById(UUID cleaningDeviceId) {

        return this.cleaningDeviceRepository.findById(cleaningDeviceId).orElseThrow();
    }

    private boolean canCleaningDeviceEnterSpace(Space space)
    {
        List<CleaningDevice> cleaningDevicesOnTargetSpace = this.cleaningDeviceRepository.findBySpace(space);
        Iterator<CleaningDevice> cleaningDeviceIterator = cleaningDevicesOnTargetSpace.iterator();
        CleaningDevice someDevice;

        while(cleaningDeviceIterator.hasNext()) {
            someDevice = cleaningDeviceIterator.next();

            if(null == someDevice.getSpace() || !someDevice.getSpace().getId().equals(space.getId()))
            {
                continue;
            }

            if(!someDevice.getPosition().equals(new Point(0, 0)))
            {
                continue;
            }

            return false;
        }

        return true;
    }

    public void deleteById(UUID cleaningDeviceId)
    {
        this.cleaningDeviceRepository.deleteById(cleaningDeviceId);
    }

    public CleaningDevice createCleaningDeviceFromDto(CleaningDeviceDto cleaningDeviceDto)
    {
        CleaningDevice cleaningDevice = (new CleaningDeviceDtoMapper()).mapToEntity(cleaningDeviceDto);
        this.cleaningDeviceRepository.save(cleaningDevice);

        return cleaningDevice;
    }
}
