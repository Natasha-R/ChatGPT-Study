package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.*;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.device.WalkableCleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services.IWalkService;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services.SetInitialSpaceService;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services.TransportService;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.services.WalkService;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space.ISpaceService;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.space.SpaceService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;


import java.util.Optional;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    private final IWalkService walkService = new WalkService();
    private final TransportService transportService;
    private final SetInitialSpaceService setInitialSpaceService = new SetInitialSpaceService();

    private final CleaningDeviceRepository deviceRepo;
    private final SpaceRepository spaceRepository;

    @Autowired
    public CleaningDeviceService(CleaningDeviceRepository deviceRepo,
                                 SpaceRepository spaceRepository,
                                 TransportTechnologyRepository transportTechnologyRepository,
                                 TransportService transportService) {
        this.deviceRepo = deviceRepo;
        this.spaceRepository = spaceRepository;
        this.transportService = transportService;
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        final CleaningDevice entity = new CleaningDevice();
        entity.setId(UUID.randomUUID());
        entity.setName(name);

        deviceRepo.save(entity);
        return entity.getId();
    }

    /**
     * This method lets the cleaning device execute a command.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a barrier or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) {
        final Optional<CleaningDevice> device = deviceRepo.findById(cleaningDeviceId);
        if (device.isEmpty())
            throw new IllegalArgumentException("no device found!");

        var entity = device.get();
        entity.getCommands().add(command);

        entity = deviceRepo.save(entity);

        final WalkableCleaningDevice cleaningDevice = new WalkableCleaningDevice(entity, deviceRepo, spaceRepository);

        switch (command.getCommandType()) {
            case EAST:
            case WEST:
            case NORTH:
            case SOUTH:
                return walkService.walk(command.getCommandType(), command.getNumberOfSteps(), cleaningDevice, cleaningDevice.getSpace());
            case TRANSPORT:
                // todo why do we need the destination space id? it was already provided in the addConnection method
                return transportService.transport(cleaningDevice);
            case ENTER:
                final Space spaceEntity = getSpace(command.getGridId());
                final ISpaceService space = new SpaceService(spaceEntity, spaceRepository, deviceRepo);
                return setInitialSpaceService.setSpace(space, cleaningDevice);
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        final Optional<CleaningDevice> device = deviceRepo.findById(cleaningDeviceId);

        if (device.isEmpty())
            throw new IllegalArgumentException("no device found with given id");

        return device.get().getSpace().getId();
    }

    /**
     * This method returns the point a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the point of the cleaning device
     */
    public Point getCleaningDevicePoint(UUID cleaningDeviceId){
        throw new UnsupportedOperationException();
    }

    private Space getSpace(UUID spaceId) {
        final Optional<Space> spaceEntity = spaceRepository.getById(spaceId);
        return spaceEntity.orElseThrow(null);
    }
}
