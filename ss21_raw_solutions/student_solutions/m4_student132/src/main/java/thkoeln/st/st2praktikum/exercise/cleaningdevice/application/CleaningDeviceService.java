package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;

import java.util.UUID;


@Service
public class CleaningDeviceService {

    private final CleaningDeviceRepository cleaningDeviceRepository;
    private final SpaceRepository spaceRepository;

    @Autowired
    public CleaningDeviceService(CleaningDeviceRepository cleaningDeviceRepository, SpaceRepository spaceRepository) {
        this.cleaningDeviceRepository = cleaningDeviceRepository;
        this.spaceRepository = spaceRepository;
    }

    /**
     * This method adds a new cleaning machine
     *
     * @param name the name of the cleaning machine
     * @return the UUID of the created cleaning machine
     */
    public UUID addCleaningDevice(String name) {
        final CleaningDevice cleaningDevice = new CleaningDevice(name);
        this.cleaningDeviceRepository.save(cleaningDevice);
        return cleaningDevice.getCleaningMachineId();
    }

    /**
     * This method lets the cleaning machine execute a order.
     * @param cleaningDeviceId the ID of the cleaning machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another space
     * ENTER for setting the initial space where a cleaning machine is placed. The cleaning machine will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning machine hits a obstacle or
     *      another cleaning machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().executeCommand(order, this.cleaningDeviceRepository, this.spaceRepository);
    }

    /**
     * This method returns the space-ID a cleaning machine is standing on
     * @param cleaningDeviceId the ID of the cleaning machine
     * @return the UUID of the space the cleaning machine is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId();
    }

    /**
     * This method returns the point a cleaning machine is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning machine
     * @return the point of the cleaning machine
     */
    public Coordinate getCleaningDevicePoint(UUID cleaningDeviceId) {
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
    }
}
