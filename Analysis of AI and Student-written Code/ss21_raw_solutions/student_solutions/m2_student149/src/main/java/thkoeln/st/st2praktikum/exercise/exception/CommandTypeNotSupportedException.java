package thkoeln.st.st2praktikum.exercise.exception;

import thkoeln.st.st2praktikum.exercise.CommandType;

public class CommandTypeNotSupportedException extends IllegalArgumentException {

    public CommandTypeNotSupportedException(String code) {
        super("No commandType with code '" + code + "' implemented");
    }

    public CommandTypeNotSupportedException(CommandType commandType) {
        super("No case for commandType '" + commandType.name() + "' implemented");
    }

}
