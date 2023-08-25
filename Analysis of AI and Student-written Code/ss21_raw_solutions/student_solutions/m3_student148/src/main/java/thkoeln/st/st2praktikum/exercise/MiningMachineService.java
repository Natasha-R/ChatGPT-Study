package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MiningMachineService {
    @Getter
    public HashMap<UUID, MiningMachine> miningMachines = new HashMap<>();
    @Getter
    public HashMap<UUID, Field> fields = new HashMap<>();
    @Getter
    public HashMap<UUID, TransportTechnology> transportTechnologies = new HashMap<>();

    public HashMap<UUID, Connection> connections = new HashMap<>();

    private final FieldRepository fieldRepository;
    private final MiningMachineRepository miningMachineRepository;
    private final TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    private MiningMachineService(MiningMachineRepository miningMachineRepository,
                                 FieldRepository fieldRepository,
                                 TransportTechnologyRepository transportTechnologyRepository) {
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
    }

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(height, width);
        fields.put(field.getId(), field);
        return fieldRepository.save(field).getId();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = fields.get(fieldId);
        if (barrier.getEnd().getX() <= field.getWidth() && barrier.getEnd().getY() <= field.getHeight()) {
            field.getBarriers().add(barrier);
            fields.put(field.getId(), field);
        } else throw new IllegalArgumentException("A barrier can't extend beyond the field it's being placed on!");
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologies.put(transportTechnology.getId(), transportTechnology);
        return transportTechnologyRepository.save(transportTechnology).getId();
    }

    /**
     * This method adds a traversable connection between two fields based on a transport technology. Connections only work in one direction.
     *
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceFieldId         the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate      the coordinate of the entry point
     * @param destinationFieldId    the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId, Coordinate destinationCoordinate) {
        Field sourceField = fields.get(sourceFieldId);
        Field destinationField = fields.get(destinationFieldId);
        TransportTechnology transportTechnology = transportTechnologies.get(transportTechnologyId);
        if (sourceCoordinate.getX() < sourceField.getWidth()
                && sourceCoordinate.getY() < sourceField.getHeight()
                && destinationCoordinate.getX() < destinationField.getWidth()
                && destinationCoordinate.getY() < destinationField.getHeight()) {
            Connection connection = new Connection(transportTechnology, sourceField, sourceCoordinate, destinationField, destinationCoordinate);
            connections.put(connection.getId(), connection);
            transportTechnology.setConnections(connection);
            return connection.getId();
        } else throw new IllegalArgumentException("A connection can't exist beyond the fields boundries!");
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachines.put(miningMachine.getId(), miningMachine);
        return miningMachineRepository.save(miningMachine).getId();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param command         the given command
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on cells with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        Movable move = new Movement(this.miningMachines, this.fields, this.transportTechnologies, this.connections);
        MiningMachine miningMachine = miningMachines.get(miningMachineId);
        Boolean result = move.moveTo(miningMachineId, command);
        miningMachineRepository.save(miningMachine);
        return result;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        MiningMachine machine = miningMachines.get(miningMachineId);
        if (machine.getState() != MachineDeploymentState.HIBERNATING) {
            return machine.getCurrentField().getId();
        }
        throw new IllegalArgumentException(machine.getMachineName() + " is sad, it has not been deployed on any field yet");
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId) {
        return miningMachines.get(miningMachineId).getCoordinate();
    }
}
