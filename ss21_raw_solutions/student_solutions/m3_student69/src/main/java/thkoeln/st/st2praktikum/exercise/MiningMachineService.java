package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MiningMachineService {

    private MachineDirections machineDirections = new MachineDirections(this);

    @Autowired
    MiningMachineRepo miningMachineRepo;
    @Autowired
    FieldRepo fieldRepo;

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID uuid = UUID.randomUUID();
        fieldRepo.save(new Field(uuid, height, width));
        return uuid;
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = fieldRepo.findById(fieldId).get();
        if (barrier.getStart().getX().equals(barrier.getEnd().getX())) {
            field.getHORIZONTAL().add(barrier);
        } else if (barrier.getStart().getY().equals(barrier.getEnd().getY())) {
            field.getVERTICAL().add(barrier);
        }
        fieldRepo.save(field);
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return UUID.randomUUID();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     *
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId         the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate      the coordinate of the entry point
     * @param destinationFieldId    the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        Field sourceField = fieldRepo.findById(sourceFieldId).get();

        if(destinationFieldId == null || destinationCoordinate == null)
            throw new RuntimeException("Destination cannot be null!");

        if(sourceCoordinate.getX() > sourceField.getWidth() || sourceCoordinate.getX() < 0)
            throw new RuntimeException("Wrong coordinates!");
        if(sourceCoordinate.getY() > sourceField.getHeight() || sourceCoordinate.getY() < 0)
            throw new RuntimeException("Wrong coordinates!");

        Connection con = new Connection(UUID.randomUUID(), sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);

        sourceField.getCONNECTION().add(con);
        fieldRepo.save(sourceField);

        return con.getConnection_ID();
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(UUID.randomUUID(), name, new Coordinate(0, 0), null);
        miningMachineRepo.save(machine);
        return machine.getMiningMachine_ID();
    }

    /**
     * This method lets the mining machine execute a task.
     *
     * @param miningMachineId the ID of the mining machine
     * @param task            the given task
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on squares with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        MiningMachine machine = miningMachineRepo.findById(miningMachineId).get();
        Field field;
        if(machine.getFieldId() != null) {
            field = fieldRepo.findById(machine.getFieldId()).get();
        }else {
            field = fieldRepo.findById(task.getGridId()).get();
        }

        switch (task.getTaskType()) {
            case NORTH:
                machineDirections.north(field.getVERTICAL(), task.getNumberOfSteps(), machine);
                break;
            case SOUTH:
                machineDirections.south(field.getVERTICAL(), task.getNumberOfSteps(), machine);
                break;
            case EAST:
                machineDirections.east(field.getHORIZONTAL(), task.getNumberOfSteps(), machine);
                break;
            case WEST:
                machineDirections.west(field.getHORIZONTAL(), task.getNumberOfSteps(), machine);
                break;
            case TRANSPORT:
                return machineDirections.transfer(machine, task.getGridId(), field);
            case ENTER:
                return machineDirections.entrance(machine, task.getGridId());
        }
        System.out.println("[" + task.getTaskType() + "," + task.getNumberOfSteps() + "]");
        System.out.println("(" + machine.getCoordinate().getX() + "," + machine.getCoordinate().getY() + ") ");
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return miningMachineRepo.findById(miningMachineId).get().getFieldId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId) {
        return miningMachineRepo.findById(miningMachineId).get().getCoordinate();
    }
}
