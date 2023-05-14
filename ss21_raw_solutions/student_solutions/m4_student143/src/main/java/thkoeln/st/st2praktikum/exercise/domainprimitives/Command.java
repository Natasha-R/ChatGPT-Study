package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;

import javax.persistence.Embeddable;
import java.util.UUID;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Command {

    CommandType commandType;
    Integer numberOfSteps;
    UUID gridId;

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public static Command fromString(String commandString) {
        String [] actionStrings = commandString
                .replaceAll("\\[|\\]","")
                .split(",");
        String action = actionStrings[0];
        String actionObject = actionStrings[1];

        switch (action){
            case "en":
                return new Command(CommandType.ENTER,UUID.fromString(actionObject));
            case "tr":
                return new Command(CommandType.TRANSPORT,UUID.fromString(actionObject));
            case "we":
                return new Command(CommandType.WEST, Integer.parseInt(actionObject));
            case "so":
                return new Command(CommandType.SOUTH,Integer.parseInt(actionObject));
            case "no":
                return new Command(CommandType.NORTH, Integer.parseInt(actionObject));
            case "ea":
                return new Command(CommandType.EAST,Integer.parseInt(actionObject));
            default:
                throw new IllegalArgumentException("The commandstring " + commandString + " is not supported");
        }
    }

    public Command(CommandType commandType, Integer numberOfSteps) {
        ensureValidCommandAndNumberOfSteps(commandType,numberOfSteps);
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
        this.gridId = null;
    }

    public Command(CommandType commandType, UUID gridId) {
        ensureValidCommandAndGrid(commandType,gridId);
        this.commandType = commandType;
        this.gridId = gridId;
        this.numberOfSteps = 0;
    }

    static void ensureValidCommandAndNumberOfSteps(CommandType commandType, Integer numberOfSteps){
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
    }
    static void ensureValidCommandAndGrid(CommandType commandType, UUID gridId){
        switch (commandType){
            case ENTER:
            case TRANSPORT:
                break;
            default:
                throw new IllegalArgumentException("commandtype" + commandType + " is not supported for a grid id");
        }
    }

}



/*public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Command() {
        
    }

    public Command(CommandType commandType, Integer numberOfSteps) {
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

    public static Command fromString(String commandString ) {
        throw new UnsupportedOperationException();
    }
    
}*/
