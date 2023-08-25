package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.exceptions.InvalidInputException;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.dto.CommandDto;

import javax.persistence.*;
import java.util.UUID;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@Access(AccessType.FIELD)
public class Command {
    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) throws InvalidInputException {
        if (!validateStepLength(numberOfSteps)) {
            throw new InvalidInputException("Number of steps for a command is not allowed to be negative.");
        }
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;

    }

    public Command(CommandType commandType, UUID gridId) throws InvalidInputException {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    public Command(CommandType commandType, Integer numberOfSteps, UUID gridId) {
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
        this.gridId = gridId;
    }

    public static Command fromString(String commandString ) throws InvalidInputException {
        return parseCommandFromString(commandString);
    }

    public static Command fromDto(CommandDto commandDto) throws InvalidInputException {
        return new Command(commandDto.getCommandType(), commandDto.getNumberOfSteps(), commandDto.getGridId());
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

    private static Command parseCommandFromString(String commandString) throws InvalidInputException {
        if (!validateCommandStringFormatting(commandString)) {
            throw new InvalidInputException("String formatting invalid");
        }
        Pair<String, String> commandSplit = separateCommandString(commandString);
        CommandType commandType = parseCommandStringToType(commandSplit.getLeft());

        if(getCommandStringValueType(commandType) == CommandStringValueType.INTEGER) {
            if(!validateStepLength(Integer.parseInt(commandSplit.getRight()))) {
                throw new InvalidInputException("Only positive movement numbers are permitted.");
            }
            Integer numberOfSteps = (Integer.parseInt(commandSplit.getRight()));
            return new Command(commandType, numberOfSteps);
        }
        if(getCommandStringValueType(commandType) == CommandStringValueType.UUID) {
            UUID gridId = UUID.fromString(commandSplit.getRight());
            return new Command(commandType, gridId);
        }
        throw new RuntimeException("Unexpected behavior while creating new Command from string. Case is undefined.");
    }

    private static Pair<String, String> separateCommandString(String commandString) {
        commandString = commandString.replaceAll("\\[", "").replaceAll("\\]", "");
        String[] commandSplit = commandString.split(",");
        return new Pair(commandSplit[0], commandSplit[1]);
    }

    private static Boolean validateCommandStringFormatting(String commandString) { // \[\w+,\d\]
        return commandString.matches("\\[\\w+,[\\w-]+\\]");
    }

    private static Boolean validateStepLength(Integer numberOfSteps) {
        return numberOfSteps > 0;
    }

    private static CommandType parseCommandStringToType(String commandStringCommandSection) throws InvalidInputException {
        CommandType command;
        switch (commandStringCommandSection) {
            case "no": command = CommandType.NORTH; break;
            case "ea": command = CommandType.EAST; break;
            case "so": command = CommandType.SOUTH; break;
            case "we": command = CommandType.WEST; break;
            case "tr": command = CommandType.TRANSPORT; break;
            case "en": command = CommandType.ENTER; break;
            default: throw new InvalidInputException("Unidentified CommandType in String");
        }
        return command;
    }

    private static CommandStringValueType getCommandStringValueType(CommandType command) {
        switch (command) {
            case ENTER:
            case TRANSPORT: return CommandStringValueType.UUID;
            default: return CommandStringValueType.INTEGER;
        }
    }

    private enum CommandStringValueType {
        UUID,
        INTEGER
    }
}
