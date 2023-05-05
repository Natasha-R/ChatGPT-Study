package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineMovement;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class MiningMachineService {
    @Autowired
    private MiningMachineMovement miningMachineMovement = new MiningMachineMovement();

    @Autowired
    private MiningMachineRepository repository;

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine newMiningMachine = new MiningMachine(name);
        repository.save(newMiningMachine);
        return newMiningMachine.getUuid();
    }

    /**
     * This method adds a new mining machine
     *
     * @param miningMachine a new mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(MiningMachine miningMachine) {
        repository.save(miningMachine);
        return miningMachine.getUuid();
    }

    /**
     * This method lets the mining machine execute a task.
     *
     * @param miningMachineId the ID of the mining machine
     * @param task            the given task
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on squares with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a obstacle or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        if (repository.findById(miningMachineId).isPresent()) {
            return miningMachineMovement.move(task, miningMachineId);
        }
        return false;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return repository.findById(miningMachineId).get().getUuid();
    }

    /**
     * This method returns the point a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getPoint(UUID miningMachineId) {
        return repository.findById(miningMachineId).get().getPoint();
    }


    /**
     * This method returns a requested mining machine
     * @param uuid the ID of the mining machine
     * @return requested mining machine entity
     */
    public MiningMachine findById(UUID uuid){
        return repository.findById(uuid).get();
    }

    /**
     * This method returns a list with all mining machines in the repository
     * @return Iterable of miningig machines
     */
    public Iterable<MiningMachine> findAll(){
        return repository.findAll();
    }

    /**
     * This methode will change the name of an existing mining machine
     * @param uuid the ID of the mining machine
     * @param name the new name
     */
    public void changeName(UUID uuid, String name){
        if(repository.findById(uuid).isPresent()) {
            MiningMachine miningMachine = repository.findById(uuid).get();
            miningMachine.setName(name);
            repository.save(miningMachine);
        }
    }

    /**
     * This methode will change the name of an existing mining machine
     * @param miningMachine with a new name and a exisiting id
     */
    public void changeName(MiningMachine miningMachine){
        repository.save(miningMachine);
    }


    /**
     * This method returns the task history of a mining machine
     * @param uuid the ID of the mining machine
     * @return List of tasks
     */
    public List<Task> getTaskHistory (UUID uuid){
        return repository.findById(uuid).get().getTasks();
    }

    /**
     *This method will set the task list to null
     * @param uuid the ID of the mining machine
     */
    public void deleteTaskHistory(UUID uuid){
        repository.findById(uuid).get().deleteTaskHistory();
    }


    public void deleteById(UUID uuid){
        repository.deleteById(uuid);
    }

}
