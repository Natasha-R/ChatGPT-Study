package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachineService {
    private ExecutorSystem ex = new ExecutorSystem();

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        var uuid = UUID.randomUUID();
        var f = new Field(uuid, height, width);
        ex.getFieldRepo().put(uuid, f);
        return uuid;
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        var field = ex.getFieldRepo().get(fieldId);
        field.getBarriers().add(barrier);
    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourcePoint the points of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point destinationPoint) {
        var srcField = ex.getFieldRepo().get(sourceFieldId);
        srcField.addConnection(destinationFieldId, sourcePoint, destinationPoint);
        return UUID.randomUUID();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        var mm = new MiningMachine(name, new Point(0,0), null);
        var uuid = UUID.randomUUID();
        ex.getMiningMachineRepo().put(uuid, mm);
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
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        return ex.execute(miningMachineId, order);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        var mm = ex.getMiningMachineRepo().get(miningMachineId);
        return mm.getFieldID();
    }

    /**
     * This method returns the points a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the points of the mining machine
     */
    public Point getPoint(UUID miningMachineId){
        var mm = ex.getMiningMachineRepo().get(miningMachineId);
        return mm.getPoint();
    }
}
