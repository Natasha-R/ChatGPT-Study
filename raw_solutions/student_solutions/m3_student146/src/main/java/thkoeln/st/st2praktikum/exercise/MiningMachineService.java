package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MiningMachineService {

    private MiningMachineComponent miningMachineComponent = new MiningMachineComponent(this);

    @Autowired
    public MiningMachineRepository miningMachineRepository;

    @Autowired
    public FieldRepository fieldRepository;
    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID uuid = UUID.randomUUID();
        fieldRepository.save(new Field(uuid, height, width));
        return uuid;
    }

    /**
     * This method adds a wall to a given field.
     * @param fieldId the ID of the field the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID fieldId, Wall wall) {
        Field field = fieldRepository.findById(fieldId).orElse(null);
        if (field == null)
            return;
        int fromX = wall.getStart().getX();
        int fromY = wall.getStart().getY();
        int toX = wall.getEnd().getX();
        int toY = wall.getEnd().getY();

        if (fromX == toX) {
            for (int i = fromY; i < toY; i++) {
                SingleWall singleWall = new SingleWall(fromX, i);
                field.getVerticalWalls().add(singleWall);
            }
        } else {
            for (int i = fromX; i < toX; i++) {
                SingleWall singleWall = new SingleWall(i, fromY);
                field.getHorizontalWalls().add(singleWall);
            }
        }
        fieldRepository.save(field);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return UUID.randomUUID();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        Field sourceField = fieldRepository.findById(sourceFieldId).orElse(null);
        Field destField = fieldRepository.findById(destinationFieldId).orElse(null);
        if(sourceField == null || destField == null)
            return null;
        UUID uuid = UUID.randomUUID();
        FieldConnection fieldConnection = new FieldConnection(uuid, sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        sourceField.getFieldConnections().put(uuid, fieldConnection);
        fieldRepository.save(sourceField);
        return uuid;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID uuid = UUID.randomUUID();
        miningMachineRepository.save(new MiningMachine(uuid, name));
        return uuid;
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        MiningMachine machine = miningMachineRepository.findById(miningMachineId).orElse(null);
        if(machine == null) {
            return false;
        }
        Field field;
        if(machine.getFieldId() != null)
            field = fieldRepository.findById(machine.getFieldId()).orElse(null);
        else
            field = fieldRepository.findById(order.getGridId()).orElse(null);
        if(field == null) {
            return false;
        }
        Pair<Boolean, MiningMachine> pair = miningMachineComponent.onMovement(machine, field, order);
        if(pair.getFirst())
            miningMachineRepository.save(machine);
        System.out.println("Machine:" + machine.getId());
        System.out.println("Field: " + machine.getFieldId());
        return pair.getFirst();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        MiningMachine miningMachine = miningMachineRepository.findById(miningMachineId).orElse(null);
        if(miningMachine == null)
            return null;
        return miningMachine.getId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        MiningMachine miningMachine = miningMachineRepository.findById(miningMachineId).orElse(null);
        if(miningMachine == null)
            return null;
        return miningMachine.getCoordinate();
    }
}
