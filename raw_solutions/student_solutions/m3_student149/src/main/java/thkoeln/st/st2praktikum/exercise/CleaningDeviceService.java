package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.environment.Environment;
import thkoeln.st.st2praktikum.exercise.environment.Space;
import thkoeln.st.st2praktikum.exercise.environment.transition.BlockedTransition;
import thkoeln.st.st2praktikum.exercise.environment.transition.ConnectedTransition;
import thkoeln.st.st2praktikum.exercise.environment.transition.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.persistence.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class CleaningDeviceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    private ConnectedTransitionRepository connectedTransitionRepository;

    @Autowired
    private BlockedTransitionRepository blockedTransitionRepository;

    private final Environment environment;

    public CleaningDeviceService() {
        environment = new Environment(this);
    }

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = environment.addSpace(height, width);
        return spaceRepository.save(space).getId();
    }

    /**
     * This method adds a barrier to a given space.
     * @param spaceId the ID of the space the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceId, Barrier barrier) {
        Space space = getSpace(spaceId);
        List<BlockedTransition> blockedTransitions = barrier.getMyBarrier().getBlockedTransitions();
        Iterable<BlockedTransition> saved = blockedTransitionRepository.saveAll(blockedTransitions);

        blockedTransitions.clear();
        saved.forEach(blockedTransitions::add);
        space.addBlockedTransitions(blockedTransitions);

        spaceRepository.save(space);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = environment.addTransportTechnology(technology);
        return transportTechnologyRepository.save(transportTechnology).getId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceId, Vector2D sourceVector2D, UUID destinationSpaceId, Vector2D destinationVector2D) {
        ConnectedTransition connectedTransition = environment.addConnection(transportTechnologyId, getSpace(sourceSpaceId), sourceVector2D,
                getSpace(destinationSpaceId), destinationVector2D);

        return connectedTransitionRepository.save(connectedTransition).getId();
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = environment.addCleaningDevice(name);
        return cleaningDeviceRepository.save(cleaningDevice).getId();
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
    public Boolean executeCommand(UUID cleaningDeviceId, Command command) {
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

    public List<CleaningDevice> getCleaningDevice() {
        ArrayList<CleaningDevice> cleaningDevices = new ArrayList<>();
        cleaningDeviceRepository.findAll().forEach(cleaningDevice -> cleaningDevices.add(cleaningDevice));

        return cleaningDevices;
    }

    public CleaningDevice getCleaningDevice(UUID cleaningDeviceId) {
        CleaningDevice cleaningDevice = cleaningDeviceRepository.findById(cleaningDeviceId).get();
        cleaningDevice.init(environment);

        return cleaningDevice;
    }

    public void updateCleaningDevice(CleaningDevice cleaningDevice) {
        cleaningDeviceRepository.save(cleaningDevice);
    }

    public Space getSpace(UUID spaceId) {
        return spaceRepository.findById(spaceId).get();
    }

    public TransportTechnology getTransportTechnology(UUID transportTechnologyId) {
        return transportTechnologyRepository.findById(transportTechnologyId).get();
    }

    public List<ConnectedTransition> getConnectedTransitions() {
        ArrayList<ConnectedTransition> connectedTransitions = new ArrayList<>();
        connectedTransitionRepository.findAll().forEach(connectedTransition -> connectedTransitions.add(connectedTransition));

        return connectedTransitions;
    }
}
