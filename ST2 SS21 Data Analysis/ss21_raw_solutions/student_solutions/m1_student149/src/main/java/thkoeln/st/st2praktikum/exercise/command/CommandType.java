package thkoeln.st.st2praktikum.exercise.command;

import thkoeln.st.st2praktikum.exercise.exception.CommandTypeNotSupportedException;

public enum CommandType {

    ENTER("en"),
    TRAVERSE("tr"),
    MOVE_NORTH("no"),
    MOVE_EAST("ea"),
    MOVE_SOUTH("so"),
    MOVE_WEST("we");

    private final String code;

    CommandType(String code) {
        this.code = code;
    }

    public static CommandType getValue(String code) {
        switch (code.toLowerCase()) {
            case "en":
                return ENTER;
            case "tr":
                return TRAVERSE;
            case "no":
                return MOVE_NORTH;
            case "ea":
                return MOVE_EAST;
            case "so":
                return MOVE_SOUTH;
            case "we":
                return MOVE_WEST;

            default:
                throw new CommandTypeNotSupportedException(code.toLowerCase());
        }
    }

    public String getCode() {
        return code;
    }

}
