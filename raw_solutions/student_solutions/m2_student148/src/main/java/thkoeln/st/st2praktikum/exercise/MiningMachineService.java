package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class MiningMachineService {
    public HashMap<UUID, MiningMachine> miningMachines = new HashMap<>();
    public HashMap<UUID, Field> fields = new HashMap<>();
    public HashMap<UUID, Connection> connections = new HashMap<>();

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height, width);
        fields.put(field.getId(), field);
        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = fields.get(fieldId);
        if (barrier.getEnd().getX() <= field.getWidth() && barrier.getEnd().getY() <= field.getHeight()) {
            field.getBarriers().add(barrier);
            fields.put(field.getId(), field);
        } else throw new IllegalArgumentException("A barrier can't extend beyond the field it's being placed on!");
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     *
     * @param sourceFieldId         the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate      the coordinates of the entry point
     * @param destinationFieldId    the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        Field sourceField = fields.get(sourceFieldId);
        Field destinationField = fields.get(destinationFieldId);
        if (sourceCoordinate.getX() < sourceField.getWidth() && sourceCoordinate.getY() < sourceField.getHeight()
                && destinationCoordinate.getX() < destinationField.getWidth() && destinationCoordinate.getY() < destinationField.getHeight()) {
        Connection connection = new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate);
        connections.put(connection.getId(), connection);
        return connection.getId();
        } else throw new IllegalArgumentException("A connection can't exist beyond the fields boundries!");
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachines.put(miningMachine.getId(), miningMachine);
        return miningMachine.getId();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param command         the given command
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on cells with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (MachineMovement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        Movable move = new MachineMovement(this.miningMachines, this.fields, this.connections);
        return move.moveTo(miningMachineId, command);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        MiningMachine machine = miningMachines.get(miningMachineId);
        if (machine.getState() != MachineDeploymentState.HIBERNATING) {
            return machine.getCurrentField().getId();
        }
        throw new IllegalArgumentException(machine.getMachineName() + " is sad, it has not been deployed on any field yet");
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordinates of the mining machine
     */
    public Coordinate getCoordinate(UUID miningMachineId) {
        return miningMachines.get(miningMachineId).getPosition();
    }
}
