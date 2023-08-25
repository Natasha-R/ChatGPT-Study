package thkoeln.st.st2praktikum.exercise;

import org.hibernate.type.UUIDBinaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.UUID;


@Service
public class MiningMachineService {

    private UUIDManager uuidManager = new UUIDManager();
    private TechnologyManager technologyManager = new TechnologyManager();

    @Autowired
    private MiningMachineRepository miningMachineRepo;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private TechnologyManagerRepository technologyManagerRepository;

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UUIDManagerRepository uuidManagerRepository;

    public MiningMachineService() {}

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width, height);
        uuidManager.addGameComponent(field);
        uuidManagerRepository.save(uuidManager);
        fieldRepository.save(field);
        return field.getUUID();
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field field = (Field) uuidManager.getObjectFromUUID(fieldId);
        field.addWall(wall);
        fieldRepository.save(field);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = technologyManager.createTransporterTechnology(technology, transportTechnologyRepository);
        uuidManager.addGameComponent(transportTechnology);
        technologyManagerRepository.save(technologyManager);
        uuidManagerRepository.save(uuidManager);
        return transportTechnology.getUUID();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        TransportTechnology technology = (TransportTechnology) uuidManager.getObjectFromUUID(transportTechnologyId);
        UUID connectionUUID = technology.createConnection(
                (Field) uuidManager.getObjectFromUUID(sourceFieldId),
                sourcePoint,
                (Field) uuidManager.getObjectFromUUID(destinationFieldId),
                destinationPoint, uuidManager, connectionRepository, uuidManagerRepository);
        return connectionUUID;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(name);
        uuidManager.addGameComponent(machine);
        uuidManagerRepository.save(uuidManager);
        miningMachineRepo.save(machine);
        return machine.getUUID();
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        MiningMachine miningMachine = (MiningMachine) uuidManager.getObjectFromUUID(miningMachineId);
        boolean result = false;
        switch (order.getOrderType()){
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                result = miningMachine.move(order.getOrderType(), order.getNumberOfSteps());
                miningMachineRepo.save(miningMachine);
                break;
            case TRANSPORT:
                IConnection connectionToUse = technologyManager.getConnection(miningMachine.getField().getUUID(), order.getGridId(), miningMachine.getPosition());
                if (connectionToUse == null) result = false;
                else result = technologyManager.useConnection(connectionToUse.getUUID());
                miningMachineRepo.save(miningMachine);
                break;
            case ENTER:
                result = setMiningMachineOnField(miningMachineId, order.getGridId());
                miningMachineRepo.save(miningMachine);
                break;
            default:

        }
        return result;
    }

    private boolean setMiningMachineOnField(UUID miningMachineId, UUID fieldID) {
        IComponent findedField = uuidManager.getObjectFromUUID(fieldID);
        if (findedField == null) { return false; }
        Field field = (Field) findedField;
        if (field.addMachine((MiningMachine) uuidManager.getObjectFromUUID(miningMachineId))) {
            MiningMachine machine = (MiningMachine) uuidManager.getObjectFromUUID(miningMachineId);
            machine.setField(field);
            machine.setPosition(new Point(0, 0));
        } else { return false; }
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        MiningMachine machine = (MiningMachine) uuidManager.getObjectFromUUID(miningMachineId);
        return machine.getField().getUUID();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        MiningMachine machine = (MiningMachine) uuidManager.getObjectFromUUID(miningMachineId);
        return machine.getPosition();
    }
}
