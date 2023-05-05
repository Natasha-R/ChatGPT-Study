package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
public class MiningMachineService {

    private HashMap<UUID, MiningMachine> miningMachines = new HashMap<>();

    @Autowired
    private MiningMachineRepository miningMachineRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;
    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID uuid = UUID.randomUUID();
        miningMachines.put(uuid, new MiningMachine(name,uuid));
        miningMachineRepository.save(miningMachines.get(uuid));
        return uuid;
    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        List<Field> fields = Streamable.of(fieldRepository.findAll()).toList();
        if(task.getTaskType() == TaskType.TRANSPORT)
        {
            List<TransportTechnology> transports = Streamable.of(transportTechnologyRepository.findAll()).toList();
            List<Connection> connections = new ArrayList<>();
            for(int i=0;i<transports.size();i++)
            {
                for(int x=0;x<transports.get(i).getConnections().size();x++)
                {
                    connections.add(transports.get(i).getConnections().get(x));
                }
            }
            if(miningMachines.get(miningMachineId).transport(connections, task.getGridId()))
            {
                miningMachines.get(miningMachineId).addTask(task);
                miningMachineRepository.save(miningMachines.get(miningMachineId));
                return true;
            }
        }
        else if(task.getTaskType() == TaskType.ENTER)
        {
            if(miningMachines.get(miningMachineId).initialize(miningMachineId, miningMachines, task.getGridId()))
            {
                miningMachines.get(miningMachineId).addTask(task);
                miningMachineRepository.save(miningMachines.get(miningMachineId));
                return true;
            }
        }
        else {
            Field mmIsLocatedOn = fields.get(GetIndexWhereUUID(fields,miningMachines.get(miningMachineId).getFieldId()));
            if(miningMachines.get(miningMachineId).move(mmIsLocatedOn, task, miningMachines))
            {
                miningMachines.get(miningMachineId).addTask(task);
                miningMachineRepository.save(miningMachines.get(miningMachineId));
                return true;
            }
        }
        miningMachines.get(miningMachineId).addTask(task);
        miningMachineRepository.save(miningMachines.get(miningMachineId));
        return false;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachines.get(miningMachineId).getFieldId();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        return miningMachines.get(miningMachineId).getPoint();
    }
    public static int GetIndexWhereUUID(List<Field> fields, UUID uuid)
    {
        for(int i=0;i<fields.size();i++)
        {
            if(fields.get(i).getId().equals(uuid))
            {
                return i;
            }
        }
        return -1;
    }
}
