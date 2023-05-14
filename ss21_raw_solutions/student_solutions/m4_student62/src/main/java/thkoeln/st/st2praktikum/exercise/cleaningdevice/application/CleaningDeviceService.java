package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH.World;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import java.util.UUID;


@Service
public class CleaningDeviceService {

    private CleaningDeviceController cleaningDeviceController;

    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;

    public CleaningDeviceController getCleaningDeviceController(){
        if(cleaningDeviceController==null)
        {
            cleaningDeviceController = new CleaningDeviceController(cleaningDeviceRepository);
        }
           return cleaningDeviceController;
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        return getCleaningDeviceController().addCleaningdevice(name);
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a wall or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) {
       return getCleaningDeviceController().executeCommand(cleaningDeviceId,command);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return getCleaningDeviceController().findCleaningDevice(cleaningDeviceId).getSpaceId();
    }

    /**
     * This method returns the point a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the point of the cleaning device
     */
    public Point getCleaningDevicePoint(UUID cleaningDeviceId){
        return getCleaningDeviceController().findCleaningDevice(cleaningDeviceId).getPosition();
    }
}
