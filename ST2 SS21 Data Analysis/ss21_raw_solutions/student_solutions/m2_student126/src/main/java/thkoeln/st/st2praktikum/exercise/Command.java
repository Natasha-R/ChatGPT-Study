package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandType = commandType;
        setNumberOfSteps(numberOfSteps);
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        String[] splitString = commandString.substring(1, commandString.length() - 1).split(",");
        commandType = parseCommandType(splitString[0].strip());
        switch (commandType) {
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                setNumberOfSteps(Integer.parseInt(splitString[1].strip()));
                break;
            case TRANSPORT:
            case ENTER:
                setGridId(UUID.fromString(splitString[1].strip()));
        }
    }

    private CommandType parseCommandType(String commandType) {
        switch (commandType) {
            case "no":
                return CommandType.NORTH;
            case "ea":
                return CommandType.EAST;
            case "so":
                return CommandType.SOUTH;
            case "we":
                return CommandType.WEST;
            case "tr":
                return CommandType.TRANSPORT;
            case "en":
                return CommandType.ENTER;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void setNumberOfSteps(int steps) {
        if(steps < 1) throw new IllegalArgumentException();
        numberOfSteps = steps;
    }

    private void setGridId(UUID gridId) {
        this.gridId = gridId;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }
}
