package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.InvalidCommandException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepo;
import thkoeln.st.st2praktikum.exercise.field.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.ConnectionRepo;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystemRepo;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


@Service
public class MiningMachineService {

    @Getter
    private FieldRepo fieldRepo;
    @Getter private MiningMachineRepo miningMachineRepo;
    @Getter private TransportSystemRepo transportSystemRepo;
    @Getter private ConnectionRepo connectionRepo;

    private MiningMachine mm = null;

    @Autowired
    public MiningMachineService(FieldRepo fieldRepo, MiningMachineRepo miningMachineRepo, TransportSystemRepo transportSystemRepo, ConnectionRepo connectionRepo) {
        this.fieldRepo = fieldRepo;
        this.miningMachineRepo = miningMachineRepo;
        this.transportSystemRepo = transportSystemRepo;
        this.connectionRepo = connectionRepo;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        var mm = new MiningMachine(name, new Point(0,0), null);
        miningMachineRepo.save(mm);
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
        return execute(miningMachineId, order);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        var mm = miningMachineRepo.findById(miningMachineId).get();
        return mm.getFieldId();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        return miningMachineRepo.findById(miningMachineId).get().getPoint();
    }


    public boolean execute(UUID mmID, Order cmd){
        mm = miningMachineRepo.get(mmID);
        try {
            if (cmd.getOrderType() == OrderType.ENTER)
                entry(cmd.getGridId());
            else if (cmd.getOrderType() == OrderType.TRANSPORT)
                travers(cmd.getGridId());
            else
                move(cmd);
            mm.getOrders().add(cmd);
            miningMachineRepo.save(mm);
            return true;
        } catch (Exception e) {
            System.out.println("Command "+cmd.toString()+" failed:\n" + "CAUSE: " + e.getMessage());
            return false;
        }
    }

    private void move(Order cmd){
        var steps = cmd.getNumberOfSteps();
        var field = fieldRepo.get(mm.getFieldId());
        for (int i = 0; i < steps; i++) { moveStep(cmd.getOrderType(), field.getBarriers()); }
    }

    private void moveStep(OrderType dir, List<Barrier> barriers){
        for (Barrier b : barriers){ if (b.blocks(mm.getPoint(), dir)) return; }
        var newCoords = mm.getPoint().copy();
        if (dir == OrderType.NORTH) newCoords.moveNo();
        if (dir == OrderType.EAST) newCoords.moveEa();
        if (dir == OrderType.SOUTH) newCoords.moveSo();
        if (dir == OrderType.WEST) newCoords.moveWe();

        if (occupiedCoords(mm.getFieldId(), newCoords))
            return;
        mm.setPoint(newCoords);
    }

    private void travers(UUID destFieldID){
        var field = fieldRepo.get(mm.getFieldId());
        var connection = field.getConnections().get(destFieldID);
        if (connection == null)
            throw new InvalidCommandException("Connection does not exist");
        if (!connection.getSrc().equals(mm.getPoint()))
            throw new InvalidCommandException("Not standing on a Tunnel");
        if (occupiedCoords(destFieldID, connection.getDest()))
            throw new InvalidCommandException("Entry Field is occupied by another machine");
        mm.setFieldId(destFieldID);
        mm.setPoint(connection.getDest());
    }

    private void entry(UUID destFieldID){
        if (mm.getFieldId() != null)
            throw new InvalidCommandException("Can't re-entry machine already in a field");
        if (occupiedCoords(destFieldID, mm.getPoint()))
            throw new InvalidCommandException("Entry Field is occupied by another machine");
        mm.setFieldId(destFieldID);
        miningMachineRepo.put(mm);
    }

    private boolean occupiedCoords(UUID fieldID, Point point){
        // todo we currently simply loop through all mining machines
        // this could be done with a more efficient search
        var blocking = miningMachineRepo.findByCoords(fieldID, point);
        return !blocking.isEmpty();
    }
}
