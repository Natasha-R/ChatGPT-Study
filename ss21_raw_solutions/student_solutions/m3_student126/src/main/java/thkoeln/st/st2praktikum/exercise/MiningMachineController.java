package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import thkoeln.st.st2praktikum.exercise.abstractions.interfaces.Controller;

import java.util.*;

public class MiningMachineController implements Controller {


    @Override
    public boolean executeCommand(MiningMachine miningMachine, Field field, FieldConnection connection, Command command) {
        switch (command.getCommandType()) {
            case TRANSPORT:
                return transportMiningMachine(miningMachine, connection);
            case ENTER:
                return placeMiningMachine(miningMachine, field);
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
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
}
