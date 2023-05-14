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

    public void addObstacle(UUID fieldId, Obstacle obstacle) {

        fields.get(fieldId).addObstacle(obstacle);
    }

    public UUID addConnection(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId,Point desetinationPoint) {
        UUID uuid = UUID.randomUUID();
        CheckIfConnectionOutOfBounds(sourceFieldId, sourcePoint, destinationFieldId, desetinationPoint);
        connections.put(uuid,new Connection(sourceFieldId, sourcePoint, destinationFieldId, desetinationPoint));
        return uuid;
    }

    public UUID addMiningMachine(String name) {
        UUID uuid = UUID.randomUUID();
        miningMachines.put(uuid, new MiningMachine(name));
        return uuid;
    }
    public Boolean executeCommand(UUID miningMachineId, Task task) {


        if(task.getTaskType() == TaskType.TRANSPORT)
        {
            Transportable t = (Transportable) miningMachines.get(miningMachineId);
            return t.transport(connections, task.getGridId());
        }
        else if(task.getTaskType() == TaskType.ENTER)
        {
            return miningMachines.get(miningMachineId).initialize(miningMachines, task.getGridId());
        }
        else {
            Field mmIsLocatedOn = fields.get(miningMachines.get(miningMachineId).getIsLocatedOnField());
            return miningMachines.get(miningMachineId).move(mmIsLocatedOn, task,miningMachines);
        }
    }
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachines.get(miningMachineId).getIsLocatedOnField();
    }
    public Point getCoordinates(UUID miningMachineId){
        return miningMachines.get(miningMachineId).getPos();
    }

    public void CheckIfConnectionOutOfBounds(UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId,Point desetinationPoint)
    {
        if(sourcePoint.getX() >= fields.get(sourceFieldId).getSize().getX() || sourcePoint.getY() >= fields.get(sourceFieldId).getSize().getY())
        {
            throw new IllegalActionException("Connection has to be placed inside fieldbounds!");
        }
        if(desetinationPoint.getX() >= fields.get(destinationFieldId).getSize().getX() || desetinationPoint.getY() >= fields.get(destinationFieldId).getSize().getY())
        {
            throw new IllegalActionException("Connection has to be placed inside fieldbounds!");
        }
    }
}
