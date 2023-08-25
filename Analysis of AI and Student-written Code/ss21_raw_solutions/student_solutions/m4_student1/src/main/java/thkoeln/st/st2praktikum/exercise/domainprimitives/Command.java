package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Setter
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    private static String INITIALISATION_ORDER = "en";
    private static String TRAVEL_ORDER = "tr";

    protected Command() {
        
    }

    public Command(CommandType commandType, Integer numberOfSteps) {
        this.commandType = commandType;
        if(numberOfSteps < 0) throw new RuntimeException();
        this.numberOfSteps = numberOfSteps;
        
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

    public static Command fromString(String commandString ) {
        final String regex = "\\[(.{2}),(.*)\\]";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(commandString);
        CommandType parsedOrderType;
        UUID parsedGridId;
        int parsedNumberOfSteps;

        String orderType = "";
        String orderOption = "";
        while (matcher.find()) {
            orderType = matcher.group(1);
            orderOption = matcher.group(2);
        }

        if(orderType.equals(Command.INITIALISATION_ORDER)) {
            parsedOrderType = CommandType.ENTER;
            parsedGridId = UUID.fromString(orderOption);
        } else if(orderType.equals(TRAVEL_ORDER)) {
            parsedOrderType = CommandType.TRANSPORT;
            parsedGridId = UUID.fromString(orderOption);
        } else {
            parsedOrderType = directionToOrderType(orderType);
            parsedNumberOfSteps = Integer.parseInt(orderOption);
            if(parsedNumberOfSteps < 0) {
                throw new RuntimeException();
            }
            return new Command(parsedOrderType, parsedNumberOfSteps);
        }

        return new Command(parsedOrderType, parsedGridId);    }

    private static CommandType directionToOrderType(String orderDirection) {
        switch (orderDirection) {
            case "we":
                return CommandType.WEST;
            case "ea":
                return CommandType.EAST;
            case "so":
                return CommandType.SOUTH;
            case "no":
                return CommandType.NORTH;
        }
        throw new RuntimeException("Not a supported direction: " + orderDirection);
    }

    public boolean isInitialisationCommand() {
        return this.commandType == CommandType.ENTER;
    }

    public boolean isTransportCommand() {
        return this.commandType == CommandType.TRANSPORT;
    }

    public boolean isMoveCommand() {
        return this.commandType == CommandType.WEST || this.commandType == CommandType.EAST || this.commandType == CommandType.SOUTH || this.commandType == CommandType.NORTH;
    }
    
}
