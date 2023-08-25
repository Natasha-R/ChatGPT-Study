package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repos.ConnectionRepo;
import thkoeln.st.st2praktikum.exercise.repos.FieldRepo;
import thkoeln.st.st2praktikum.exercise.repos.MiningMachineRepo;
import thkoeln.st.st2praktikum.exercise.repos.TransportSystemRepo;

import java.util.List;
import java.util.UUID;

@Service
public class Controller {
    @Getter private FieldRepo fieldRepo;
    @Getter private MiningMachineRepo miningMachineRepo;
    @Getter private TransportSystemRepo transportSystemRepo;
    @Getter private ConnectionRepo connectionRepo;

    private MiningMachine mm = null;

    @Autowired
    public Controller(FieldRepo fieldRepo, MiningMachineRepo miningMachineRepo, TransportSystemRepo transportSystemRepo, ConnectionRepo connectionRepo) {
        this.fieldRepo = fieldRepo;
        this.miningMachineRepo = miningMachineRepo;
        this.transportSystemRepo = transportSystemRepo;
        this.connectionRepo = connectionRepo;
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
            return true;
        } catch (Exception e) {
            System.out.println("Command "+cmd.toString()+" failed:\n"+e.getMessage());
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
