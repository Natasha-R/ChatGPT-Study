package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@Setter(AccessLevel.PROTECTED)
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Command() {
        
    }

    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps >= 0) {
            this.commandType = commandType;
            this.numberOfSteps = numberOfSteps;
        }
        else
            throw new RuntimeException();
        
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

    public static Command fromString(String commandString ) {
        CommandType commandType = null;
        Integer numberOfSteps = null;
        UUID gridId = null;
        if(checkCommandString(commandString)) {
            switch (commandString.substring(1, 3)) {
                case "so": commandType = CommandType.SOUTH; break;
                case "we": commandType = CommandType.WEST; break;
                case "no": commandType = CommandType.NORTH; break;
                case "ea": commandType = CommandType.EAST; break;
                case "tr": commandType = CommandType.TRANSPORT; break;
                case "en": commandType = CommandType.ENTER; break;
            }


            if (!commandString.contains("-"))
                numberOfSteps = Integer.parseInt(commandString.replaceAll(".*,", "").replace("]", ""));
            else
                gridId = UUID.fromString(commandString.replaceAll(".*,", "").replace("]", ""));

            if (commandType == CommandType.TRANSPORT || commandType == CommandType.ENTER) {
                return new Command(commandType, gridId);
            } else
                return new Command(commandType, numberOfSteps);
        }
        else
            throw new RuntimeException();
    }

    private static Boolean checkCommandString(String commandString){
        if(commandString.chars().filter(ch -> ch == '[').count() != 1 && commandString.chars().filter(ch -> ch == ']').count() != 1 ) return false;
        if(commandString.chars().filter(ch -> ch == ',').count() != 1) return false;
        if(!commandString.contains("[") || !commandString.contains("]")) return false;
        if((!commandString.contains("-") && !Character.isDigit(commandString.charAt(commandString.length()-2)) ) || Character.isDigit(commandString.charAt(1))) return false; //for move command
        if(commandString.contains("-") && !commandString.replaceAll(".*,","").replace("]","").matches("[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}")) return false; //for command with UUID
        switch (commandString.substring(1,commandString.indexOf(','))){
            case "so" : break;
            case "we" : break;
            case "no" : break;
            case "ea" : break;
            case "tr" : break;
            case "en" : break;
            default: return false;
        }

        return true;
    }
}
