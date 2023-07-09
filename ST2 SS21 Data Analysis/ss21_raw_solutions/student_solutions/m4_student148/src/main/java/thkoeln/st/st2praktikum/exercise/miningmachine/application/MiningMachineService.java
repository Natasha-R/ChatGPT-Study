package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import thkoeln.st.st2praktikum.exercise.field.domain.*;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.*;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.*;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Getter
@Service
public class MiningMachineService {

    private HashMap<UUID, Connection> connections;
    private HashMap<UUID, Field> fields;

    private final MiningMachineRepository miningMachineRepository;
    private final TransportTechnologyRepository transportTechnologyRepository;
    private final FieldRepository fieldRepository;

    @Autowired
    private MiningMachineService(MiningMachineRepository miningMachineRepository,
                                 TransportTechnologyRepository transportTechnologyRepository,
                                 FieldRepository fieldRepository) {
        this.miningMachineRepository = miningMachineRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
        this.fieldRepository = fieldRepository;
    }

    /**
     * This method adds a new mining machine
     *
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMachine miningMachine = new MiningMachine(name);
        return miningMachineRepository.save(miningMachine).getId();
    }

    /**
     * This method lets the mining machine execute a command.
     *
     * @param miningMachineId the ID of the mining machine
     * @param command         the given command
     *                        NORTH, WEST, SOUTH, EAST for movement
     *                        TRANSPORT for transport - only works on cells with a connection to another field
     *                        ENTER for setting the initial field where a mining machine is placed.
     *                        The mining machine will always spawn at (0,0) of the given field.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the mining machine hits a barrier or
     * another mining machine, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, Command command) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        MiningMachine miningMachine = found.get();
        miningMachine.getCommands().add(command);
        Boolean result = moveTo(miningMachineId, command);
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
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        MiningMachine machine = found.get();
        if (machine.getState() != MachineDeploymentState.HIBERNATING) {
            return machine.getCurrentField().getId();
        }
        throw new IllegalArgumentException(machine.getName() + " is sad, it has not been deployed on any field yet");
    }

    /**
     * This method returns the coordinate a mining machine is standing on
     *
     * @param miningMachineId the ID of the mining machine
     * @return the coordinate of the mining machine
     */
    public Coordinate getMiningMachineCoordinate(UUID miningMachineId) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        MiningMachine miningMachine = found.get();
        return miningMachine.getCoordinate();
    }

    public Boolean moveTo(UUID miningMachineId, Command moveCommand) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        MiningMachine machine = found.get();
        boolean status;
        if (machine.getState() == MachineDeploymentState.HIBERNATING
                && moveCommand.getCommandType() == CommandType.ENTER) {
            UUID deploymentFieldId = moveCommand.getGridId();
            status = deployMiningMachine(miningMachineId, deploymentFieldId);
        } else if (machine.getState() == MachineDeploymentState.HIBERNATING) {
            throw new IllegalArgumentException(machine.getName() +
                    " is sad, it has not been deployed on any field yet");
        } else if (moveCommand.getCommandType() == CommandType.TRANSPORT) {
            UUID destinationFieldId = moveCommand.getGridId();
            status = moveToAnotherField(miningMachineId, destinationFieldId);
        } else {
            CommandType moveDirection = moveCommand.getCommandType();
            int steps = moveCommand.getNumberOfSteps();
            switch (moveDirection) {
                case NORTH:
                    status = moveNorth(miningMachineId, steps);
                    break;
                case EAST:
                    status = moveEast(miningMachineId, steps);
                    break;
                case SOUTH:
                    status = moveSouth(miningMachineId, steps);
                    break;
                case WEST:
                    status = moveWest(miningMachineId, steps);
                    break;
                default:
                    throw new IllegalArgumentException("The command " + moveDirection + " is not recognised");
            }
        }
        return status;
    }

    public Boolean deployMiningMachine(UUID miningMachineId, UUID deploymentFieldId) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        MiningMachine machine = found.get();
        Coordinate dummyPosition = new Coordinate(0, 0);

        if (checkMachineMap(dummyPosition, deploymentFieldId)) return false;
        else {
            Optional<Field> f = fieldRepository.findById(deploymentFieldId);
            machine.setField(f.get());
            machine.setPosition(0, 0);
            machine.setState(MachineDeploymentState.DEPLOYED);
            miningMachineRepository.save(machine);
            return true;
        }
    }

    public Boolean moveToAnotherField(UUID miningMachineId, UUID destinationFieldId) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        MiningMachine machine = found.get();
        connections = TransportTechnologyService.getConnections();
        UUID sourceFieldId = machine.getCurrentField().getId();
        Optional<Field> f = fieldRepository.findById(destinationFieldId);
        Optional<Field> fl = fieldRepository.findById(sourceFieldId);
        boolean traversable = false;
        for (Map.Entry<UUID, Connection> c : connections.entrySet()) {
            if (c.getValue().getSourceFieldId() == fl.get().getId()
                    && c.getValue().getDestinationFieldId() == f.get().getId()
                    && c.getValue().getSourceCoordinate().getX().equals(machine.getCoordinate().getX())
                    && c.getValue().getSourceCoordinate().getY().equals(machine.getCoordinate().getY())) {
                machine.setPosition(c.getValue().getDestinationCoordinate().getX(),
                        c.getValue().getDestinationCoordinate().getY());
                machine.setField(f.get());
                miningMachineRepository.save(machine);
                traversable = true;
            }
        }
        return traversable;
    }

    public Boolean moveNorth(UUID miningMachineId, int steps) {
        MiningMachine machine;
        for (int i = 0; i < steps; i++) {
            Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
            machine = found.get();
            Coordinate position = new Coordinate(machine.getCoordinate().getX(), (machine.getCoordinate().getY() + 1));
            if (machine.getCoordinate().getY() == (machine.getCurrentField().getHeight() - 1)
                    || (checkMachineMap(position, machine.getCurrentField().getId()))
                    || (checkBarriers(machine.getCoordinate(), position, machine.getCurrentField()))) {
                break;
            } else {
                machine.setPosition(position);
                miningMachineRepository.save(machine);
            }
        }
        return true;
    }

    public Boolean moveEast(UUID miningMachineId, int steps) {
        MiningMachine machine;
        for (int i = 0; i < steps; i++) {
            Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
            machine = found.get();
            Coordinate position = new Coordinate((machine.getCoordinate().getX() + 1), machine.getCoordinate().getY());
            if (machine.getCoordinate().getX() == (machine.getCurrentField().getWidth() - 1)
                    || (checkMachineMap(position, machine.getCurrentField().getId()))
                    || (checkBarriers(machine.getCoordinate(), position, machine.getCurrentField()))) {
                break;
            } else {
                machine.setPosition(position);
                miningMachineRepository.save(machine);
            }
        }
        return true;
    }

    public Boolean moveSouth(UUID miningMachineId, int steps) {
        MiningMachine machine;
        for (int i = 0; i < steps; i++) {
            Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
            machine = found.get();

            if (machine.getCoordinate().getY() == 0) break;

            Coordinate position = new Coordinate(machine.getCoordinate().getX(), (machine.getCoordinate().getY() - 1));

            if (checkMachineMap(position, machine.getCurrentField().getId())
                    || (checkBarriers(machine.getCoordinate(), position, machine.getCurrentField()))) {
                break;
            } else {
                machine.setPosition(position);
                miningMachineRepository.save(machine);
            }
        }
        return true;
    }

    public Boolean moveWest(UUID miningMachineId, int steps) {
        MiningMachine machine;
        for (int i = 0; i < steps; i++) {
            Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
            machine = found.get();

            if (machine.getCoordinate().getX() == 0) break;

            Coordinate position = new Coordinate((machine.getCoordinate().getX() - 1), machine.getCoordinate().getY());

            if (checkMachineMap(position, machine.getCurrentField().getId())
                    || (checkBarriers(machine.getCoordinate(), position, machine.getCurrentField()))) {
                break;
            } else {
                machine.setPosition(position);
                miningMachineRepository.save(machine);
            }
        }
        return true;
    }

    public Boolean checkMachineMap(Coordinate position, UUID incomingFieldID) {
        boolean occupied = false;
        Optional<Field> f = fieldRepository.findById(incomingFieldID);
        Iterator<MiningMachine> machineList = miningMachineRepository.findAll().iterator();
        while (machineList.hasNext()) {
            MiningMachine machine = machineList.next();
            if (machine.getCoordinate() != null) {
                occupied = machine.getFieldId() == f.get().getId()
                        && position.getX().equals(machine.getCoordinate().getX())
                        && position.getY().equals(machine.getCoordinate().getY());
            }
        }
        return occupied;
    }

    public Boolean checkBarriers(Coordinate oldPosition, Coordinate newPosition, Field field) {
        boolean barred = false;
        Optional<Field> f = fieldRepository.findById(field.getId());
        List<Barrier> barrier = f.get().getBarriers();
        for (Barrier value : barrier) {
            if (value.getStart().getX().equals(value.getEnd().getX())) {
                if (oldPosition.getY() >= value.getStart().getY() && oldPosition.getY() < value.getStart().getY()) {
                    if (newPosition.getX().equals(value.getStart().getX()) && oldPosition.getX() < newPosition.getX())
                        barred = true;
                    else if (oldPosition.getX().equals(value.getStart().getX()) && oldPosition.getX() > newPosition.getX())
                        barred = true;
                }
            } else if (value.getStart().getY().equals(value.getEnd().getY())) {
                if (oldPosition.getX() >= value.getStart().getX() && oldPosition.getX() < value.getEnd().getX()) {
                    if (newPosition.getY().equals(value.getStart().getY()) && oldPosition.getY() < newPosition.getY())
                        barred = true;
                    else if (oldPosition.getY().equals(value.getStart().getY()) && oldPosition.getY() > newPosition.getY())
                        barred = true;
                }
            }
        }
        return barred;
    }
}
