package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class MachineMovement implements Movable {

    private final Map<UUID, MiningMachine> miningMachines;
    private final Map<UUID, Field> fields;
    private final Map<UUID, Connection> connections;

    public MachineMovement(Map<UUID, MiningMachine> miningMachines, Map<UUID, Field> fields, Map<UUID, Connection> connections) {
        this.miningMachines = miningMachines;
        this.fields = fields;
        this.connections = connections;
    }

    @Override
    public Boolean moveTo(UUID miningMachineId, Command moveCommand) {
        MiningMachine machine = miningMachines.get(miningMachineId);
        boolean status;

        if (machine.getState() == MachineDeploymentState.HIBERNATING && moveCommand.getCommandType() == CommandType.ENTER) {
            UUID deploymentFieldId = moveCommand.getGridId();
            status = deployMiningMachine(miningMachineId, deploymentFieldId);
        } else if (machine.getState() == MachineDeploymentState.HIBERNATING) {
            throw new IllegalArgumentException(machine.getMachineName() + " is sad, it has not been deployed on any field yet");
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
        MiningMachine machine = miningMachines.get(miningMachineId);
        Coordinate dummyPosition = new Coordinate(0, 0);
        if (checkMachineMap(dummyPosition, deploymentFieldId)) return false;
        else {
            machine.setField(fields.get(deploymentFieldId));
            machine.setPosition(0, 0);
            machine.setState(MachineDeploymentState.DEPLOYED);
            miningMachines.put(machine.getId(), machine);
            return true;
        }
    }

    public Boolean moveToAnotherField(UUID miningMachineId, UUID destinationFieldId) {
        MiningMachine machine = miningMachines.get(miningMachineId);
        UUID sourceFieldId = machine.getCurrentField().getId();
        boolean traversable = false;
        for (Map.Entry<UUID, Connection> f : connections.entrySet()) {
            if (f.getValue().getSourceFieldId() == fields.get(sourceFieldId).getId()
                    && f.getValue().getDestinationFieldId() == fields.get(destinationFieldId).getId()
                    && f.getValue().getSourceCoordinate().getX().equals(machine.getPosition().getX())
                    && f.getValue().getSourceCoordinate().getY().equals(machine.getPosition().getY())) {
                machine.setPosition(f.getValue().getDestinationCoordinate().getX(), f.getValue().getDestinationCoordinate().getY());
                machine.setField(fields.get(destinationFieldId));
                miningMachines.put(machine.getId(), machine);
                traversable = true;
            }
        }
        return traversable;
    }

    public Boolean moveNorth(UUID miningMachineId, int steps) {
        MiningMachine machine;
        for (int i = 0; i < steps; i++) {
            machine = miningMachines.get(miningMachineId);
            Coordinate position = new Coordinate(machine.getPosition().getX(), (machine.getPosition().getY() + 1));
            if (machine.getPosition().getY() == (machine.getCurrentField().getHeight() - 1)
                    || (checkMachineMap(position, machine.getCurrentField().getId()))
                    || (checkBarriers(machine.getPosition(), position, machine.getCurrentField()))) {
                break;
            } else {
                machine.setPosition(position);
                miningMachines.put(machine.getId(), machine);
            }
        }
        return true;
    }

    public Boolean moveEast(UUID miningMachineId, int steps) {
        MiningMachine machine;
        for (int i = 0; i < steps; i++) {
            machine = miningMachines.get(miningMachineId);
            Coordinate position = new Coordinate((machine.getPosition().getX() + 1), machine.getPosition().getY());
            if (machine.getPosition().getX() == (machine.getCurrentField().getWidth() - 1)
                    || (checkMachineMap(position, machine.getCurrentField().getId()))
                    || (checkBarriers(machine.getPosition(), position, machine.getCurrentField()))) {
                break;
            } else {
                machine.setPosition(position);
                miningMachines.put(machine.getId(), machine);
            }
        }
        return true;
    }

    public Boolean moveSouth(UUID miningMachineId, int steps) {
        MiningMachine machine;
        for (int i = 0; i < steps; i++) {
            machine = miningMachines.get(miningMachineId);
            if (machine.getPosition().getY() == 0) break;
            Coordinate position = new Coordinate(machine.getPosition().getX(), (machine.getPosition().getY() - 1));
            if (checkMachineMap(position, machine.getCurrentField().getId())
                    || (checkBarriers(machine.getPosition(), position, machine.getCurrentField()))) {
                break;
            } else {
                machine.setPosition(position);
                miningMachines.put(machine.getId(), machine);
            }
        }
        return true;
    }

    public Boolean moveWest(UUID miningMachineId, int steps) {
        MiningMachine machine;
        for (int i = 0; i < steps; i++) {
            machine = miningMachines.get(miningMachineId);
            if (machine.getPosition().getX() == 0) break;
            Coordinate position = new Coordinate((machine.getPosition().getX() - 1), machine.getPosition().getY());
            if (checkMachineMap(position, machine.getCurrentField().getId())
                    || (checkBarriers(machine.getPosition(), position, machine.getCurrentField()))) {
                break;
            } else {
                machine.setPosition(position);
                miningMachines.put(machine.getId(), machine);
            }
        }
        return true;
    }

    public Boolean checkMachineMap(Coordinate position, UUID incomingFieldID) {
        boolean occupied = false;
        for (Map.Entry<UUID, MiningMachine> f : miningMachines.entrySet()) {
            if (f.getValue().getPosition() != null) {
                occupied = f.getValue().getCurrentField().getId() == fields.get(incomingFieldID).getId()
                        && position.getX().equals(f.getValue().getPosition().getX())
                        && position.getY().equals(f.getValue().getPosition().getY());
            }
        }
        return occupied;
    }

    public Boolean checkBarriers(Coordinate oldPosition, Coordinate newPosition, Field field) {
        boolean barred = false;
        ArrayList<Barrier> barrier = fields.get(field.getId()).getBarriers();
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