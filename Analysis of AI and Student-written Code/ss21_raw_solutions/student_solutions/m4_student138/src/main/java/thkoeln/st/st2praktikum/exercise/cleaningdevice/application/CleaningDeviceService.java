package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleanerRepository;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    @Autowired
    CleanerRepository cleanerRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    TransportCategoryRepository transportCategoryRepository;

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
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
        CleaningDevice cleaningDevice = cleanerRepository.findById(cleaningDeviceId).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        Space destinationSpace;
        Space currentSpace;
        Boolean operationSuccess = false;

        switch (command.getCommandType())
        {
            case TRANSPORT:
                currentSpace = spaceRepository.findById(cleaningDevice.getSpaceId()).orElseThrow(() -> new NullPointerException("There is no object with this id"));
                destinationSpace = spaceRepository.findById(command.getGridId()).orElseThrow(() -> new NullPointerException("There is no object with this id"));
                operationSuccess = cleaningDevice.transport(currentSpace, destinationSpace);
                break;
            case ENTER:
                destinationSpace = spaceRepository.findById(command.getGridId()).orElseThrow(() -> new NullPointerException("There is no object with this id"));
                operationSuccess = cleaningDevice.enterSpace(destinationSpace);
                break;
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                currentSpace = spaceRepository.findById(cleaningDevice.getSpaceId()).orElseThrow(() -> new NullPointerException("There is no object with this id"));
                operationSuccess = cleaningDevice.move(currentSpace, command.getCommandType(), command.getNumberOfSteps());
                break;

            default:
                throw new UnsupportedOperationException("This is not a valid command");
        }
        //cleanerRepository.deleteById(cleaningDevice.getUuid());
        cleaningDevice.addCommand(command);
        cleanerRepository.save(cleaningDevice);
        return operationSuccess;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        CleaningDevice cleaningDevice = cleanerRepository.findById(cleaningDeviceId).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        return cleaningDevice.getSpaceId();
    }

    /**
     * This method returns the vector-2D a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector-2D of the cleaning device
     */
    public Vector2D getCleaningDeviceVector2D(UUID cleaningDeviceId){
        CleaningDevice cleaningDevice = cleanerRepository.findById(cleaningDeviceId).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        return cleaningDevice.getVector2D();
    }

    public List<Command> getCommandList(UUID id) {
        CleaningDevice cleaningDevice = cleanerRepository.findById(id).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        return cleaningDevice.getCommandsList();
    }

    public Iterable<CleaningDevice> findAllCleaningDevices() {
        Iterable<CleaningDevice> cleaningDevices = cleanerRepository.findAll();
        return cleaningDevices;
    }

    public CleaningDevice getCleaningDeviceById(UUID id) {
        CleaningDevice cleaningDevice = cleanerRepository.findById(id).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        return cleaningDevice;
    }

    public CleaningDevice createCleaningDevice(CleaningDevice cleaningDevice) {
        CleaningDevice newCleaningDevice = new CleaningDevice(cleaningDevice.getName());
        cleanerRepository.save(newCleaningDevice);

        return newCleaningDevice;
    }

    public boolean deleteById(UUID id) {
        try {
            cleanerRepository.findById(id).orElseThrow(() -> new NullPointerException("There is no object with this id"));
            cleanerRepository.deleteById(id);
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public void changeName(UUID id, String name) {
        CleaningDevice cleaningDevice = cleanerRepository.findById(id).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        cleaningDevice.setName(name);
        cleanerRepository.save(cleaningDevice);
    }

    public void deleteCommands(UUID id) {
        CleaningDevice cleaningDevice = cleanerRepository.findById(id).orElseThrow(() -> new NullPointerException("There is no object with this id"));
        cleaningDevice.clearCommandHistory();
    }
}
