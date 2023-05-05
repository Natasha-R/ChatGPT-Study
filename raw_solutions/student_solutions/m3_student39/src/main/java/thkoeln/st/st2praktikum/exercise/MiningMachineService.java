package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.FieldRepository;
import thkoeln.st.st2praktikum.exercise.repositories.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;

import java.util.Optional;
import java.util.UUID;


@Service
public class MiningMachineService {
    protected FieldRepository fieldRepository;
    protected TransportTechnologyRepository transportTechnologyRepository;
    protected MiningMachineRepository miningMachineRepository;

    @Autowired
    public MiningMachineService(FieldRepository fieldRepository, TransportTechnologyRepository transportTechnologyRepository, MiningMachineRepository miningMachineRepository){
        this.fieldRepository = fieldRepository;
        this.miningMachineRepository = miningMachineRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        UUID newFieldUUID = UUID.randomUUID();
        fieldRepository.save(new Field(newFieldUUID, height, width));
        return newFieldUUID;
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        fieldRepository.findById(fieldId).get().addBarrier(barrier);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        return transportTechnologyRepository.save(new TransportTechnology(technology)).getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceFieldId, Vector2D sourceVector2D, UUID destinationFieldId, Vector2D destinationVector2D) {
        UUID newConnectionUUID = UUID.randomUUID();
        if(fieldRepository.findById(sourceFieldId).get().checkPositionOnMap(sourceVector2D) && fieldRepository.findById(destinationFieldId).get().checkPositionOnMap(destinationVector2D)){
            transportTechnologyRepository.findById(transportTechnologyId).get().getConnections().add(new Connection(newConnectionUUID, sourceFieldId, destinationFieldId, sourceVector2D, destinationVector2D));
            fieldRepository.findById(sourceFieldId).get().addTransportTechnology(transportTechnologyRepository.findById(transportTechnologyId).get());
            return newConnectionUUID;
        }else{
            throw new RuntimeException("source or destination Position are out of bounds");
        }
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
}
