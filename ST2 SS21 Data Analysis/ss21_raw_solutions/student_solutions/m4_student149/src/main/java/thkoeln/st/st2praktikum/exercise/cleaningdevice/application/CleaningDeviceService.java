package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.NoSuchEntityException;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
public class CleaningDeviceService {

    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;

    private static Supplier<CleaningDeviceService> instance = () -> null;

    public static Supplier<CleaningDeviceService> getServiceSupplier() {
        return instance;
    }

    @PostConstruct
    public void init() {
        instance = () -> this;
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        return addCleaningDeviceReturnResult(name).getId();
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the created cleaning device
     */
    public CleaningDevice addCleaningDeviceReturnResult(String name) {
        return cleaningDeviceRepository.save(new CleaningDevice(name));
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a barrier or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) throws NoSuchEntityException {
        CleaningDevice cleaningDevice = getCleaningDevice(cleaningDeviceId);
        Boolean executed = cleaningDevice.executeCommand(command.getMyCommand());

        return executed;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return getCleaningDevice(cleaningDeviceId).getSpace().getId();
    }

    /**
     * This method returns the vector-2D a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the vector-2D of the cleaning device
     */
    public Vector2D getCleaningDeviceVector2D(UUID cleaningDeviceId){
        return getCleaningDevice(cleaningDeviceId).getVector2D();
    }

    public List<CleaningDevice> getCleaningDevices() {
        ArrayList<CleaningDevice> cleaningDevices = new ArrayList<>();
        cleaningDeviceRepository.findAll().forEach(cleaningDevice -> cleaningDevices.add(cleaningDevice));

        return cleaningDevices;
    }

    public CleaningDevice getCleaningDevice(UUID cleaningDeviceId) throws NoSuchEntityException {
        try {
            return cleaningDeviceRepository.findById(cleaningDeviceId).orElseThrow();
        } catch (NoSuchElementException ex) {
            throw new NoSuchEntityException(ex);
        }
    }

    public void deleteCleaningDevice(UUID cleaningDeviceId) {
        cleaningDeviceRepository.deleteById(cleaningDeviceId);
    }

    public CleaningDevice updateCleaningDevice(CleaningDevice cleaningDevice) {
        return cleaningDeviceRepository.save(cleaningDevice);
    }

    public List<Vector2D> getDeviceVector2Ds(Space space) {
        return getCleaningDevices().stream()
                .filter(cleaningDevice -> Objects.equals(cleaningDevice.getSpace(), space))
                .map(cleaningDevice -> cleaningDevice.getVector2D())
                .collect(Collectors.toList());
    }
}
