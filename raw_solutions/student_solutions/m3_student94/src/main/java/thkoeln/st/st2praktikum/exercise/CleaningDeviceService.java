package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;


@Service
public class CleaningDeviceService {
    @Autowired
    Execution execution= new Execution();

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    TransportCategoryRepository transportCategoryRepository;

    @Autowired
    ConnectionRepository connectionRepository;

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space= new Space(height,width);
        spaceRepository.save(space);
        return space.getSpaceId();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        Space actualSpace= spaceRepository.findById(spaceId).get();

        if (getSpaceShip(spaceId).getHeight() <= obstacle.getStart().getX() || getSpaceShip(spaceId).getWidth() <=obstacle.getStart().getY())
            throw new RuntimeException();
        else if (getSpaceShip(spaceId).getHeight() <= obstacle.getEnd().getX() || getSpaceShip(spaceId).getWidth() <= obstacle.getEnd().getY())
            throw new RuntimeException();
        else
            actualSpace.addObstacle(obstacle);



    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory= new TransportCategory(category);
        transportCategoryRepository.save(transportCategory);
        return transportCategory.getTransportCategoryId();
    }

    /**
     * This method adds a traversable connection between two spaces based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        TransportCategory transportCategory= transportCategoryRepository.findById(transportCategoryId).get();
        Connection connection= new Connection(transportCategory,sourceSpaceId,sourceCoordinate,destinationSpaceId,destinationCoordinate);

        if (getSpaceShip(sourceSpaceId).getHeight() <= sourceCoordinate.getX() || getSpaceShip(sourceSpaceId).getWidth() <= sourceCoordinate.getY())
            throw new RuntimeException();
        else if (getSpaceShip(destinationSpaceId).getHeight()<= destinationCoordinate.getX() || getSpaceShip(destinationSpaceId).getWidth() <= destinationCoordinate.getY())
            throw new RuntimeException();
        else
            connectionRepository.save(connection);
           return connection.getConnectionId();

    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);
        cleaningDeviceRepository.save(cleaningDevice);
        return cleaningDevice.getCleaningDeviceId();

    }

    /**
     * This method lets the cleaning device execute a order.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {
        switch (order.getOrderType()){
            case TRANSPORT:return execution.TransportCommand(order,cleaningDeviceId);
            case ENTER: return execution.InitialCommand(order,cleaningDeviceId);
            case EAST:
                execution.calculateBlockPositionEast(order,cleaningDeviceId);
                return true;
            case NORTH:
                execution.calculateBlockPositionNorth(order,cleaningDeviceId);
                return true;
            case SOUTH:
                execution.calculateBlockPositionSouth(order,cleaningDeviceId);
                return true;
            case WEST:
                execution.calculateBlockPositionWest(order,cleaningDeviceId);
                return true;
            default:return false;
        }

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
     * This method returns the coordinate a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinate of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
    }

    public Space getSpaceShip(UUID spaceId){
        return spaceRepository.findById(spaceId).get();
    }
}
