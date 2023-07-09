package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exception.CommandTypeNotSupportedException;

public enum CommandType {
    NORTH("no"),
    WEST("we"),
    SOUTH("so"),
    EAST("ea"),
    TRANSPORT("tr"),
    ENTER("en");


    private final String code;

    CommandType(String code) {
        this.code = code;
    }

    public static CommandType getValue(String code) {
        switch (code.toLowerCase()) {
            case "no":
                return NORTH;
            case "ea":
                return EAST;
            case "so":
                return SOUTH;
            case "we":
                return WEST;
            case "tr":
                return TRANSPORT;
            case "en":
                return ENTER;

            default:
                throw new CommandTypeNotSupportedException(code.toLowerCase());
        }
    }

    public String getCode() {
        return code;
    }
}
