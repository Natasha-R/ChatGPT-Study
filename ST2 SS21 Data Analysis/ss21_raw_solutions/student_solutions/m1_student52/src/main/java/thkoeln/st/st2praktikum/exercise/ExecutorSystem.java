package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

public class ExecutorSystem {
    @Getter private final FieldRepo fieldRepo = new FieldRepo();
    @Getter private final MiningMachineRepo miningMachineRepo = new MiningMachineRepo();

    private MiningMachine mm = null;

    public boolean execute(UUID mmID, Command cmd){
        mm = miningMachineRepo.get(mmID);
        try {
            if (cmd.getName() == Command.Name.en)
                entry(UUID.fromString(cmd.getParameter()));
            else if (cmd.getName() == Command.Name.tr)
                travers(UUID.fromString(cmd.getParameter()));
            else
                move(cmd);
            return true;
        } catch (Exception e) {
            System.out.println("Command "+cmd.toString()+" failed:\n"+e.getMessage());
            return false;
        }
    }

    private void move(Command cmd){
        var steps = Integer.parseInt(cmd.getParameter());
        var field = fieldRepo.get(mm.getFieldID());
        for (int i = 0; i < steps; i++) { moveStep(cmd.getName(), field.getBarriers()); }
    }

    private void moveStep(Command.Name dir, ArrayList<Barrier> barriers){
        for (Barrier b : barriers){ if (b.blocks(mm.getCoords(), dir)) return; }
        var newCoords = mm.getCoords().copy();
        if (dir == Command.Name.no) newCoords.moveNo();
        if (dir == Command.Name.ea) newCoords.moveEa();
        if (dir == Command.Name.so) newCoords.moveSo();
        if (dir == Command.Name.we) newCoords.moveWe();

        if (occupiedCoords(mm.getFieldID(), newCoords))
            throw new InvalidCommandException("Field blocked by another machine");
        mm.setCoords(newCoords);
    }

    private void travers(UUID destFieldID){
        var field = fieldRepo.get(mm.getFieldID());
        var connection = field.getConnectionsTo().get(destFieldID);
        if (connection == null)
            throw new InvalidCommandException("Connection does not exist");
        if (!connection.getSrc().equals(mm.getCoords()))
            throw new InvalidCommandException("Not standing on a Tunnel");
        if (occupiedCoords(destFieldID, connection.getDest()))
            throw new InvalidCommandException("Entry Field is occupied by another machine");
        mm.setFieldID(destFieldID);
        mm.setCoords(connection.getDest());
    }

    private void entry(UUID destFieldID){
        if (mm.getFieldID() != null)
            throw new InvalidCommandException("Can't re-entry machine already in a field");
        if (occupiedCoords(destFieldID, mm.getCoords()))
            throw new InvalidCommandException("Entry Field is occupied by another machine");
        mm.setFieldID(destFieldID);
    }

    private boolean occupiedCoords(UUID fieldID, Coords coords){
        for (MiningMachine mm: miningMachineRepo.values()) {
            if (coords.equals(mm.getCoords()) && fieldID.equals(mm.getFieldID())){
                return true;
            }
        }
        return false;
    }
}
