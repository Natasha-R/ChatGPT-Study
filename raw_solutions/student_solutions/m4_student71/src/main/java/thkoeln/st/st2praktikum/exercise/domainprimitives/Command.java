package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
public class Command {

    @Getter
    private CommandType commandType;
    @Getter
    private Integer numberOfSteps;
    @Getter
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps <= 0)
            throw new InvalidArgumentRuntimeException("numberOfSteps is less then 0");

        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
        
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
        
    }


    public static Command fromString(String commandString ) {
        if(commandString.toCharArray()[0] != '[' || commandString.toCharArray()[commandString.length()-1] != ']')
            throw new RuntimeException("InvalidAttributeValueException");

        var rawString = commandString.substring(1,commandString.length()-1);

        var command = rawString.split(",")[0];
        var information = rawString.split(",")[1];

        switch(command) {
            case "no":
                return new Command(CommandType.NORTH,Integer.parseInt(information));
            case "ea":
                return new Command(CommandType.EAST,Integer.parseInt(information));
            case "so":
                return new Command(CommandType.SOUTH,Integer.parseInt(information));
            case "we":
                return new Command(CommandType.WEST,Integer.parseInt(information));
            //teleport miningMachine
            case "tr":
                return new Command(CommandType.TRANSPORT,UUID.fromString(information));
            //spawns miningMachine
            case "en":
                return new Command(CommandType.ENTER,UUID.fromString(information));
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }
    }

    
}
