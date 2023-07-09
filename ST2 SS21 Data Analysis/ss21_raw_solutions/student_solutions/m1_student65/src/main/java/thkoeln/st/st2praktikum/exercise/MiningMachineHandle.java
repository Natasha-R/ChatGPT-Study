package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class MiningMachineHandle {
    private HashMap<UUID,Field> fields = new HashMap<UUID,Field>();
    private HashMap<UUID,MiningMachine> miningMachines = new HashMap<UUID,MiningMachine>();
    private HashMap<UUID,Connection> connections = new HashMap<UUID,Connection>();

    public UUID addField(Integer height, Integer width)
    {
        UUID uuid = UUID.randomUUID();
        fields.put(uuid,new Field(height, width));
        return uuid;
    }

    public void addObstacle(UUID fieldId, String obstacleString) {
        fields.get(fieldId).addObstacle(obstacleString);
    }

    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {
        UUID uuid = UUID.randomUUID();
        connections.put(uuid,new Connection(sourceFieldId, sourceCoordinate, destinationFieldId, destinationCoordinate));
        return uuid;
    }

    public UUID addMiningMachine(String name) {
        UUID uuid = UUID.randomUUID();
        miningMachines.put(uuid, new MiningMachine(name));
        return uuid;
    }
    public Boolean executeCommand(UUID miningMachineId, String commandString) {

        if(commandString.contains("tr"))
        {
            Transportable t = (Transportable) miningMachines.get(miningMachineId);
            return t.transport(connections, commandString);
        }
        else if(commandString.contains("en"))
        {
            return miningMachines.get(miningMachineId).initialize(miningMachines, commandString);
        }
        else {
            Field mmIsLocatedOn = fields.get(miningMachines.get(miningMachineId).getIsLocatedOnField());
            return miningMachines.get(miningMachineId).move(mmIsLocatedOn, commandString);
        }
    }
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachines.get(miningMachineId).getIsLocatedOnField();
    }
    public String getCoordinates(UUID miningMachineId){
        return miningMachines.get(miningMachineId).getStringPos();
    }
}
