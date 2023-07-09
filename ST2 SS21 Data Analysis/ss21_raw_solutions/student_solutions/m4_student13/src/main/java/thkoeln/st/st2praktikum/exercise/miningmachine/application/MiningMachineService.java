package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.ConnectionRepository;


import java.util.UUID;


@Service
public class MiningMachineService {

    private MiningMachineRepository miningMachineRepository;
    private FieldRepository fieldRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository,
                                FieldRepository fieldRepository,
                                ConnectionRepository connectionRepository){
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
        this.connectionRepository = connectionRepository;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {

        MiningMachine miningMachine = new MiningMachine(name);
        return miningMachineRepository.save(miningMachine).getId();
        //throw new UnsupportedOperationException();
    }

    /**
     * This method lets the mining machine execute a task.
     * @param miningMachineId the ID of the mining machine
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        return miningMachineRepository.findById(miningMachineId).get().
                execute(task,fieldRepository,connectionRepository);
        //throw new UnsupportedOperationException();
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){

        return miningMachineRepository.findById(miningMachineId).get().getRoom().getField().getUuid();
        //throw new UnsupportedOperationException();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        return miningMachineRepository.findById(miningMachineId).get().getPoint();
        //throw new UnsupportedOperationException();
    }

    public Iterable<MiningMachine> getAllMiningMachines(){
        return miningMachineRepository.findAll();
    }
}
