package thkoeln.st.st2praktikum.exercise.command;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    private static final String TRANSPORT_COMMAND_STRING = "tr";
    private static final String SET_INITIAL_SPACE_COMMAND_STRING = "en";

    public static Command parse(String command) {
        final Pattern pattern = Pattern.compile("^\\[(.*),(.*)]$");
        final Matcher matcher = pattern.matcher(command);

        if(matcher.find()) {
           final String c = matcher.group(1);
           final String argument = matcher.group(2);
           return CreateCommand(c, argument);
        } else {
           throw new IllegalArgumentException();
        }
    }

    private static Command CreateCommand(String command, String argument) {
        if(command.equals(TRANSPORT_COMMAND_STRING)) {
            return new TransportCommand(UUID.fromString(argument));
        } else if(command.equals(SET_INITIAL_SPACE_COMMAND_STRING)) {
            return new SetInitialSpaceCommand(UUID.fromString(argument));
        }
        return parseWalkCommand(command, argument);
    }

    private static WalkCommand parseWalkCommand(String directionString, String stepsString) {
        Direction direction;
        switch (directionString) {
            case "no":
                direction = Direction.north;
                break;
            case "ea":
                direction = Direction.east;
                break;
            case "so":
                direction = Direction.south;
                break;
            case "we":
                direction = Direction.west;
                break;
            default:
                throw new UnsupportedOperationException();

        }

        return new WalkCommand(direction, Integer.parseInt(stepsString));
    }
}
