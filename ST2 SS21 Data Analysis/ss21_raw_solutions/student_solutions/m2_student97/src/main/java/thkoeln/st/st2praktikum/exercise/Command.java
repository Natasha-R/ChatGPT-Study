package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        if (numberOfSteps < 1) throw new UnsupportedOperationException();

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
        String[] command = commandString.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
        String commandType = command[0];
        String commandValue = command[1];

        switch (commandType) {
            case "en":
            case "tr":
                try {
                    this.gridId = UUID.fromString(commandValue);
                } catch (IllegalArgumentException exception) {
                    throw new IllegalArgumentException();
                }
                break;
            case "no":
            case "we":
            case "so":
            case "ea":
                if (Integer.parseInt(commandValue) < 1) throw new UnsupportedOperationException();
                break;
        }

        switch (commandType) {
            case "en": 
                this.commandType = CommandType.ENTER;
                this.gridId = UUID.fromString(commandValue);
                break;
            case "tr": 
                this.commandType = CommandType.TRANSPORT;
                this.gridId = UUID.fromString(commandValue);
                break;
            case "no": 
                this.commandType = CommandType.NORTH;
                this.numberOfSteps = Integer.parseInt(commandValue);
                break;
            case "we": 
                this.commandType = CommandType.WEST;
                this.numberOfSteps = Integer.parseInt(commandValue);
                break;
            case "so": {
                this.commandType = CommandType.SOUTH;
                this.numberOfSteps = Integer.parseInt(commandValue);
                break;
            }
            case "ea": 
                this.commandType = CommandType.EAST;
                this.numberOfSteps = Integer.parseInt(commandValue);
                break;
            default: 
                throw new UnsupportedOperationException();
        }
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public Integer getNumberOfSteps() {
        return this.numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }
}
