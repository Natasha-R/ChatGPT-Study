package thkoeln.st.st2praktikum.exercise;
import lombok.Getter;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Command {

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

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        if (commandString.isEmpty())
            throw new RuntimeException("Empty Command String");
        if (!(commandString.startsWith("[") && commandString.endsWith("]")) || !commandString.contains(","))
            throw new RuntimeException("Invalid Command String");

        String[] input = commandString.replace("[","").replace("]","").split(",");

        if (!(input[0].equals("no") || input[0].equals("ea") || input[0].equals("so") || input[0].equals("we") || input[0].equals("en") || input[0].equals("tr")))
            throw new RuntimeException("Invalid Command");

        if (input[0].equals("no"))
            commandType  = CommandType.NORTH;
        if (input[0].equals("ea"))
            commandType  = CommandType.EAST;
        if (input[0].equals("so"))
            commandType  = CommandType.SOUTH;
        if (input[0].equals("we"))
            commandType  = CommandType.WEST;
        if (input[0].equals("en"))
            commandType  = CommandType.ENTER;
        if (input[0].equals("tr"))
            commandType  = CommandType.TRANSPORT;

        if (commandType==CommandType.ENTER || commandType==CommandType.TRANSPORT) {
            if (input[1].matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}"))
                gridId = UUID.fromString(input[1]);
            else
                throw new RuntimeException("Invalid UUID");
        }
        else {
            if (input[1].startsWith("0") || Integer.parseInt(input[1]) < 0)
                throw new RuntimeException("Invalid Number Of Steps");
            else
                numberOfSteps = Integer.valueOf(input[1]);
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

    @Override
    public String toString() {
        if (commandType==CommandType.ENTER || commandType==CommandType.TRANSPORT)
            return getCommandType().toString() + " --> " + getGridId();
        else
            return getCommandType().toString() + " --> " + getNumberOfSteps();
    }

}


