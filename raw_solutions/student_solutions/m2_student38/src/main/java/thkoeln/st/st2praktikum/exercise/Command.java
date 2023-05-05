package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        if (numberOfSteps < 0)
            throw new IllegalArgumentException("Number of steps have to be positive");
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
        String[] commands = commandString.replaceAll("[\\[\\]]", "").split(",");

        if (commands.length != 2)
            throw new IllegalArgumentException("Illegal amount of parameters!");

        switch (commands[0]){
            case "en":
                try{
                    this.commandType = CommandType.ENTER;
                    this.gridId = UUID.fromString(commands[1]);
                } catch (Exception exp){
                    throw exp;
                }
                break;
            case "tr":
                try {
                    this.commandType = CommandType.TRANSPORT;
                    this.gridId = UUID.fromString(commands[1]);
                } catch (Exception exp){
                    throw exp;
                }
                break;
            default:
                this.commandType = identifyDirection(commands[0]);
                try{
                    this.numberOfSteps = Integer.parseInt(commands[1]);
                } catch (Exception exp){
                    throw exp;
                }
        }
    }

    private CommandType identifyDirection(String direction){
        switch (direction){
            case "no":
                return CommandType.NORTH;
            case "so":
                return CommandType.SOUTH;
            case "ea":
                return CommandType.EAST;
            case "we":
                return CommandType.WEST;
            default:
                throw new IllegalArgumentException("Illegal direction!");
        }
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
