package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Controller;

import java.util.*;

public class MiningMachineController implements Controller {

    private final Map<UUID, Field> fieldMap = new HashMap<>();
    private final Map<UUID, FieldConnection> fieldConnectionMap = new HashMap<>();
    private final Map<UUID, MiningMachine> miningMachineMap = new HashMap<>();


    @Override
    public boolean executeCommand(UUID miningMachineId, Command command) {
        switch (command.getCommandType()) {
            case TRANSPORT:
                return transportMiningMachine(miningMachineId, command.getGridId());
            case ENTER:
                return placeMiningMachine(miningMachineId, command.getGridId());
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                return moveMiningMachine(
                        miningMachineId,
                        command.getCommandType(),
                        command.getNumberOfSteps()
                );
            default:
                throw new UnsupportedOperationException("There is no command with that name");
        }
    }

    private boolean moveMiningMachine(UUID miningMachineId, CommandType direction, int distance) {
        Field sourceField = fieldMap.get(getMiningMachineFieldId(miningMachineId));
        if (sourceField == null) {
            throw new RuntimeException("The MiningMachine isn't on any field");
        }
        switch (direction) {
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                sourceField.moveMiningMachine(miningMachineId, direction, distance);
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean transportMiningMachine(UUID miningMachineId, UUID destinationFieldID) {
        FieldConnection fieldConnection = getFieldConnectionBySourceFieldId(getMiningMachineFieldId(miningMachineId));
        if (fieldConnection == null)
            throw new RuntimeException("This Mining Machine isn't on any Field");
        if (!fieldConnection.getDestinationId().equals(destinationFieldID))
            throw new RuntimeException("There is no Connection between those Fields");
        Field destinationField = fieldMap.get(destinationFieldID);
        Field sourceField = fieldMap.get(getMiningMachineFieldId(miningMachineId));

        return attemptTransport(fieldConnection, miningMachineId, sourceField, destinationField);
    }

    private boolean attemptTransport(FieldConnection fieldConnection, UUID miningMachineId, Field sourceField, Field destinationField) {
        MiningMachine miningMachine = miningMachineMap.get(miningMachineId);
        if (miningMachine == null)
            throw new ResourceNotFoundException("There is no Mining Machine with this id");
        if (!fieldConnection.isOnEntryCell(miningMachine.getCoordinate()))
            return false;

        try {
            destinationField.addMiningMachine(miningMachine, fieldConnection.getDestinationCoordinates());
        } catch (Exception e) {
            return false;
        }
        sourceField.removeMiningMachine(miningMachineId);
        return true;
    }

    private FieldConnection getFieldConnectionBySourceFieldId(UUID sourceFieldId) {
        for (FieldConnection f : fieldConnectionMap.values()) {
            if (f.getSourceId().equals(sourceFieldId))
                return f;
        }
        return null;
    }

    private boolean placeMiningMachine(UUID miningMachineId, UUID fieldID) {
        if (isMachineOnAField(miningMachineId)) {
            throw new RuntimeException("This machine is already on a Field");
        }
        Field field = fieldMap.get(fieldID);
        if (field != null) {
            try {
                field.addMiningMachine(miningMachineMap.get(miningMachineId), new Coordinate(0, 0));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private boolean isMachineOnAField(UUID miningMachineId) {
        return fieldMap.get(getMiningMachineFieldId(miningMachineId)) != null;
    }

    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        for (Field f : fieldMap.values()) {
            if (f.getMiningMachineMap().containsKey(miningMachineId))
                return f.getId();
        }
        return null;
    }

    @Override
    public void addField(Field field) {
        fieldMap.put(field.getId(), field);
    }

    @Override
    public void addFieldConnection(FieldConnection fieldConnection) {
        if (isOutOfBounds(fieldConnection.getSourceId(), fieldConnection.getSourceCoordinates())
                || isOutOfBounds(fieldConnection.getDestinationId(), fieldConnection.getDestinationCoordinates()))
            throw new IllegalArgumentException();
        fieldConnectionMap.put(fieldConnection.getId(), fieldConnection);
    }

    @Override
    public void addMiningMachine(MiningMachine miningMachine) {
        miningMachineMap.put(miningMachine.getId(), miningMachine);
    }

    @Override
    public void addBarrier(UUID fieldId, Barrier barrier) {
        Field field = fieldMap.get(fieldId);
        if (isOutOfBounds(fieldId, barrier.getStart()) || isOutOfBounds(fieldId, barrier.getEnd()))
            throw new IllegalArgumentException();
        field.addBarrier(barrier);
    }

    @Override
    public Coordinate getCoordinates(UUID miningMachineId) {
        return miningMachineMap.get(miningMachineId).getCoordinate();
    }

    private boolean isOutOfBounds(UUID fieldID, Coordinate coordinate) {
        Field field = fieldMap.get(fieldID);
        return field.getWidth() <= coordinate.getX() || field.getHeight() <= coordinate.getY();
    }
}
