package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        throw new UnsupportedOperationException();
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
