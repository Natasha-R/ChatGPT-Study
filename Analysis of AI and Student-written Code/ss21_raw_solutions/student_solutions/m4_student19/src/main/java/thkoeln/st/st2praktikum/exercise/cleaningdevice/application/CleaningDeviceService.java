package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.repositories.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.repositories.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportCategoryRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

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
        device.addTask(task);

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

    public Iterable<CleaningDevice> getAllDevices() {
        return this.cleaningDeviceRepository.findAll();
    }

    public Optional<CleaningDevice> getCleaningDevice(UUID cleaningDeviceId) {
        return this.cleaningDeviceRepository.findById(cleaningDeviceId);
    }

    public void deleteCleaningDevice(CleaningDevice cleaningDevice) {
        this.cleaningDeviceRepository.delete(cleaningDevice);
    }

    public Boolean addTaskToCleaningDevice(UUID cleaningDeviceId, Task newTask) {
        CleaningDevice cleaningDevice = this.cleaningDeviceRepository.findById(cleaningDeviceId).get();
        cleaningDevice.addTask(newTask);
        return executeCommand(cleaningDeviceId, newTask);
    }

}
