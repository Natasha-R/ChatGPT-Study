package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CleaningDeviceService {

    World world = new World();

    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private ConnectorRepository connectorRepository;

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height,width);
        world.addSpace(space);
        spaceRepository.save(space);
        return space.getId();
    }

    /**
     * This method adds a wall to a given space.
     * @param spaceId the ID of the space the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceId, Wall wall) {
        world.addWalltoSpace(spaceId, wall);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        world.addTransportTechnology(transportTechnology);
        return transportTechnology.getTechnologyId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceSpaceId, Point sourcePoint, UUID destinationSpaceId, Point destinationPoint) {
        Connector connector = new Connector(sourcePoint,destinationSpaceId,destinationPoint);
        world.getTechnology(transportTechnologyId).addConnector(connector);
        world.addConnectors(sourceSpaceId,connector);
        connectorRepository.save(connector);
        return connector.getId();
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaner = new CleaningDevice(name);
        world.addCleaningDevice(cleaner);
        cleaningDeviceRepository.save(cleaner);
        return cleaner.getId();
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
        boolean result = world.sendcommand(cleaningDeviceId,command);
        try{
            cleaningDeviceRepository.save(world.getcleaningdevice(cleaningDeviceId));
        } catch (CleaningDeviceNotFoundExeption cleaningDeviceNotFoundExeption) {
            cleaningDeviceNotFoundExeption.printStackTrace();
        }
        return result;
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        try {
            return world.getcleaningspaceid(cleaningDeviceId);
        }catch(SpaceNotFoundExeption e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the point a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the point of the cleaning device
     */
    public Point getCleaningDevicePoint(UUID cleaningDeviceId){
        try{
            return world.getcleaningdevice(cleaningDeviceId).getPoint();
        }catch (CleaningDeviceNotFoundExeption e){
            e.printStackTrace();
            return null;
        }
    }
}
