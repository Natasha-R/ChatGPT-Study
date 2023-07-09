package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        if (numberOfSteps < 1) throw new IllegalArgumentException();
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
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

    public static Command fromString(String commandString) {
        String[] splitString = commandString.substring(1, commandString.length() - 1).split(",");
        CommandType commandType = parseCommandType(splitString[0].strip());
        switch (commandType) {
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                return new Command(commandType, Integer.parseInt(splitString[1].strip()));
            case TRANSPORT:
            case ENTER:
                return new Command(commandType, UUID.fromString(splitString[1].strip()));
            default:
                throw new IllegalArgumentException("Command string is malformed");
        }
    }

    private static CommandType parseCommandType(String commandType) {
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
}
