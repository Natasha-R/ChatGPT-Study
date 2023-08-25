package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;


import java.util.Optional;
import java.util.UUID;


@Service
public class MiningMachineService {
    protected MiningMachineRepository miningMachineRepository;
    protected TransportTechnologyRepository transportTechnologyRepository;
    protected FieldRepository fieldRepository;

    @Autowired
    public MiningMachineService(TransportTechnologyRepository transportTechnologyRepository, FieldRepository fieldRepository, MiningMachineRepository miningMachineRepository){
        this.miningMachineRepository = miningMachineRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.fieldRepository = fieldRepository;
    }
    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        UUID miningMachineNewUUID = UUID.randomUUID();
        miningMachineRepository.save(new MiningMachine(miningMachineNewUUID, name));
        return miningMachineNewUUID;
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
        MiningMachine miningMachine = miningMachineRepository.findById(miningMachineId).get();
        miningMachine.getListOfOrders().add(order);

        if(order.getOrderType() == OrderType.ENTER){
            if(fieldRepository.findById(order.getGridId()).get().addBlockingObject(new Vector2D(0,0))){
                miningMachine.placeOnMap(fieldRepository.findById(order.getGridId()).get() ,new Vector2D(0,0));
                return true;
            }
            return false;
        }
        else if(order.getOrderType() == OrderType.TRANSPORT){
            return miningMachine.useConnection(fieldRepository.findById(order.getGridId()).get());
        }
        else{
            return miningMachine.move(order);
        }
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachineRepository.findById(miningMachineId).get().getMapOfTheMiningMachine().getId();
    }

    /**
     * This method returns the vector-2D a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the vector-2D of the mining machine
     */
    public Vector2D getMiningMachineVector2D(UUID miningMachineId){
        return miningMachineRepository.findById(miningMachineId).get().getPosition();
    }

    public Iterable<MiningMachine> findAllMiningMachines(){
        return miningMachineRepository.findAll();
    }

    public MiningMachineRepository getMiningMachineRepository(){
        return miningMachineRepository;
    }

    public boolean changeName(String name, UUID id){
        Optional<MiningMachine> miningMachine = miningMachineRepository.findById(id);
        if(miningMachine.isEmpty()){
            return false;
        }else{
            miningMachine.get().setName(name);
            return true;
        }
    }
}
