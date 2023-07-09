package thkoeln.st.st2praktikum.exercise.domainprimitives;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Command() {

    }

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
    
    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(Integer numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }

    public void setGridId(UUID gridId) {
        this.gridId = gridId;
    }

    public static Command fromString(String commandString ) {
        String[] commands = commandString.replaceAll("[\\[\\]]", "").split(",");

        if (commands.length != 2)
            throw new IllegalArgumentException("Illegal amount of parameters!");
        CommandType commandType;
        UUID gridId;
        Integer numberOfSteps;

        switch (commands[0]){
            case "en":
                try{
                    commandType = CommandType.ENTER;
                    gridId = UUID.fromString(commands[1]);
                    return new Command(commandType,gridId);
                } catch (Exception exp){
                    throw exp;
                }
            case "tr":
                try {
                    commandType = CommandType.TRANSPORT;
                    gridId = UUID.fromString(commands[1]);
                    return new Command(commandType,gridId);
                } catch (Exception exp){
                    throw exp;
                }
            default:
                commandType = identifyDirection(commands[0]);
                try{
                    numberOfSteps = Integer.parseInt(commands[1]);
                    return new Command(commandType,numberOfSteps);
                } catch (Exception exp){
                    throw exp;
                }
        }
    }

    private static CommandType identifyDirection(String direction){
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
    
}
