package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.FieldConnection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.FieldConnectionRepository;


import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class MiningMachineService {

    private final MiningMachineRepository miningMachineRepository;
    private final FieldRepository fieldRepository;
    private final FieldConnectionRepository fieldConnectionRepository;

    @Autowired
    public MiningMachineService(MiningMachineRepository miningMachineRepository,
                                FieldRepository fieldRepository,
                                FieldConnectionRepository fieldConnectionRepository) {
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
        this.fieldConnectionRepository = fieldConnectionRepository;
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
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another field
     * ENTER for setting the initial field where a mining machine is placed. The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        MiningMachine machine = miningMachineRepository.findById(miningMachineId).orElseThrow();
        Field destination = null;
        if (command.getGridId() != null) {
            destination = fieldRepository.findById(command.getGridId()).orElseThrow();
        }
        FieldConnection connection = null;
        if (machine.getField() != null && destination != null) {
            connection = fieldConnectionRepository.findBySource_IdAndDestination_Id(machine.getFieldId(), destination.getId()).orElse(null);
            if (command.getCommandType() == CommandType.TRANSPORT && connection == null) {
                throw new NoSuchElementException();
            }
        }
        boolean success = executeCommand(machine, destination, connection, command);
        miningMachineRepository.save(machine);
        return success;
    }

    private boolean executeCommand(MiningMachine miningMachine, Field field, FieldConnection connection, Command command) {
        switch (command.getCommandType()) {
            case TRANSPORT:
                miningMachine.addCommand(command);
                return transportMiningMachine(miningMachine, connection);
            case ENTER:
                miningMachine.addCommand(command);
                return placeMiningMachine(miningMachine, field);
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                miningMachine.addCommand(command);
                return moveMiningMachine(
                        miningMachine,
                        command.getCommandType(),
                        command.getNumberOfSteps()
                );
            default:
                throw new UnsupportedOperationException("There is no command with that name");
        }
    }

    private boolean moveMiningMachine(MiningMachine miningMachine, CommandType direction, int distance) {
        Field sourceField = miningMachine.getField();
        if (sourceField == null) {
            throw new RuntimeException("The MiningMachine isn't on any field");
        }
        switch (direction) {
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                sourceField.moveMiningMachine(miningMachine, direction, distance);
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean transportMiningMachine(MiningMachine miningMachine, FieldConnection fieldConnection) {
        if (fieldConnection == null)
            throw new RuntimeException("This Mining Machine isn't on any Field");
        if (!fieldConnection.isOnEntryCell(miningMachine.getCoordinate()))
            return false;

        try {
            fieldConnection.getDestination().addMiningMachine(miningMachine, fieldConnection.getDestinationCoordinates());
        } catch (Exception e) {
            return false;
        }
        fieldConnection.getSource().removeMiningMachine(miningMachine.getId());
        return true;
    }

    private boolean placeMiningMachine(MiningMachine miningMachine, Field field) {
        if (miningMachine.getField() != null) {
            throw new RuntimeException("This machine is already on a Field");
        }
        try {
            field.addMiningMachine(miningMachine, new Coordinate(0, 0));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        return miningMachineRepository.findById(miningMachineId).orElseThrow().getFieldId();
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId){
        return miningMachineRepository.findById(miningMachineId).orElseThrow().getCoordinate();
    }
}
