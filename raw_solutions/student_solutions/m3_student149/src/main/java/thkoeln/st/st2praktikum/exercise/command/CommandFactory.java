package thkoeln.st.st2praktikum.exercise.command;

import thkoeln.st.st2praktikum.exercise.CommandType;
import thkoeln.st.st2praktikum.exercise.exception.CommandTypeNotSupportedException;
import thkoeln.st.st2praktikum.exercise.exception.NegativeMovementStepsException;

public class CommandFactory {

    public static MyCommand buildCommand(CommandType type, String argument) throws NegativeMovementStepsException {
        switch (type) {
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                return new MoveCommand(type, argument);
            case TRANSPORT:
                return new TransportCommand(argument);
            case ENTER:
                return new EnterCommand(argument);

            default:
                throw new CommandTypeNotSupportedException(type);
        }
    }
}
