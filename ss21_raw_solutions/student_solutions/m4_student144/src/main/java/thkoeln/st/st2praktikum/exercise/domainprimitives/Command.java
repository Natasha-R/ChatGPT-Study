package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.Object;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Command {
    @Id
    private  UUID commandId;

    @Embedded
    private CommandType commandType;

    private Integer numberOfSteps;
    private UUID gridId;
    private Boolean isHorizontalMovement;

    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandId = UUID.randomUUID();
        this.commandType = commandType;
        this.isHorizontalMovement = isHorizontalMovement();
        this.numberOfSteps = numberOfSteps;
        checkForNegativeSteps(numberOfSteps);
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandId = UUID.randomUUID();
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString){
        CommandType commandType = parseCommandType(commandString);
        if(validCommandType(commandString)){
            if(isMoveCommand(commandType)){
                this.commandType = commandType;
                this.isHorizontalMovement = isHorizontalMovement();
                this.numberOfSteps = parseNumberOfStepsToInteger(commandString);
                checkForNegativeSteps(numberOfSteps);
            }
            if(isEnterCommand(commandType)||isTransportCommand(commandType)){
                this.commandType = commandType;
                this.gridId = parseUuidFromCommandString(commandString);
            }
        }
        else throw new RuntimeException("Invalid Command");

    }

    private UUID parseUuidFromCommandString(String commandString){
        UUID gridId = UUID.fromString(commandString.substring(commandString.indexOf(",")+1,commandString.lastIndexOf("]")));
        return gridId;
    }

    private Boolean validCommandType(String commandString){
        CommandType commandType = parseCommandType(commandString);
        if(commandType.equals(CommandType.INVALID)){
            return false;
        }
        else return true;
    }

    private boolean isHorizontalMovement(){
        if(this.getCommandType().equals(CommandType.NORTH)||this.getCommandType().equals(CommandType.SOUTH)){
            return false;
        }
        else return true;
    }

    private Integer parseNumberOfStepsToInteger(String commandString){
        Integer numberOfSteps = Integer.parseInt(commandString.substring(4,commandString.indexOf("]")));
        return numberOfSteps;
    }

    private CommandType parseCommandType(String commandString){
        String stringCommandType = commandString.substring(1,commandString.indexOf(","));
        CommandType commandType = CommandType.INVALID;
        if(stringCommandType.equals("no")){ commandType = CommandType.NORTH; return commandType;}
        if(stringCommandType.equals("so")){ commandType = CommandType.SOUTH; return commandType;}
        if(stringCommandType.equals("ea")){ commandType = CommandType.EAST; return commandType;}
        if(stringCommandType.equals("we")){ commandType = CommandType.WEST; return commandType;}
        if(stringCommandType.equals("tr")){ commandType = CommandType.TRANSPORT; return commandType;}
        if(stringCommandType.equals("en")){ commandType = CommandType.ENTER; return commandType;}
        else return commandType;
    }

    public Boolean isMoveCommand(CommandType commandType){
        if(commandType.equals(CommandType.NORTH)||commandType.equals(CommandType.SOUTH)||commandType.equals(CommandType.EAST)||commandType.equals(CommandType.WEST)){
            return true;
        }
        else return false;
    }

    private void checkForNegativeSteps(Integer numberOfSteps){
        if(numberOfSteps<0){throw new RuntimeException("number of steps cant be negative");}
    }

    private Boolean isTransportCommand(CommandType commandType){
        if(commandType.equals(CommandType.TRANSPORT)){return true;}
        else return false;
    }

    private Boolean isEnterCommand(CommandType commandType){
        if(commandType.equals(CommandType.ENTER)){return true;}
        else return false;
    }

    public static Command fromString(String commandString ) {
        Command command = new Command(commandString);
        return command;
    }
    public UUID getCommandId(){ return this.commandId;}
    
}
