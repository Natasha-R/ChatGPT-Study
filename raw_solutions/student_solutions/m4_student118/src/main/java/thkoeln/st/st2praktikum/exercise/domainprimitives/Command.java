package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Embeddable;
import java.util.UUID;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps>=0) {
            this.commandType = commandType;
            this.numberOfSteps = numberOfSteps;
        }else throw new RuntimeException();
        
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
    private static CommandType fetchTheCommandType(String commandString){
        if(commandString.charAt(1)=='e' && commandString.charAt(2)=='n'){
            return CommandType.ENTER;
        }else if (commandString.charAt(1)=='t' && commandString.charAt(2)=='r'){
            return CommandType.TRANSPORT;
        }else if (commandString.charAt(1)=='n' && commandString.charAt(2)=='o'){
            return CommandType.NORTH;
        }else if (commandString.charAt(1)=='s' && commandString.charAt(2)=='o'){
            return CommandType.SOUTH;
        }else if (commandString.charAt(1)=='e' && commandString.charAt(2)=='a'){
            return CommandType.EAST;
        }else if (commandString.charAt(1)=='w' && commandString.charAt(2)=='e'){
            return CommandType.WEST;
        }
        return null;
    }

    private static boolean isTheCommandCorrect(String commandString){

        if (fetchTheCommandType(commandString)!=null){
            if (fetchTheCommandType(commandString)==CommandType.TRANSPORT || fetchTheCommandType(commandString)==CommandType.ENTER){
                if (commandString.length()==41){
                    if (commandString.replaceAll("[a-zA-Z]","").replaceAll("[0-9]","").replace("-","").equals("[,]")){
                        return true;
                    }
                }
            }else{
                if (commandString.length()==6){
                    if (commandString.replaceAll("[a-zA-Z]","").replaceAll("[0-9]","").equals("[,]")){
                        return true;
                    }
                }
            }
        }else return false;

        return false;
    }
//    public Command(String commandString) {
//        if (isTheCommandCorrect(commandString)){
//            if (fetchTheCommandType(commandString)==CommandType.TRANSPORT || fetchTheCommandType(commandString)==CommandType.ENTER){
//                this.commandType = fetchTheCommandType(commandString);
//                this.gridId = UUID.fromString(commandString.substring(commandString.indexOf(',') + 1, commandString.length() - 1));
//            }else {
//                this.commandType = fetchTheCommandType(commandString);
//                this.numberOfSteps = Integer.parseInt(commandString.replaceAll("[^0-9]", ""));
//            }
//        }else throw new RuntimeException();
//    }

    public static Command fromString(String commandString ) {
        if (isTheCommandCorrect(commandString)){
            if (fetchTheCommandType(commandString)==CommandType.TRANSPORT || fetchTheCommandType(commandString)==CommandType.ENTER){
               return new Command(fetchTheCommandType(commandString),UUID.fromString(commandString.substring(commandString.indexOf(',') + 1, commandString.length() - 1)));
            }else {
               return new Command(fetchTheCommandType(commandString),Integer.parseInt(commandString.replaceAll("[^0-9]", "")));
            }
        }else throw new RuntimeException();
    }
    
}
