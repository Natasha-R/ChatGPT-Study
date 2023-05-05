package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Command(CommandType commandType, Integer numberOfSteps) {
        if(numberOfSteps < 0)
            throw new IllegalArgumentException("numberOfSteps < 0");
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
        this.commandType = CommandParser.parseCommandType(commandString);

        switch(this.commandType){
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                this.numberOfSteps = CommandParser.getSteps(commandString);
                break;
            case TRANSPORT:
            case ENTER:
                this.gridId = CommandParser.getUUID(commandString);
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

    private static class CommandParser {
        private static Matcher getMatcher(String command) {
            final Pattern pattern = Pattern.compile("^\\[(.*),(.*)]$");
            final Matcher matcher = pattern.matcher(command);

            if(!matcher.find()) {
                throw new IllegalArgumentException();
            }
            return matcher;
        }

        private static CommandType parseCommandType(String commandString) {
            final String command = getCommand(commandString);
            switch (command) {
                case "no":
                    return CommandType.NORTH;
                case "ea":
                    return CommandType.EAST;
                case "so":
                    return CommandType.SOUTH;
                case "we":
                    return CommandType.WEST;
                case "tr":
                    return CommandType.TRANSPORT;
                case "en":
                    return CommandType.ENTER;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        private static Integer getSteps(String commandString) {
            final String argument = getArgument(commandString);
            return Integer.parseUnsignedInt(argument);
        }
        private static UUID getUUID(String commandString) {
            final String argument = getArgument(commandString);
            return UUID.fromString(argument);
        }

        private static String getCommand(String commandString) {
            final Matcher matcher = getMatcher(commandString);
            return matcher.group(1);
        }

        private static String getArgument(String commandString) {
            final Matcher matcher = getMatcher(commandString);
            return matcher.group(2);
        }
    }
}
