package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.Movable;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystemRepository;

import java.util.List;
import java.util.UUID;


@Service
public class MiningMachineService {

    @Autowired
    private MiningMachineRepository miningMachineRepository;
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private TransportSystemRepository transportSystemRepository;


    public MiningMachineRepository getMiningMachineRepository(){
        return miningMachineRepository;
    }
    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachineRepository.save(miningMachine);
        return miningMachine.getId();
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
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Task task) {
        Movable miningMachine = miningMachineRepository.getMiningMachineByMiningMachineId(miningMachineId);
        if(miningMachine == null)throw new NullPointerException();
        return miningMachine.executeCommand(task, (List<Field>) fieldRepository.findAll(), (List) miningMachineRepository.findAll(), (List<TransportSystem>) transportSystemRepository.findAll());
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        UUID miningMachineFieldId = miningMachineRepository.getMiningMachineByMiningMachineId(miningMachineId).getFieldId();
        if(miningMachineFieldId == null) throw new NullPointerException();
        return miningMachineFieldId;
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        Movable miningMachine = miningMachineRepository.getMiningMachineByMiningMachineId(miningMachineId);
        if(miningMachine == null) throw new NullPointerException();
        return miningMachine.getCoordinate();
    }
}
