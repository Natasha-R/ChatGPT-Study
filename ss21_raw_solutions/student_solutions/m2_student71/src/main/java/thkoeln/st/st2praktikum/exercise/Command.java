package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
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

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {

        if(commandString.toCharArray()[0] != '[' || commandString.toCharArray()[commandString.length()-1] != ']')
            throw new RuntimeException("InvalidAttributeValueException");

        var fieldList = World.getInstance().getFieldList();

        var rawString = commandString.substring(1,commandString.length()-1);

        var command = rawString.split(",")[0];
        var information = rawString.split(",")[1];

        switch(command) {
            case "no":
                commandType = CommandType.NORTH;
                numberOfSteps = Integer.parseInt(information);

                break;
            case "ea":
                commandType = CommandType.EAST;
                numberOfSteps = Integer.parseInt(information);
                break;
            case "so":
                commandType = CommandType.SOUTH;
                numberOfSteps = Integer.parseInt(information);
                break;
            case "we":
                commandType = CommandType.WEST;
                numberOfSteps = Integer.parseInt(information);
                break;

            //teleport miningMachine
            case "tr":
                commandType = CommandType.TRANSPORT;
                gridId = UUID.fromString(information);
                //this.teleport(this.getCurrentTile().getConnection());
                break;

            //spawns miningMachine
            case "en":
                commandType = CommandType.ENTER;
                gridId = UUID.fromString(information);
                //var field = fieldList.get(UUID.fromString(information));
                //field.spawnMiningMachine(this,new Coordinates(0,0));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + command);
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
