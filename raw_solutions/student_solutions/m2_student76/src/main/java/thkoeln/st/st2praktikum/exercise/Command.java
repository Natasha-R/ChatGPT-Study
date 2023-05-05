package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private final CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps>=0){
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
        }else throw new RuntimeException();
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    private CommandType determineCommandType(String commandString){
        if(commandString.charAt(1)=='n'&&commandString.charAt(2)=='o'){ return CommandType.NORTH; }
        if(commandString.charAt(1)=='s'&&commandString.charAt(2)=='o'){ return CommandType.SOUTH; }
        if(commandString.charAt(1)=='e' && commandString.charAt(2)=='a'){ return CommandType.EAST;}
        if(commandString.charAt(1)=='w'&&commandString.charAt(2)=='e'){return CommandType.WEST;}
        if(commandString.charAt(1)=='t' &&commandString.charAt(2)=='r'){return CommandType.TRANSPORT;}
        if(commandString.charAt(1)=='e'&&commandString.charAt(2)=='n'){return CommandType.ENTER;}
        return null;
    }

    private boolean checkCommand(String commandString){
        if(determineCommandType(commandString)!=null){
            if(determineCommandType(commandString)==CommandType.ENTER || determineCommandType(commandString) == CommandType.TRANSPORT){
                if (commandString.length()==41){
                    return commandString.replaceAll("[a-zA-Z]", "").replaceAll("[0-9]", "").replace("-", "").equals("[,]");
                }
            }else{
                if (commandString.length()==6){
                    return commandString.replaceAll("[a-zA-Z]", "").replaceAll("[0-9]", "").equals("[,]");
                }
            }
        }else return false;

        return false;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        if (checkCommand(commandString)){

            this.commandType = determineCommandType(commandString);

            if (determineCommandType(commandString)==CommandType.TRANSPORT || determineCommandType(commandString)==CommandType.ENTER){
                this.gridId = UUID.fromString(commandString.substring(commandString.indexOf(',') + 1, commandString.length() - 1));
            }else {
                this.numberOfSteps = Integer.parseInt(commandString.replaceAll("[^0-9]", ""));
            }

        }else throw new RuntimeException();

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
