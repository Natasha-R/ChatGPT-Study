package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.repository.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.repository.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.repository.TransportSystemRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    TransportSystemRepository transportSystemRepository;

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = CleaningDevice.fromShit(name);
        cleaningDeviceRepository.save(cleaningDevice);
        return cleaningDevice.getId();
    }

    public void addCleaningDevice(CleaningDevice cleaningDevice){
        cleaningDeviceRepository.save(cleaningDevice);
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
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().executeCommand(task, spaceRepository);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId();
    }

    /**
     * This method returns the coordinate a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinate of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
    }

    public List<CleaningDevice> getAllCleaningDevices(){
        List<CleaningDevice> deviceList = cleaningDeviceRepository.findAll();
//        List<CleaningDevice> iterable = ;
//        for (CleaningDevice device : iterable){
//            deviceList.add(device);
//        }
        return deviceList;
    }

    public CleaningDevice getCleaningDevice(String id){
        return cleaningDeviceRepository.findById(UUID.fromString(id)).get();
    }

    public CleaningDevice changeName(String id, CleaningDevice givenDevice){
        CleaningDevice device = cleaningDeviceRepository.findById(UUID.fromString(id)).get();
        device.changeName(givenDevice.getName());
        return device;
    }

    public void deleteCleaningDevice(String id){
        cleaningDeviceRepository.deleteById(UUID.fromString(id));
    }

    public void deleteCleaningDevices(){
        cleaningDeviceRepository.deleteAll();
    }

    public void deleteTasks(String id){
        cleaningDeviceRepository.findById(UUID.fromString(id)).get().deleteTasks();
    }

    public List<Task> getAllTasks(String id){
        return cleaningDeviceRepository.findById(UUID.fromString(id)).get().getAllTasks();
    }
}
