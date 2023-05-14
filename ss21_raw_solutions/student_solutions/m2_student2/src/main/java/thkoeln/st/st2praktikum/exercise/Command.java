package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;
    private Boolean isValid = false;

    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps >= 0) {
            this.commandType = commandType;
            this.numberOfSteps = numberOfSteps;
        }
        else
            throw new CommandException();
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        if(checkCommandString(commandString)){
            this.isValid = true;
            changeCommandStringToType(commandString);
        }
        else
            throw new CommandException();
    }

    private Boolean checkCommandString(String commandString){
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

    private void changeCommandStringToType(String commandString){
        switch(commandString.substring(1,3)){
            case "so" : this.commandType = CommandType.SOUTH; break;
            case "we" : this.commandType = CommandType.WEST; break;
            case "no" : this.commandType = CommandType.NORTH; break;
            case "ea" : this.commandType = CommandType.EAST; break;
            case "tr" : this.commandType = CommandType.TRANSPORT; break;
            case "en" : this.commandType = CommandType.ENTER; break;
        }

        if(!commandString.contains("-"))
            this.numberOfSteps = Integer.parseInt(commandString.replaceAll(".*,","").replace("]",""));
        else
            this.gridId = UUID.fromString(commandString.replaceAll(".*,","").replace("]",""));
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

    public Boolean getIsValid(){
        return isValid;
    }
}
