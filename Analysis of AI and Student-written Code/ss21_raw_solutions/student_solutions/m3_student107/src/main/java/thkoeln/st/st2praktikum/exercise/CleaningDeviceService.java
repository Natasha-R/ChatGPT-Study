package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CleaningDeviceService {

//    private HashMap<UUID,Space> world = new HashMap<UUID, Space>();
//    private HashMap<UUID,CleaningDevice> cleaningDeviceHashMap = new HashMap<UUID,CleaningDevice>() ;
//    private HashMap<UUID, Connection> connectionHashMap = new HashMap<UUID, Connection>();
//    private HashMap<UUID,TransportCategory> transportCategoryHashMap = new HashMap<UUID, TransportCategory>();


    private CleaningDeviceRepository cleaningDeviceRepository;
    private ConnectionRepository connectionRepository;
    private SpaceRepository spaceRepository;
    private TransportCategoryRepository transportCategoryRepository;

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
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height, width);

        return spaceRepository.save(space).getId();

//        this.world.put(space.getId(), space );
//        return space.getId();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        this.spaceRepository.findById(spaceId).get().addObstacle(obstacle);


        //this.world.get(spaceId).addObstacle(obstacle);
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transport = new TransportCategory(category);

        return this.transportCategoryRepository.save(transport).getId();
        //this.transportCategoryHashMap.put(transport.getId(),transport);
        //return transport.getId();
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
        Connection connection = new Connection(
                this.spaceRepository.findById(sourceSpaceId).get(),
                sourceCoordinate,this.spaceRepository.findById(destinationSpaceId).get()
                ,destinationCoordinate);


        this.transportCategoryRepository.findById(transportCategoryId).get().addConnection(connection);
        return this.connectionRepository.save(connection).getId();

//        this.connectionHashMap.put(connection.getId(),connection);
//        return connection.getId();
    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice(name);

        return this.cleaningDeviceRepository.save(cleaningDevice).getId();
        //cleaningDeviceHashMap.put(cleaningDevice.getId(),cleaningDevice);
        //return cleaningDevice.getId();
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
        //return cleaningDevice.command(order,this.connectionHashMap , this.spaceRepository);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId();

        //return this.cleaningDeviceHashMap.get(cleaningDeviceId).getSpaceID();
    }

    /**
     * This method returns the coordinate a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinate of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){

        return this.cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();
//        return this.cleaningDeviceHashMap.get(cleaningDeviceId).getCleaningDeviceCoordinate();


    }
}
