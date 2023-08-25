package thkoeln.st.st2praktikum.exercise;

import java.security.InvalidParameterException;
import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps<0){
            throw new InvalidParameterException();
        }
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
        String[] command = commandString.substring(1,commandString.length()-1).split(",");
        switch(command[0]) {
            case "tr":
                commandType = CommandType.TRANSPORT;
                gridId = UUID.fromString(command[1]);
                break;
            case "en":
                commandType = CommandType.ENTER;
                gridId = UUID.fromString(command[1]);
                break;
            case "no":
                commandType = CommandType.NORTH;
                numberOfSteps = Integer.parseInt(command[1]);
                break;
            case "ea":
                commandType = CommandType.EAST;
                numberOfSteps = Integer.parseInt(command[1]);
                break;
            case "so":
                commandType = CommandType.SOUTH;
                numberOfSteps = Integer.parseInt(command[1]);
                break;
            case "we":
                commandType = CommandType.WEST;
                numberOfSteps = Integer.parseInt(command[1]);
                break;
            default:
                throw new InvalidParameterException();
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
