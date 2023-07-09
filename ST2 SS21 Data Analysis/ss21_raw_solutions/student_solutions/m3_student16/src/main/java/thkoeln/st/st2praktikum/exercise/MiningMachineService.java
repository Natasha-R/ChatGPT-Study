package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class MiningMachineService {

    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private MiningMachineRepository miningMachineRepository;
    @Autowired
    private TransportSystemRepository transportSystemRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    /*
    @Autowired
    public MiningMachineService(FieldRepository fieldRepository,
                                MiningMachineRepository miningMachineRepository,
                                TransportSystemRepository transportSystemRepository){
        this.fieldRepository = fieldRepository;
        this.miningMachineRepository = miningMachineRepository;
        this.transportSystemRepository = transportSystemRepository;
    }
    */


    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height,width);
        fieldRepository.save(field);
        return field.getFieldId();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field targetField = fieldRepository.getFieldByFieldId(fieldId);
        if(targetField == null) throw new NullPointerException();
        targetField.getWalls().add(wall);
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        transportSystemRepository.save(transportSystem);
        return transportSystem.getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        Connection connection = new Connection(transportSystemId, sourceFieldId,sourceCoordinate,destinationFieldId,destinationCoordinate);
        boolean sourceValid = fieldRepository.getFieldByFieldId(sourceFieldId).coordinateIsNotOutOfBounds(sourceCoordinate);
        boolean destinationValid = fieldRepository.getFieldByFieldId(destinationFieldId).coordinateIsNotOutOfBounds(destinationCoordinate);
        Field sourceField = fieldRepository.getFieldByFieldId(sourceFieldId);
        if(sourceField == null) throw new NullPointerException();
        if(sourceValid && destinationValid) {
            connectionRepository.save(connection);
            sourceField.addConnection(connection);
            return connection.getConnectionID();
        }
        throw new IllegalArgumentException();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachineRepository.save(miningMachine);
        return miningMachine.getId();
    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        Movable miningMachine = miningMachineRepository.getMiningMachineByMiningMachineId(miningMachineId);
        if(miningMachine == null)throw new NullPointerException();
        return miningMachine.executeCommand(task,(List) fieldRepository.findAll(),(List) miningMachineRepository.findAll());
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        UUID miningMachineFieldId = miningMachineRepository.getMiningMachineByMiningMachineId(miningMachineId).getFieldId();
        if(miningMachineFieldId == null) throw new NullPointerException();
        return miningMachineFieldId;
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        Movable miningMachine = miningMachineRepository.getMiningMachineByMiningMachineId(miningMachineId);
        if(miningMachine == null) throw new NullPointerException();
        return miningMachine.getCoordinate();
    }
}
