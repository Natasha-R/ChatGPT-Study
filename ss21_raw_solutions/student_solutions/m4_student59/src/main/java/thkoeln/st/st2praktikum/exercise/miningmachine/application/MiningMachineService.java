package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;


import java.util.UUID;


@Service
public class MiningMachineService {

    private final MiningMachineRepository miningMachineRepository;
    private final FieldRepository fieldRepository;

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository, FieldRepository fieldRepository){
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
    }
    
    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        final MiningMachine miningMachine = new MiningMachine(name);
        this.miningMachineRepository.save(miningMachine);
        return miningMachine.getMiningmaschineId();
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        return this.miningMachineRepository.findById(miningMachineId).get().executeCommand(order, this.miningMachineRepository, this.fieldRepository);
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return this.miningMachineRepository.findById(miningMachineId).get().getFieldId();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        return this.miningMachineRepository.findById(miningMachineId).get().getPoint();
    }
}
