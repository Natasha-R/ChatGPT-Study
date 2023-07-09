package thkoeln.st.st2praktikum.exercise.parser;

import thkoeln.st.st2praktikum.exercise.CommandType;
import thkoeln.st.st2praktikum.exercise.command.CommandFactory;
import thkoeln.st.st2praktikum.exercise.command.MyCommand;
import thkoeln.st.st2praktikum.exercise.exception.CommandTypeNotSupportedException;

import java.util.regex.Matcher;

public class CommandParser implements Parsable<MyCommand> {

    private static final String PATTERN = "\\[(\\w{2})\\,((?:\\w|-)+)\\]";

    @Override
    public MyCommand create(Matcher matcher) throws CommandTypeNotSupportedException {
        CommandType commandType = CommandType.getValue(matcher.group(1));
        String argument = matcher.group(2);

        return CommandFactory.buildCommand(commandType, argument);
    }

    @Override
    public String getPattern() {
        return PATTERN;
    }
}
