package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.repositories.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.repositories.SpaceRepository;
import thkoeln.st.st2praktikum.repositories.TransportSystemRepository;

import java.util.Optional;
import java.util.UUID;


@Service
public class CleaningDeviceService {


    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    TransportSystemRepository transportSystemRepository;
    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = Space.fromShit(height, width);
        spaceRepository.save(space);
        return space.getId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        if(spaceRepository.findById(spaceId).isPresent()){
            Space space = spaceRepository.findById(spaceId).get();
            space.addWall(wall);
            spaceRepository.save(space);
        }
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = TransportSystem.fromShit(system);
        transportSystemRepository.save(transportSystem);
        return transportSystem.getId();
    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        Optional<Space> sourceSpace = spaceRepository.findById(sourceSpaceId);
        if(sourceSpace.isPresent()){
            Connection connection = Connection.fromShit(transportSystemId, sourceSpaceId, destinationSpaceId, sourceCoordinate, destinationCoordinate, spaceRepository);
            sourceSpace.get().addConnection(connection);
            return connection.getId();
        } else {
            throw new IllegalArgumentException("Source Space does not exist");
        }
    }

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

    /**
     * This method lets the cleaning device execute a task.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another space
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
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinates of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
    }
}
