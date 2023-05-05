package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

public class ExecutorSystem {
    @Getter private final FieldRepo fieldRepo = new FieldRepo();
    @Getter private final MiningMachineRepo miningMachineRepo = new MiningMachineRepo();

    private MiningMachine mm = null;

    public boolean execute(UUID mmID, Order cmd){
        mm = miningMachineRepo.get(mmID);
        try {
            if (cmd.getOrderType() == OrderType.ENTER)
                entry(cmd.getGridId());
            else if (cmd.getOrderType() == OrderType.TRANSPORT)
                travers(cmd.getGridId());
            else
                move(cmd);
            return true;
        } catch (Exception e) {
            System.out.println("Command "+cmd.toString()+" failed:\n"+e.getMessage());
            return false;
        }
    }

    private void move(Order cmd){
        var steps = cmd.getNumberOfSteps();
        var field = fieldRepo.get(mm.getFieldID());
        for (int i = 0; i < steps; i++) { moveStep(cmd.getOrderType(), field.getBarriers()); }
    }

    private void moveStep(OrderType dir, ArrayList<Barrier> barriers){
        for (Barrier b : barriers){ if (b.blocks(mm.getPoint(), dir)) return; }
        var newCoords = mm.getPoint().copy();
        if (dir == OrderType.NORTH) newCoords.moveNo();
        if (dir == OrderType.EAST) newCoords.moveEa();
        if (dir == OrderType.SOUTH) newCoords.moveSo();
        if (dir == OrderType.WEST) newCoords.moveWe();

        if (occupiedCoords(mm.getFieldID(), newCoords))
            return;
        mm.setPoint(newCoords);
    }

    private void travers(UUID destFieldID){
        var field = fieldRepo.get(mm.getFieldID());
        var connection = field.getConnectionsTo().get(destFieldID);
        if (connection == null)
            throw new InvalidCommandException("Connection does not exist");
        if (!connection.getSrc().equals(mm.getPoint()))
            throw new InvalidCommandException("Not standing on a Tunnel");
        if (occupiedCoords(destFieldID, connection.getDest()))
            throw new InvalidCommandException("Entry Field is occupied by another machine");
        mm.setFieldID(destFieldID);
        mm.setPoint(connection.getDest());
    }

    private void entry(UUID destFieldID){
        if (mm.getFieldID() != null)
            throw new InvalidCommandException("Can't re-entry machine already in a field");
        if (occupiedCoords(destFieldID, mm.getPoint()))
            throw new InvalidCommandException("Entry Field is occupied by another machine");
        mm.setFieldID(destFieldID);
    }

    private boolean occupiedCoords(UUID fieldID, Point point){
        for (MiningMachine mm: miningMachineRepo.values()) {
            if (point.equals(mm.getPoint()) && fieldID.equals(mm.getFieldID())){
                return true;
            }
        }
        return false;
    }
}
