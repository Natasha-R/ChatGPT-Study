package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.FieldConnectionRepo;
import thkoeln.st.st2praktikum.exercise.repositories.FieldRepository;
import thkoeln.st.st2praktikum.exercise.repositories.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;

import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class MiningMachineService {

    private final MiningMachineController controller = new MiningMachineController();

    @Autowired
    FieldRepository fieldRepo;
    @Autowired
    MiningMachineRepository miningMachineRepo;
    @Autowired
    TransportTechnologyRepository transportTechnologyRepo;
    @Autowired
    FieldConnectionRepo fieldConnectionRepo;

    /**
     * This method creates a new field.
     *
     * @param height the height of the field
     * @param width  the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field field = new Field(width, height);
        fieldRepo.save(field);
        return field.getId();
    }

    /**
     * This method adds a barrier to a given field.
     *
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = fieldRepo.findById(fieldId).orElseThrow();
        field.addBarrier(barrier);
        fieldRepo.save(field);
    }

    /**
     * This method adds a transport technology
     *
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologyRepo.save(transportTechnology);
        return transportTechnology.getId();
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
        Field source = fieldRepo.findById(sourceFieldId).orElseThrow();
        Field destination = fieldRepo.findById(destinationFieldId).orElseThrow();
        FieldConnection connection = new FieldConnection(
                source,
                destination,
                sourceCoordinate,
                destinationCoordinate,
                transportTechnologyRepo.findById(transportTechnologyId).orElseThrow()
        );
        fieldConnectionRepo.save(connection);
        return connection.getId();
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        miningMachineRepo.save(miningMachine);
        return miningMachine.getId();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param command         the given command
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on grid cells with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        MiningMachine machine = miningMachineRepo.findById(miningMachineId).orElseThrow();
        Field destination = null;
        if (command.getFieldId() != null) {
            destination = fieldRepo.findById(command.getFieldId()).orElseThrow();
        }
        FieldConnection connection = null;
        if (machine.getField() != null && destination != null) {
            connection = fieldConnectionRepo.findBySource_IdAndDestination_Id(machine.getFieldId(), destination.getId()).orElse(null);
            if (command.getCommandType() == CommandType.TRANSPORT && connection == null) {
                throw new NoSuchElementException();
            }
        }
        boolean success = controller.executeCommand(machine, destination, connection, command);
        miningMachineRepo.save(machine);
        return success;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return miningMachineRepo.findById(miningMachineId).orElseThrow().getFieldId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId) {
        return miningMachineRepo.findById(miningMachineId).orElseThrow().getCoordinate();
    }
}
