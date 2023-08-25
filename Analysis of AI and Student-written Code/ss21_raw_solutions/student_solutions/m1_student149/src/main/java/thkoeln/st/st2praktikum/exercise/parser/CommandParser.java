package thkoeln.st.st2praktikum.exercise.parser;

import thkoeln.st.st2praktikum.exercise.command.*;
import thkoeln.st.st2praktikum.exercise.exception.CommandTypeNotSupportedException;

import java.util.regex.Matcher;

public class CommandParser implements Parsable<Command> {

    private static final String PATTERN = "\\[(\\w{2})\\,((?:\\w|-)+)\\]";

    @Override
    public Command create(Matcher matcher) throws CommandTypeNotSupportedException {
        CommandType commandType = CommandType.getValue(matcher.group(1));
        String argument = matcher.group(2);

        switch (commandType) {
            case ENTER:
                return new EnterCommand(argument);
            case TRAVERSE:
                return new TraverseCommand(argument);
            case MOVE_NORTH:
            case MOVE_EAST:
            case MOVE_SOUTH:
            case MOVE_WEST:
                return new MoveCommand(commandType, argument);

            default:
                throw new CommandTypeNotSupportedException(commandType);
        }
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }
}
