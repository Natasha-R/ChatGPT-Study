package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private final CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
        noNegativeNames();
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        String replaceBrackets = commandString.replace("[", "");
        replaceBrackets = replaceBrackets.replace("]","");
        String[] commandSplitAtComma = replaceBrackets.split(",");
        if (commandSplitAtComma[0] != null && commandSplitAtComma[1] != null) {
            if(commandSplitAtComma.length==2) {
                commandType=setCommandType(commandSplitAtComma[0]);
                if(isComppassDircetion(commandType)){
                    try{
                        numberOfSteps=Integer.parseInt(commandSplitAtComma[1]);
                    }catch (NumberFormatException nfe){
                        throw new RuntimeException();
                    }
                    gridId=null;
                }
                else{
                    try {
                        gridId=UUID.fromString(commandSplitAtComma[1]);
                    }catch (NumberFormatException nfe){
                        throw new RuntimeException();
                    }
                    numberOfSteps=0;
                }
            }
            else{
                throw new RuntimeException();
            }
        }
        else{
            throw new RuntimeException();
        }
        noNegativeNames();
    }

    private CommandType setCommandType(String pCommandType){
        pCommandType=pCommandType.toLowerCase();
        switch (pCommandType) {
            case "no":
                return CommandType.NORTH;
            case "ea":
                return CommandType.EAST;
            case "we":
                return CommandType.WEST;
            case "so":
                return CommandType.SOUTH;
            case "en":
                return CommandType.ENTER;
            case "tr":
                return CommandType.TRANSPORT;
        }
            throw new RuntimeException();
    }

    private boolean isComppassDircetion(CommandType pCommandType){
        return !pCommandType.equals(CommandType.ENTER) && !pCommandType.equals(CommandType.TRANSPORT);
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

    private void noNegativeNames(){
        if(numberOfSteps<0)throw new RuntimeException();
    }
}
