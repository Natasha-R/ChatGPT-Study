package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
public class Command {

    //@Embedded
    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandType = commandType;
        if (numberOfSteps >= 0)
            this.numberOfSteps = numberOfSteps;
        else
            throw new IllegalArgumentException("The number of steps can't be negative");
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        if (!commandString.startsWith("[") && !commandString.endsWith("]"))
            throw new IllegalArgumentException("A command needs to start with '[' and end with ']'");
        if (!commandString.contains(","))
            throw new IllegalArgumentException("The Input is not valid it needs to be like [no, 3], [en, <uuid>], [tr, <uuid>] but was " + commandString);
        String splitCommand[] = commandString.replace("[","").replace("]","").split(",");
        if (splitCommand.length != 2)
            throw new IllegalArgumentException("The command should have two parts separated with ','");


        switch (splitCommand[0])
        {
            case "tr":
                commandType = CommandType.TRANSPORT;
                break;
            case "en":
                commandType = CommandType.ENTER;
                break;

            case "ea":
                commandType = CommandType.EAST;
                break;
            case "we":
                commandType = CommandType.WEST;
                break;
            case "no":
                commandType = CommandType.NORTH;
                break;
            case "so":
                commandType = CommandType.SOUTH;
                break;

            default:
                throw new IllegalArgumentException("That is not a valid CommandType");
        }

        switch (commandType)
        {
            case TRANSPORT :
            case ENTER:
                gridId = UUID.fromString(splitCommand[1]);
                break;
            case EAST:
            case WEST:
            case NORTH:
            case SOUTH:
                Integer number = Integer.parseInt(splitCommand[1]);
                if (number >= 0)
                    numberOfSteps = number;
                else
                    throw new IllegalArgumentException("The number of steps can't be negative");
                break;
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
