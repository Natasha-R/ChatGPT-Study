package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.WrongFormatException;

import javax.persistence.Embeddable;
import java.util.UUID;


public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Command() {

    }

    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandType = commandType;
        checkFormat(numberOfSteps);
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

        String command = commandString.substring(1,3);
        switch (command){
            case "tr": return new Command(CommandType.TRANSPORT,tryUUID(commandString));
            case "en": return new Command(CommandType.ENTER,tryUUID(commandString));
            case "no": return new Command(CommandType.NORTH,tryNumberOfSteps(commandString));
            case "ea": return new Command(CommandType.EAST,tryNumberOfSteps(commandString));
            case "so": return new Command(CommandType.SOUTH,tryNumberOfSteps(commandString));
            case "we": return new Command(CommandType.WEST,tryNumberOfSteps(commandString));
            default: throw new WrongFormatException("Wrong Format for Command, try for example [no, 3], [en, <uuid>], [tr, <uuid>] ");
        }
    }



    private static UUID tryUUID(String commandString){
        UUID gridIdFromString;
        try{
            gridIdFromString = UUID.fromString(commandString.substring(4, commandString.length()-1));
        }catch(Exception e){
            throw new WrongFormatException("Wrong Format for Command, try for example [en, <uuid>], [tr, <uuid>] ");
        }
        return gridIdFromString;
    }

    private static int tryNumberOfSteps(String commandString){
        int numberOfStepsFromString;
        try{
            numberOfStepsFromString = Integer.parseInt(commandString.substring(4).replace(']', ' ').trim());
        }catch(Exception e){
            throw new WrongFormatException("Wrong Format for Command, try for example [no, 3]");
        }
        if(numberOfStepsFromString < 0){
            throw new WrongFormatException("No negative number of steps allowed!");
        }
        return numberOfStepsFromString;


    }

    private void checkFormat(int numberOfSteps){

        if(numberOfSteps < 0){
            throw new WrongFormatException("No negative number of steps allowed!");
        }

        this.numberOfSteps = numberOfSteps;
    }

}


