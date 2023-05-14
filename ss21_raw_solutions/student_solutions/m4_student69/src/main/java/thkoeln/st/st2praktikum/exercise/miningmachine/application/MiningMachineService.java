package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.MainComponent;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;


import java.util.ArrayList;
import java.util.UUID;


@Service
public class MiningMachineService {
    public MiningMachineRepo miningMachineRepo;

    @Autowired
    public MiningMachineService(MiningMachineRepo miningMachineRepo) {
        this.miningMachineRepo = miningMachineRepo;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(UUID.randomUUID(), name, new Coordinate(0, 0), null, new ArrayList<>());
        miningMachineRepo.save(machine);
        return machine.getMiningMachine_ID();    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        MiningMachine machine = miningMachineRepo.findById(miningMachineId).get();
        for (Task machineTask : machine.getTasks()) {
            System.out.println("PRE" + machineTask.getTaskType());
        }
        switch (task.getTaskType()) {
            case NORTH:
                MainComponent.machineDirections.north(task.getNumberOfSteps(), machine);
                break;
            case SOUTH:
                MainComponent.machineDirections.south(task.getNumberOfSteps(), machine);
                break;
            case EAST:
                MainComponent.machineDirections.east(task.getNumberOfSteps(), machine);
                break;
            case WEST:
                MainComponent.machineDirections.west(task.getNumberOfSteps(), machine);
                break;
            case TRANSPORT:
                machine.getTasks().add(task);
                miningMachineRepo.save(machine);
                return MainComponent.machineDirections.transfer(machine, task);
            case ENTER:
                machine.getTasks().add(task);
                miningMachineRepo.save(machine);
                return MainComponent.machineDirections.entrance(machine, task.getGridId());
        }
        machine.getTasks().add(task);
        miningMachineRepo.save(machine);
        for (Task machineTask : machine.getTasks()) {
            System.out.println("AFTER" + machineTask.getTaskType());
        }
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachineRepo.findById(miningMachineId).get().getFieldId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        return miningMachineRepo.findById(miningMachineId).get().getCoordinate();
    }
}
