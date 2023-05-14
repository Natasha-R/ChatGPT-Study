package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.AllArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Embeddable
public class Command {

    @Getter
    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Command(CommandType commandType, Integer numberOfSteps) {
        if (numberOfSteps<0)
            throw new RuntimeException("Invalid Number Of Steps");
        else {
            this.commandType = commandType;
            this.numberOfSteps = numberOfSteps;
        }
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    protected Command() {}


    @Override
    public String toString() {
        if (commandType==CommandType.ENTER || commandType==CommandType.TRANSPORT)
            return getCommandType().toString() + " --> " + getGridId();
        else
            return getCommandType().toString() + " --> " + getNumberOfSteps();
    }


    public static Command fromString(String commandString ) {
        if (commandString.isEmpty())
            throw new RuntimeException("Empty Command String");
        if (!(commandString.startsWith("[") && commandString.endsWith("]")) || !commandString.contains(","))
            throw new RuntimeException("Invalid Command String");

        String[] input = commandString.replace("[","").replace("]","").split(",");

        if (!(input[0].equals("no") || input[0].equals("ea") || input[0].equals("so") || input[0].equals("we") || input[0].equals("en") || input[0].equals("tr")))
            throw new RuntimeException("Invalid Command");

        CommandType commandType;

        switch (input[0]) {
            case "no":
                commandType = CommandType.NORTH;
                break;
            case "ea":
                commandType = CommandType.EAST;
                break;
            case "so":
                commandType = CommandType.SOUTH;
                break;
            case "we":
                commandType = CommandType.WEST;
                break;
            case "en":
                commandType = CommandType.ENTER;
                break;
            case "tr":
                commandType = CommandType.TRANSPORT;
                break;
            default:
                throw new RuntimeException("Invalid CommandType");
        }

        if (commandType == CommandType.ENTER || commandType == CommandType.TRANSPORT) {
            if (input[1].matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}"))
                return new Command(commandType, UUID.fromString(input[1]));
            else
                throw new RuntimeException("Invalid UUID");
        }
        else {
            if (input[1].startsWith("0") || Integer.parseInt(input[1]) < 0)
                throw new RuntimeException("Invalid Number Of Steps");
            else
                return new Command(commandType, Integer.valueOf(input[1]));
        }
    }
    
}
