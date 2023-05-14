package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceException;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Tile;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.*;


@Service
public class CleaningDeviceService {

    private CleaningDeviceRepository cleaningDeviceRepo;
    private SpaceRepository spaceRepo;
    private TransportCategoryRepository transportCategoryRepo;

    public CleaningDevice findById( UUID id ) {
        CleaningDevice device = cleaningDeviceRepo.findById( id ).orElseThrow( () ->
                new RuntimeException( "CleaningDevice " + id + " existiert nicht") );
        return device;
    }

    public void deleteById( UUID id ) {
        cleaningDeviceRepo.deleteById(id);
    }

    public Iterable<CleaningDevice> findAll () {
        return cleaningDeviceRepo.findAll();
    }

    public Space findSpaceById(UUID id ) {
        Space space = spaceRepo.findById( id ).orElseThrow( () ->
                new RuntimeException( "Space " + id + " existiert nicht") );
        return space;
    }

    public List<TransportCategory> findAllTransportCategories () {
        List<TransportCategory> transportCategoryList = new ArrayList<TransportCategory>();
        transportCategoryRepo.findAll().forEach(transportCategoryList::add);
        return transportCategoryList;
    }

    @Autowired
    public CleaningDeviceService (
            SpaceRepository spaceRepo,
            TransportCategoryRepository transportCategoryRepo,
            CleaningDeviceRepository cleaningDeviceRepo) {
        this.spaceRepo = spaceRepo;
        this.transportCategoryRepo = transportCategoryRepo;
        this.cleaningDeviceRepo = cleaningDeviceRepo;
    }


    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        UUID connectionId = cleaningDevice.getId();
        cleaningDeviceRepo.save(cleaningDevice);
        return connectionId;
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
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Task task) {
        CleaningDevice cleaningDevice = findById(cleaningDeviceId);
        return cleaningDevice.executeTask(task, this);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        CleaningDevice device = findById(cleaningDeviceId);
        Space space = device.getSpace();
        if (space == null) throw new RuntimeException(
                "CleaningDevice  " + cleaningDeviceId.toString() + " steht in keinem Space");
        return space.getId();
    }

    /**
     * This method returns the vector2Ds a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector2Ds of the cleaning device
     */
    public Vector2D getVector2D(UUID cleaningDeviceId){
        return findById(cleaningDeviceId).getPosition();
    }

}
