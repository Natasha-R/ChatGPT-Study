package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.InvalidParameterException;
import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Command()
    {
        set(CommandType.ENTER, null, null);
    }

    public Command(CommandType commandType, Integer numberOfSteps) {
        set(commandType, numberOfSteps);
    }

    public Command(CommandType commandType, UUID gridId) {
        set(commandType, gridId);
    }

    public Command(String string)
    {
        String[] command = string.substring(1,string.length()-1).split(",");
        switch(command[0])
        {
            case "tr":
                set(CommandType.TRANSPORT, UUID.fromString(command[1]));
                break;

            case "en":
                set(CommandType.ENTER,UUID.fromString(command[1]));
                break;

            case "no":
                set(CommandType.NORTH,Integer.parseInt(command[1]));
                break;

            case "ea":
                set(CommandType.EAST,Integer.parseInt(command[1]));
                break;

            case "so":
                set(CommandType.SOUTH,Integer.parseInt(command[1]));
                break;

            case "we":
                set(CommandType.WEST,Integer.parseInt(command[1]));
                break;

            default:
                throw new InvalidParameterException();
        }
    }

    public Command(CommandType commandType, Integer numberOfSteps, UUID gridId){
        set(commandType, numberOfSteps, gridId);
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


    public static Command fromString(String commandString )
    {
        return new Command(commandString);
    }

    public void set(CommandType commandType, Integer numberOfSteps)
    {
        set(commandType, numberOfSteps, null);
    }

    public void set(CommandType commandType, UUID gridId)
    {
        set(commandType, null, gridId);
    }

    public void set(CommandType commandType, Integer numberOfSteps, UUID gridId)
    {
        if(numberOfSteps != null)
        {
            if(numberOfSteps < 0)
            {
                throw new InvalidParameterException();
            }
        }

        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
        this.gridId = gridId;
    }
}
