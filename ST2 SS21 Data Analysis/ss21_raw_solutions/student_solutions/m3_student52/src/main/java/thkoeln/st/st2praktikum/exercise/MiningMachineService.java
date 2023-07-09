package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MiningMachineService {
    // todo understand autowired
    private final Controller controller;

    @Autowired
    public MiningMachineService(Controller controller) {
        this.controller = controller;
    }


    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        var f = new Field(height, width);
        controller.getFieldRepo().save(f);
        return f.getId();
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        var field = controller.getFieldRepo().get(fieldId);
        field.getBarriers().add(barrier);
        controller.getFieldRepo().save(field);
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        var ts = new TransportSystem(system);
        controller.getTransportSystemRepo().save(ts);
        return ts.getId();
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
        var transportSystem = controller.getTransportSystemRepo().get(transportSystemId);
        var srcField = controller.getFieldRepo().get(sourceFieldId);

        var c = new Connection(sourcePoint, destinationPoint);
        controller.getConnectionRepo().save(c);

        transportSystem.getConnections().add(c);
        controller.getTransportSystemRepo().save(transportSystem);

        srcField.getConnections().put(destinationFieldId, c);
        controller.getFieldRepo().save(srcField);
        return c.getId();
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        var mm = new MiningMachine(name, new Point(0,0), null);
        controller.getMiningMachineRepo().save(mm);
        return mm.getId();
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
        return controller.execute(miningMachineId, order);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        var mm = controller.getMiningMachineRepo().findById(miningMachineId).get();
        return mm.getFieldId();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        var mm = controller.getMiningMachineRepo().findById(miningMachineId).get();
        return mm.getPoint();
    }
}
