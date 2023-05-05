package thkoeln.st.st2praktikum.exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;
@Embeddable
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;

        if(numberOfSteps < 0) throw new RuntimeException("only positive numbers");
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        String[] input = parseCommandString(commandString);
        if(input[1].length() > 5) parseUUID(input[1]);
        else parseSteps(input[1]);
        switch (input[0]){
            case "tr":
                commandType = CommandType.TRANSPORT;
                break;
            case "en":
                commandType = CommandType.ENTER;
                break;
            case "no":
                commandType = CommandType.NORTH;
                break;
            case "we":
                commandType = CommandType.WEST;
                break;
            case "ea":
                commandType = CommandType.EAST;
                break;
            case "so":
                commandType = CommandType.SOUTH;
                break;
            default: throw new RuntimeException("invalid command");
        }
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    public void parseUUID(String input){
        //if (!input.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}"))throw new  RuntimeException("wrong format");
        gridId = UUID.fromString(input);
    }

    public void parseSteps(String input){
        //if(!input.matches("[0-9]+"))throw new RuntimeException("only positiv numbers");
        numberOfSteps = Integer.parseInt(input);
    }

    public String[] parseCommandString(String command){
        command = removeFirstAndLastCharacter(command);
        return command.split(",");
    }

    public String removeFirstAndLastCharacter(String S){  //remove first and Last character
        StringBuilder s = new StringBuilder(S);
        s.deleteCharAt(0);
        s.deleteCharAt(s.length()-1);
        return s.toString();
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
}
