package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Movement implements Movable {

    public Map<UUID, MiningMachine> miningMachines;
    public  Map<UUID, Field> fields;
    public  Map<UUID, Connection> connections;

    public Movement(Map<UUID, MiningMachine> miningMachines, Map<UUID, Field> fields, Map<UUID, Connection> connections){
        this.miningMachines = miningMachines;
        this.fields = fields;
        this.connections = connections;
    }

    @Override
    public Boolean moveTo(UUID miningMachineId, String MoveCommandString) {
        boolean status;
        String commandString = MoveCommandString.replace("[", "");
        commandString = commandString.replace("]", "");
        String[] moveArray = commandString.split(",");
        status = movement(miningMachineId, moveArray);
        return status;
    }

    public Boolean movement(UUID miningMachineId, String[] moveArray) {
        MiningMachine machine = miningMachines.get(miningMachineId);
        String moveDirection = moveArray[0];
        boolean status = false;

        if (machine.getState() == MachineDeploymentState.HIBERNATING && moveDirection.equals("en")) {
            Position dummyPosition = new Position(0, 0);
            if (checkMachineMap(dummyPosition, UUID.fromString(moveArray[1]))) status = false;
            else {
                machine.setField(fields.get(UUID.fromString(moveArray[1])));
                machine.setPosition(0, 0);
                machine.setState(MachineDeploymentState.DEPLOYED);
                miningMachines.put(machine.getId(), machine);
                status = true;
            }
        } else if (machine.getState() == MachineDeploymentState.HIBERNATING) {
            status = false;
        } else if (moveDirection.equals("tr")) {
            machine = miningMachines.get(miningMachineId);
            UUID sourceFieldId = machine.getCurrentField().getId();
            UUID destinationFieldId = UUID.fromString(moveArray[1]);
            for (Map.Entry<UUID, Connection> f : connections.entrySet()) {
                if (f.getValue().getSourceFieldId() == fields.get(sourceFieldId).getId()
                        && f.getValue().getDestinationFieldId() == fields.get(destinationFieldId).getId()
                        && f.getValue().getSourceCoordinate().getX() == machine.getPosition().getX()
                        && f.getValue().getSourceCoordinate().getY() == machine.getPosition().getY()) {
                    machine.setPosition(f.getValue().getDestinationCoordinate().x, f.getValue().getDestinationCoordinate().y);
                    machine.setField(fields.get(destinationFieldId));
                    miningMachines.put(machine.getId(), machine);
                    status = true;
                }
            }
        } else {
            int steps = Integer.parseInt(moveArray[1]);
            machine = miningMachines.get(miningMachineId);
            switch (moveDirection) {
                case "no":
                    for (int i = 0; i < steps; i++) {
                        machine = miningMachines.get(miningMachineId);
                        Position position = new Position(machine.getPosition().getX(), machine.getPosition().getY());
                        position.setY(position.getY() + 1);
                        if (machine.getPosition().getY() == (machine.getCurrentField().getHeight() - 1)
                                || (checkMachineMap(position, machine.getCurrentField().getId()))
                                || (checkBarriers(machine.getPosition(), position, machine.getCurrentField()))) {
                            break;
                        } else {
                            machine.setPosition(position);
                            miningMachines.put(machine.getId(), machine);
                        }
                    }
                    status = true;
                    break;
                case "ea":
                    for (int i = 0; i < steps; i++) {
                        machine = miningMachines.get(miningMachineId);
                        Position position = new Position(machine.getPosition().getX(), machine.getPosition().getY());
                        position.setX(position.getX() + 1);
                        if (machine.getPosition().getX() == (machine.getCurrentField().getWidth() - 1)
                                || (checkMachineMap(position, machine.getCurrentField().getId()))
                                || (checkBarriers(machine.getPosition(), position, machine.getCurrentField()))) {
                            break;
                        } else {
                           // machine.setPosition(dummyPosition.getX(), dummyPosition.getY());
                            machine.setPosition(position);
                            miningMachines.put(machine.getId(), machine);
                        }
                    }
                    status = true;
                    break;
                case "so":
                    for (int i = 0; i < steps; i++) {
                        machine = miningMachines.get(miningMachineId);
                        Position position = new Position(machine.getPosition().getX(), machine.getPosition().getY());
                        position.setY(position.getY() - 1);
                        if (machine.getPosition().getY() == 0
                                || (checkMachineMap(position, machine.getCurrentField().getId()))
                                || (checkBarriers(machine.getPosition(), position, machine.getCurrentField()))) {
                            break;
                        } else {
                            machine.setPosition(position);
                            miningMachines.put(machine.getId(), machine);
                        }
                    }
                    status = true;
                    break;
                case "we":
                    for (int i = 0; i < steps; i++) {
                        machine = miningMachines.get(miningMachineId);
                        Position position = new Position(machine.getPosition().getX(), machine.getPosition().getY());
                        position.setX(position.getX() - 1);
                        if (machine.getPosition().getX() == 0
                                || (checkMachineMap(position, machine.getCurrentField().getId()))
                                || (checkBarriers(machine.getPosition(), position, machine.getCurrentField()))) {
                            break;
                        } else {
                            machine.setPosition(position);
                            miningMachines.put(machine.getId(), machine);
                        }
                    }
                    status = true;
                    break;
                default:
                    status = false;
            }
        }
        return status;
    }

    public Boolean checkBarriers(Position oldPosition, Position newPosition, Field field) {
        boolean barred = false;
        ArrayList<Barrier> barrier = fields.get(field.getId()).getBarriers();
        for (Barrier value : barrier) {
            if (value.getBarrierXStart() == value.getBarrierXEnd()) {
                if (oldPosition.getY() >= value.getBarrierYStart() && oldPosition.getY() < value.getBarrierYEnd()) {
                    if (newPosition.getX() == value.getBarrierXStart() && oldPosition.getX() < newPosition.getX())
                        barred = true;
                    else if (oldPosition.getX() == value.getBarrierXStart() && oldPosition.getX() > newPosition.getX())
                        barred = true;
                }
            } else if (value.getBarrierYStart() == value.getBarrierYEnd()) {
                if (oldPosition.getX() >= value.getBarrierXStart() && oldPosition.getX() < value.getBarrierXEnd()) {
                    if (newPosition.getY() == value.getBarrierYStart() && oldPosition.getY() < newPosition.getY())
                        barred = true;
                    else if (oldPosition.getY() == value.getBarrierYStart() && oldPosition.getY() > newPosition.getY())
                        barred = true;
                }
            }
        }
        return barred;
    }

    public Boolean checkMachineMap(Position position, UUID incomingFieldID) {
        boolean occupied = false;
        for (Map.Entry<UUID, MiningMachine> f : miningMachines.entrySet()) {
            if (f.getValue().getPosition() != null) {
                occupied =f.getValue().getCurrentField().getId() == fields.get(incomingFieldID).getId()
                        && position.getX() == f.getValue().getPosition().getX()
                        && position.getY() == f.getValue().getPosition().getY();
                }
            }
        return occupied;
    }
}