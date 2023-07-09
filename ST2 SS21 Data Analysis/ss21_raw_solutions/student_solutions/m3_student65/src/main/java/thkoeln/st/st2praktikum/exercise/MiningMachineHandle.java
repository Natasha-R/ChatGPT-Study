package thkoeln.st.st2praktikum.exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class MiningMachineHandle {

    private HashMap<UUID, Field> fields = new HashMap<>();
    private HashMap<UUID, MiningMachine> miningMachines = new HashMap<>();
    private HashMap<UUID, Connection> connections = new HashMap<>();
    private HashMap<UUID, TransportTechnology> transportTechnology = new HashMap<>();

    @Autowired
    private MiningMachineRepository miningMachineRepository;

    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;

    public UUID addField(Integer height, Integer width)
    {
        UUID uuid = UUID.randomUUID();
        fields.put(uuid,new Field(height, width,uuid));
        //fieldRepository.save(fields.get(uuid));
        return uuid;
    }

    public void addObstacle(UUID fieldId, Obstacle obstacle) {

        fields.get(fieldId).addObstacle(obstacle);
    }
    public UUID addTransportTechnology(String technology) {
        UUID uuid = UUID.randomUUID();
        transportTechnology.put(uuid,new TransportTechnology(technology,uuid));
        transportTechnologyRepository.save(transportTechnology.get(uuid));
        return uuid;
    }

    public UUID addConnection(UUID transportTechnology, UUID sourceFieldId, Point sourcePoint, UUID destinationFieldId, Point desetinationPoint) {
        UUID uuid = UUID.randomUUID();
        CheckIfConnectionOutOfBounds(sourceFieldId, sourcePoint, destinationFieldId, desetinationPoint);
        connections.put(uuid,new Connection(sourceFieldId, sourcePoint, destinationFieldId, desetinationPoint, uuid));
        connectionRepository.save(connections.get(uuid));
        return uuid;
    }

    public UUID addMiningMachine(String name) {
        UUID uuid = UUID.randomUUID();
        miningMachines.put(uuid, new MiningMachine(name,uuid));
        miningMachineRepository.save(miningMachines.get(uuid));
        return uuid;
    }
    public Boolean executeCommand(UUID miningMachineId, Task task) {


        if(task.getTaskType() == TaskType.TRANSPORT)
        {
            if(miningMachines.get(miningMachineId).transport(connections, task.getGridId()))
            {
                miningMachineRepository.save(miningMachines.get(miningMachineId));
                return true;
            }
        }
        else if(task.getTaskType() == TaskType.ENTER)
        {
            if(miningMachines.get(miningMachineId).initialize(miningMachineId, miningMachines, task.getGridId()))
            {
                miningMachineRepository.save(miningMachines.get(miningMachineId));
                return true;
            }
        }
        else {
            Field mmIsLocatedOn = fields.get(miningMachines.get(miningMachineId).getFieldId());
            if(miningMachines.get(miningMachineId).move(mmIsLocatedOn, task, miningMachines))
            {
                miningMachineRepository.save(miningMachines.get(miningMachineId));
                return true;
            }
        }
        miningMachineRepository.save(miningMachines.get(miningMachineId));
        return false;
    }
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachines.get(miningMachineId).getFieldId();
    }
    public Point getCoordinates(UUID miningMachineId){
        return miningMachines.get(miningMachineId).getPoint();
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
