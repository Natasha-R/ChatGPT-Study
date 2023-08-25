package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.ObjectHolder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineCommands;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;


import java.util.UUID;


@Service
public class MiningMachineService {
    private MiningMachineCommands commands = ObjectHolder.getMiningMachineCommands();
    @Autowired
    private MiningMachineRepository miningMachineRepository;

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine temp = new MiningMachine();
        temp.setName(name);
        commands.getMiningMachines().add(temp);
        miningMachineRepository.save(temp);
        return temp.getId();

    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        commands.findMiningMachine(miningMachineId).getTasks().add(task);
        switch(task.getTaskType()){
            case ENTER:
                return commands.spawnMiningMachine(miningMachineId, task.getGridId(), miningMachineRepository);
            case NORTH:
                commands.walk(miningMachineId, task.getNumberOfSteps(),0,1, miningMachineRepository);
                return true;
            case EAST:
                commands.walk(miningMachineId, task.getNumberOfSteps(),1,0, miningMachineRepository);
                return true;
            case SOUTH:
                commands.walk(miningMachineId, task.getNumberOfSteps(),0,-1, miningMachineRepository);
                return true;
            case WEST:
                commands.walk(miningMachineId, task.getNumberOfSteps(),-1,0, miningMachineRepository);
                return true;
            case TRANSPORT:
                return commands.transportMiningMachine(miningMachineId, task.getGridId(), miningMachineRepository);
            default:
                return false;


        }

    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        for(int i = 0; i<commands.getFields().size(); i++){
            if(commands.findMiningMachine(miningMachineId).getFieldId().equals(commands.getFields().get(i).getId())){
                return commands.getFields().get(i).getId();
            }
        }
        throw new NullPointerException();

    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        return commands.findMiningMachine(miningMachineId).getPoint();
    }
}
