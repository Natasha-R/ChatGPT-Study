package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.persistence.Embeddable;
import java.util.UUID;


public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    protected Command() {

    }

    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps < 0 ){
            throw new RuntimeException("Steps cant be negative.");
        }
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

        if (!commandString.substring(1, 4).equals("en,") &&
                !commandString.substring(1,4).equals("tr,") &&
                !commandString.substring(1,4).equals("no,") &&
                !commandString.substring(1,4).equals("ea,") &&
                !commandString.substring(1,4).equals("so,") &&
                !commandString.substring(1,4).equals("we,")){
            throw new RuntimeException("No valid command input.");
        }



        switch (commandString.substring(1,3)){


            case "no": return new Command(CommandType.NORTH , Character.getNumericValue(commandString.charAt(4)));


            case "ea": return new Command(CommandType.EAST , Character.getNumericValue(commandString.charAt(4)));


            case "so": return new Command(CommandType.SOUTH , Character.getNumericValue(commandString.charAt(4)));


            case "we": return new Command(CommandType.WEST , Character.getNumericValue(commandString.charAt(4)));


            case "en": return new Command(CommandType.ENTER , UUID.fromString(commandString.substring(4,40)));


            case "tr": return new Command(CommandType.TRANSPORT , UUID.fromString(commandString.substring(4,40)));

        }
        throw new RuntimeException("No valid input");
    }

}
