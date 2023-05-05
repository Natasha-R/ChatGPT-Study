package thkoeln.st.st2praktikum.exercise;


import lombok.Value;
import javax.persistence.Embeddable;

import java.util.UUID;

@Value
@Embeddable
public class Command {

    CommandType commandType;
    Integer numberOfSteps;
    UUID gridId;

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
                this.numberOfSteps = 0;
                break;
            case "tr":
                this.commandType = CommandType.TRANSPORT;
                this.gridId = UUID.fromString(actionObject);
                this.numberOfSteps = 0;
                break;
            case "we":
                this.commandType = CommandType.WEST;
                this.numberOfSteps = Integer.parseInt(actionObject);
                this.gridId = null;
                break;
            case "so":
                this.commandType = CommandType.SOUTH;
                this.numberOfSteps = Integer.parseInt(actionObject);
                this.gridId = null;
                break;
            case "no":
                this.commandType = CommandType.NORTH;
                this.numberOfSteps = Integer.parseInt(actionObject);
                this.gridId = null;
                break;
            case "ea":
                this.commandType = CommandType.EAST;
                this.numberOfSteps = Integer.parseInt(actionObject);
                this.gridId = null;
                break;
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

    void ensureValidCommandAndNumberOfSteps(CommandType commandType, Integer numberOfSteps){
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
    void ensureValidCommandAndGrid(CommandType commandType, UUID gridId){
        switch (commandType){
            case ENTER:
            case TRANSPORT:
                break;
            default:
                throw new IllegalArgumentException("commandtype" + commandType + " is not supported for a grid id");
        }
    }

}

