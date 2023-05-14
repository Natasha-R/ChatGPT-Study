package thkoeln.st.st2praktikum.exercise;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;

    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
    }

    public Command(CommandType commandType) {
        this.commandType = commandType;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        var command = fromStringToCommand(commandString);
        if (command.isMovement()) commandType = command.commandType;
        if (command.isTransport()) commandType = CommandType.TRANSPORT;
        if (command.isSettingInitial()) commandType = CommandType.ENTER;

        numberOfSteps = command.numberOfSteps;
    }

    private Command fromStringToCommand(String commandString){
        var commandSize = commandString.length();
        var subString = commandString.substring(1, commandSize - 1);
        var command = subString.split(",");
        var commandType = command[0];
        var commandSteps = command[1];

        return new Command(CommandType.valueOf(commandType.toUpperCase()), 2);
    }

    private boolean isMovement(){
        return commandType.equals("NORTH") ||commandType.equals("SOUTH") || commandType.equals("EAST") || commandType.equals("WEST");
    }

    private boolean isTransport(){
        return commandType.equals("TRANSPORT");
    }

    private boolean isSettingInitial(){
        return commandType.equals("ENTER");
    }



}
