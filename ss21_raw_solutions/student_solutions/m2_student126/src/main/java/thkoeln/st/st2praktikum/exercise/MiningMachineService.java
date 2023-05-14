package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachineService {

    private final MiningMachineController controller = new MiningMachineController();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width, height);
        controller.addField(field);
        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        controller.addBarrier(fieldId, barrier);
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        FieldConnection fieldConnection = new FieldConnection(
                sourceFieldId,
                destinationFieldId,
                sourceCoordinate,
                destinationCoordinate
        );
        controller.addFieldConnection(fieldConnection);
        return fieldConnection.getId();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        controller.addMiningMachine(miningMachine);
        return miningMachine.getId();
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        return controller.executeCommand(miningMachineId, command);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return controller.getMiningMachineFieldId(miningMachineId);
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinates of the mining machine
     */
    public Coordinate getCoordinate(UUID miningMachineId) {
        return controller.getCoordinates(miningMachineId);
    }

}
