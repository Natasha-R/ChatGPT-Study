package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.IComponent;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;


import java.util.Optional;
import java.util.UUID;


@Service
public class MiningMachineService {

    @Autowired
    MiningMachineRepository miningMachineRepository;

    private FieldService fieldService;
    private TransportTechnologyService transportTechnologyService;

    @Autowired
    public MiningMachineService(FieldService fieldService1, TransportTechnologyService transportTechnologyService1) {
        this.fieldService = fieldService1;
        this.transportTechnologyService = transportTechnologyService1;
    }
    
    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachineRepository.save(miningMachine);
        return miningMachine.getUUID();
    }

    /**
     * This method lets the mining machine execute a order.
     * @param miningMachineId the ID of the mining machine
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a wall or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Order order) {
        Optional<MiningMachine> miningMachineOptional = miningMachineRepository.findById(miningMachineId);
        if (miningMachineOptional.isEmpty()) return false;
        Boolean result = miningMachineOptional.get().executeOrder(order, transportTechnologyService, fieldService.getFieldRepository());
        miningMachineRepository.save(miningMachineOptional.get());
        return result;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the point a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the point of the mining machine
     */
    public Point getMiningMachinePoint(UUID miningMachineId){
        throw new UnsupportedOperationException();
    }
}
