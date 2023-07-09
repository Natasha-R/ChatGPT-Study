package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.device.WalkableCleaningDevice;
import thkoeln.st.st2praktikum.exercise.entities.*;
import thkoeln.st.st2praktikum.exercise.repositories.*;
import thkoeln.st.st2praktikum.exercise.services.IWalkService;
import thkoeln.st.st2praktikum.exercise.services.SetInitialSpaceService;
import thkoeln.st.st2praktikum.exercise.services.TransportService;
import thkoeln.st.st2praktikum.exercise.services.WalkService;
import thkoeln.st.st2praktikum.exercise.space.ISpaceService;
import thkoeln.st.st2praktikum.exercise.space.SpaceService;

import java.util.Optional;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    private final IWalkService walkService = new WalkService();
    private final TransportService transportService;
    private final SetInitialSpaceService setInitialSpaceService = new SetInitialSpaceService();

    private final CleaningDeviceRepository deviceRepo;
    private final SpaceRepository spaceRepository;
    //private final BarrierRepository barrierRepository;
    //private final SpaceConnectionRepository spaceConnectionRepository;
    private final TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    public CleaningDeviceService(CleaningDeviceRepository deviceRepo,
                                 SpaceRepository spaceRepository,
                                 //BarrierRepository barrierRepository,
                                 //SpaceConnectionRepository spaceConnectionRepository,
                                 TransportTechnologyRepository transportTechnologyRepository,
                                 TransportService transportService) {
        this.deviceRepo = deviceRepo;
        this.spaceRepository = spaceRepository;
        //this.barrierRepository = barrierRepository;
        //this.spaceConnectionRepository = spaceConnectionRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.transportService = transportService;
    }

    /**
     * This method creates a new space.
     *
     * @param height the height of the space
     * @param width  the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        final Space spaceEntity = new Space();
        spaceEntity.setWidth(width);
        spaceEntity.setHeight(height);
        spaceEntity.setId(UUID.randomUUID());

        spaceRepository.save(spaceEntity);
        return spaceEntity.getId();
    }

    /**
     * This method adds a barrier to a given space.
     *
     * @param spaceId the ID of the space the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceId, Barrier barrier) {
        final Space space = getSpace(spaceId);
        if (barrier.getStart().getX() > space.getWidth() ||
                barrier.getEnd().getX() > space.getWidth() ||
                barrier.getStart().getY() > space.getHeight() ||
                barrier.getEnd().getY() > space.getHeight()
        ) {
            throw new IllegalArgumentException("barrier is out of bound!");
        }

        /*final Barrier barrierEntity = barrierRepository.save(new Barrier(
                barrier.getStart(),
                barrier.getEnd()
        ));*/

        space.getBarriers().add(new Barrier(
                barrier.getStart(),
                barrier.getEnd()
        ));
        spaceRepository.save(space);
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        final Optional<TransportTechnology> technologyEntity = transportTechnologyRepository.findByTechnology(technology);

        if (technologyEntity.isPresent())
            return technologyEntity.get().getId();

        final TransportTechnology t = transportTechnologyRepository.save(new TransportTechnology(UUID.randomUUID(), technology));
        return t.getId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport technology. Connections only work in one direction.
     *
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceId         the ID of the space where the entry point of the connection is located
     * @param sourcePoint           the point of the entry point
     * @param destinationSpaceId    the ID of the space where the exit point of the connection is located
     * @param destinationPoint      the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceId, Point sourcePoint, UUID destinationSpaceId, Point destinationPoint) {
        final Optional<TransportTechnology> technologyEntityOptional = transportTechnologyRepository.findById(transportTechnologyId);

        final Space source = getSpace(sourceSpaceId);
        final Space destination = getSpace(destinationSpaceId);

        if (sourcePoint.getX() > source.getWidth() || sourcePoint.getY() > source.getHeight())
            throw new IllegalArgumentException("sourcePoint is out of bounds!");

        if (destinationPoint.getX() > destination.getWidth() || destinationPoint.getY() > destination.getHeight())
            throw new IllegalArgumentException("destinationPoint is out of bounds!");

        // final SpaceConnection connection = new SpaceConnection(UUID.randomUUID(), source, sourcePoint, destination, destinationPoint);
        final SpaceConnection spaceConnectionEntity = new SpaceConnection(
                UUID.randomUUID(),
                technologyEntityOptional.get(),
                source,
                destination,
                sourcePoint,
                destinationPoint
        );

        //spaceConnectionRepository.save(spaceConnectionEntity);

        source.getSpaceConnections().add(spaceConnectionEntity);
        destination.getSpaceConnections().add(spaceConnectionEntity);

        spaceRepository.save(source);
        spaceRepository.save(destination);

        return spaceConnectionEntity.getId();
    }

    /**
     * This method adds a new cleaning device
     *
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
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @param command          the given command
     *                         NORTH, WEST, SOUTH, EAST for movement
     *                         TRANSPORT for transport - only works on grid cells with a connection to another space
     *                         ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the cleaning device hits a barrier or
     * another cleaning device, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
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
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId) {
        final Optional<CleaningDevice> device = deviceRepo.findById(cleaningDeviceId);

        if (device.isEmpty())
            throw new IllegalArgumentException("no device found with given id");

        return device.get().getSpace().getId();
    }

    /**
     * This method returns the point a cleaning device is standing on
     *
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the point of the cleaning device
     */
    public Point getCleaningDevicePoint(UUID cleaningDeviceId) {
        final Optional<CleaningDevice> entity = deviceRepo.findById(cleaningDeviceId);
        if (entity.isEmpty())
            return null;

        return entity.get().getPoint();
    }

    private Space getSpace(UUID spaceId) {
        final Optional<Space> spaceEntity = spaceRepository.getById(spaceId);
        return spaceEntity.orElseThrow(null);
    }
}
