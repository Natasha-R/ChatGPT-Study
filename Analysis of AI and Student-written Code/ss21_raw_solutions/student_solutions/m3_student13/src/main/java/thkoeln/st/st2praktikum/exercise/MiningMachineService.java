package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Repositories.*;

import java.util.UUID;


@Service
public class MiningMachineService {
    //HashMap for the assignment of the object to its id
//    private HashMap<UUID,Field> uuidFieldHashMap = new HashMap<UUID,Field>();
//    private HashMap<UUID,MiningMachine> uuidMiningMachineHashMap = new HashMap<UUID,MiningMachine>();
//    private HashMap<UUID,Connection> uuidConnectionHashMap = new HashMap<UUID,Connection>();
//    private HashMap<UUID,TransportSystem> uuidTransportSystemHashMap = new HashMap<UUID,TransportSystem>();
    //private World world = new World();

    private ConnectionRepository connectionRepository;
    private MiningMachineRepository miningMachineRepository;
    private TransportSystemRepository transportSystemRepository;
    private FieldRepository fieldRepository;
    private RoomRepository roomRepository;

    @Autowired
    public MiningMachineService(ConnectionRepository connectionRepository,
                                MiningMachineRepository miningMachineRepository,
                                TransportSystemRepository transportSystemRepository,
                                FieldRepository fieldRepository,
                                RoomRepository roomRepository){

        this.connectionRepository = connectionRepository;
        this.miningMachineRepository = miningMachineRepository;
        this.transportSystemRepository = transportSystemRepository;
        this.fieldRepository = fieldRepository;
        this.roomRepository = roomRepository;

    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height,width);
        fieldRepository.save(field);
        field.determineNeighbor();
        fieldRepository.save(field);
        return field.getId();

    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        fieldRepository.findById(fieldId).get().placeWall(wall);
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        return transportSystemRepository.save(transportSystem).getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        TransportSystem transportSystem = transportSystemRepository.findById(transportSystemId).get();

        Field sourceField = fieldRepository.findById(sourceFieldId).get();
        Field destinationField = fieldRepository.findById(destinationFieldId).get();
        Connection connection = transportSystem.createConnection(sourceField,sourcePoint,destinationField,destinationPoint);

        return connectionRepository.save(connection).getId();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {

        MiningMachine miningMachine = new MiningMachine(name);
        return miningMachineRepository.save(miningMachine).getId();
    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {

        return miningMachineRepository.findById(miningMachineId).get().execute(task,fieldRepository,connectionRepository);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){

        return miningMachineRepository.findById(miningMachineId).get().getRoom().getField().getUuid();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getPoint(UUID miningMachineId){

       return  miningMachineRepository.findById(miningMachineId).get().getPoint();

    }
}
