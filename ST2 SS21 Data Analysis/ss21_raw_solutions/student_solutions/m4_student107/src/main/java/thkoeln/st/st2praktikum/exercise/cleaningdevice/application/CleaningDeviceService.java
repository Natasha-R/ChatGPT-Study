package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryRepository;


import java.util.UUID;


@Service
public class CleaningDeviceService {

    private final CleaningDeviceRepository cleaningDeviceRepository;
    private final ConnectionRepository connectionRepository;
    private final SpaceRepository spaceRepository;
    private final TransportCategoryRepository transportCategoryRepository;

    @Autowired
    CleaningDeviceService(CleaningDeviceRepository cleaningDeviceRepository,
                          ConnectionRepository connectionRepository,
                          SpaceRepository spaceRepository,
                          TransportCategoryRepository transportCategoryRepository) {
        this.cleaningDeviceRepository = cleaningDeviceRepository;
        this.connectionRepository = connectionRepository;
        this.spaceRepository = spaceRepository;
        this.transportCategoryRepository = transportCategoryRepository;
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);

        return this.cleaningDeviceRepository.save(cleaningDevice).getId();
    }

    /**
     * This method lets the cleaning device execute a order.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {
        CleaningDevice cleaningDevice = this.cleaningDeviceRepository.findById(cleaningDeviceId).get();

        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().command(order,this.connectionRepository , this.spaceRepository);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId();
    }

    /**
     * This method returns the coordinate a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinate of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
    }
}

