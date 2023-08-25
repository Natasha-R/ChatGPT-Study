package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


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

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        if (!commandString.substring(1, 4).equals("en,") &&
                !commandString.substring(1,4).equals("tr,") &&
                !commandString.substring(1,4).equals("no,") &&
                !commandString.substring(1,4).equals("ea,") &&
                !commandString.substring(1,4).equals("so,") &&
                !commandString.substring(1,4).equals("we,")){
            throw new RuntimeException("No valid command input.");
        }



        switch (commandString.substring(1,3)){

            case "no": this.commandType = CommandType.NORTH;
                this.numberOfSteps = Character.getNumericValue(commandString.charAt(4));
                break;

            case "ea": this.commandType = CommandType.EAST;
                this.numberOfSteps = Character.getNumericValue(commandString.charAt(4));
                break;

            case "so": this.commandType = CommandType.SOUTH;
                this.numberOfSteps = Character.getNumericValue(commandString.charAt(4));
                break;

            case "we": this.commandType = CommandType.WEST;
                this.numberOfSteps = Character.getNumericValue(commandString.charAt(4));
                break;

            case "en": this.commandType = CommandType.ENTER;
                this.gridId = UUID.fromString(commandString.substring(4,40));
                break;

            case "tr": this.commandType = CommandType.TRANSPORT;
                this.gridId = UUID.fromString(commandString.substring(4,40));
                break;
        }
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
