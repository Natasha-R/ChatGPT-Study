package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {

        this.commandType = commandType;
        checkFormat(numberOfSteps);
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {


        switch (commandString.substring(1,3)){
            case "tr": tryUUID(commandString);commandType = CommandType.TRANSPORT;break;
            case "en": tryUUID(commandString);commandType = CommandType.ENTER;break;
            case "no": tryNumberOfSteps(commandString);commandType = CommandType.NORTH;break;
            case "ea": tryNumberOfSteps(commandString);commandType = CommandType.EAST;break;
            case "so": tryNumberOfSteps(commandString);commandType = CommandType.SOUTH;break;
            case "we": tryNumberOfSteps(commandString);commandType = CommandType.WEST;break;
            default: throw new WrongFormatException("Wrong Format for Command, try for example [no, 3], [en, <uuid>], [tr, <uuid>] ");
        }


    }

    private void tryUUID(String commandString){
        UUID gridIdFromString;
        try{
            gridIdFromString = UUID.fromString(commandString.substring(4, commandString.length()-1));
        }catch(Exception e){
            throw new WrongFormatException("Wrong Format for Command, try for example [en, <uuid>], [tr, <uuid>] ");
        }
        this.gridId = gridIdFromString;
    }

    private void tryNumberOfSteps(String commandString){
        int numberOfStepsFromString;
        try{
            numberOfStepsFromString = Integer.parseInt(commandString.substring(4).replace(']', ' ').trim());
        }catch(Exception e){
            throw new WrongFormatException("Wrong Format for Command, try for example [no, 3]");
        }

        checkFormat(numberOfStepsFromString);


    }

    private void checkFormat(int numberOfSteps){

        if(numberOfSteps < 0){
            throw new WrongFormatException("No negative number of steps allowed!");
        }

        this.numberOfSteps = numberOfSteps;
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
