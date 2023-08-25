package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.UUID;

@Service
public class MiningMachineService {

    private final FieldRepository fieldRepository;
    private final MiningMachineRepository miningMachineRepository;
    private final TransportSystemRepository transportSystemRepository;
    private final MachineCommander machineCommander;

    public MiningMachineService(FieldRepository fieldRepository, MiningMachineRepository miningMachineRepository, TransportSystemRepository transportSystemRepository, MachineCommander machineCommander) {
        this.fieldRepository = fieldRepository;
        this.miningMachineRepository = miningMachineRepository;
        this.transportSystemRepository = transportSystemRepository;
        this.machineCommander = machineCommander;
    }

    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */

    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width,height);
        fieldRepository.save(field);
        return field.getFieldID();
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */

    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        transportSystemRepository.save(transportSystem);
        return transportSystem.getTsID();
    }

    /*
     * @param transportSystemId the transport system which is used by the connection
     */
    public Field searchfieldbyID(UUID id){
        Optional<Field> field = fieldRepository.findById(id);
        if (field.isPresent()){
            return field.get();
        }
        else throw new RuntimeException("field not found");
    }

    public UUID addConnection(UUID transportSystemId, UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate) {
        Field source = searchfieldbyID(sourceFieldId);
        Field destination = searchfieldbyID(destinationFieldId);
        Connection connection = new Connection(source,sourceCoordinate,destination,destinationCoordinate);
        isConnectionInBounds(sourceFieldId,sourceCoordinate,destinationFieldId, destinationCoordinate);
        for (TransportSystem t : transportSystemRepository.findAll()){
            if(transportSystemId == t.getTsID()) t.getConnections().add(connection);
        }

        return connection.getSourceField().getFieldID();
    }

    void isConnectionInBounds(UUID sourceFieldId, Point sourceCoordinate, UUID destinationFieldId, Point destinationCoordinate){
        for (Field value : fieldRepository.findAll()) {
            if(value.getFieldID()==destinationFieldId){
                if(value.getWidth() < destinationCoordinate.getX() || value.getHeight() < destinationCoordinate.getY()) throw new RuntimeException("destination Out of Bounds");
            }
            if (value.getFieldID() == sourceFieldId) {
                if(sourceCoordinate.getX() > value.getWidth() || sourceCoordinate.getY() > value.getHeight())throw new RuntimeException("source out of bounds");
            }
        }
    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */

    public void addBarrier(UUID fieldId, Barrier barrierString) {
        for (Field value : fieldRepository.findAll()) {
            if (value.getFieldID() == fieldId) {
              /*  if(barrierString.getStart().getX() > value.getWidth()
                        || barrierString.getEnd().getX() > value.getWidth()
                        || barrierString.getStart().getY() > value.getHeight()
                        || barrierString.getEnd().getY() > value.getHeight())throw new RuntimeException("barrier out of bounds");

               */
                value.getBarriers().add(barrierString);
            }
        }
    }


    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */

    public UUID addMiningMachine(String name) {
        MiningMachine machine = new MiningMachine(name);
        miningMachineRepository.save(machine);
        return machine.getMiningMachineId();
    }


    public Point getPoint(UUID miningMachineId){
        for (MiningMachine miningMachine : miningMachineRepository.findAll()) {
            if (miningMachine.getMiningMachineId() == miningMachineId) {
                return miningMachine.getPoint();
            }
        }
        return null;
    }

    public Boolean executeCommand(UUID miningMachineId, Command commandString) {
        return machineCommander.executeCommand(miningMachineId,commandString);
    }
}
