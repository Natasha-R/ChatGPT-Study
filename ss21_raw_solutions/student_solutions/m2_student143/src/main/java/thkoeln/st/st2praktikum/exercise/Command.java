package thkoeln.st.st2praktikum.exercise;

import java.util.Objects;
import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps < 0 ){
            throw new IllegalArgumentException("number of steps shall be positive");
        }
        switch (commandType){
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                break;
            default:
                throw new IllegalArgumentException("commandtype " + commandType + "is not a move command" );
        }

        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
    }

    public Command(CommandType commandType, UUID gridId) {
        switch (commandType){
            case ENTER:
            case TRANSPORT:
                break;
            default:
                throw new IllegalArgumentException("commandtype" + commandType + " is not supported for a grid id");
        }
        this.commandType = commandType;
        this.gridId = gridId;
    }



    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        String [] actionStrings = commandString
                .replaceAll("\\[|\\]","")
                .split(",");
        String action = actionStrings[0];
        String actionObject = actionStrings[1];

        switch (action){
            case "en":
                this.commandType = CommandType.ENTER;
                this.gridId = UUID.fromString(actionObject);
                break;
            case "tr":
                this.commandType = CommandType.TRANSPORT;
                this.gridId = UUID.fromString(actionObject);
                break;
            case "we":
                this.commandType = CommandType.WEST;
                this.numberOfSteps = Integer.parseInt(actionObject);
                break;
            case "so":
                this.commandType = CommandType.SOUTH;
                this.numberOfSteps = Integer.parseInt(actionObject);
                break;
            case "no":
                this.commandType = CommandType.NORTH;
                this.numberOfSteps = Integer.parseInt(actionObject);
                break;
            case "ea":
                this.commandType = CommandType.EAST;
                this.numberOfSteps = Integer.parseInt(actionObject);
                break;

            default:
                throw new IllegalArgumentException("The commandstring " + commandString + " is not supported");
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return commandType == command.commandType && Objects.equals(numberOfSteps, command.numberOfSteps) && Objects.equals(gridId, command.gridId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandType, numberOfSteps, gridId);
    }
}
