package thkoeln.st.st2praktikum.exercise.domainprimitives;

public enum CommandType {
    NORTH("no"),
    WEST("we"),
    SOUTH("so"),
    EAST("ea"),
    TRANSPORT("tr"),
    ENTER("en");

    private final String direction;

    CommandType(final String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public static CommandType of(final String direction) {
        if (direction != null) {
            for (final CommandType command : CommandType.values()) {
                if (command.getDirection().equals(direction)) {
                    return command;
                }
            }
        }
        throw new IllegalArgumentException("Not a valid move!");
    }
}
