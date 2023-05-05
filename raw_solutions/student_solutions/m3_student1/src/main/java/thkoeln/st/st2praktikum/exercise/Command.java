package thkoeln.st.st2praktikum.exercise;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.CustomExceptions.InvalidStringFormatException;
import thkoeln.st.st2praktikum.exercise.CustomExceptions.NegativeStepNumberException;

import javax.persistence.Embeddable;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class Command {
    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    private void checkIfTaskStringIsValid(String taskString) {
        Pattern pattern = Pattern.compile("^\\[[(no|ea|so|we|en|tr)]+,[a-z0-9\\-]+]$");
        Matcher matcher = pattern.matcher(taskString);
        if (!matcher.matches()) {
            throw new InvalidStringFormatException("\"" + taskString + "\" is not a valid taskString in Task().");
        }
    }

    public CommandType getCommandType() {
        return commandType;
    }
    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }
    public UUID getGridId() { //TODO: Possible to rename this?
        return gridId;
    }

    /**
     * @param taskString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String taskString) {
        checkIfTaskStringIsValid(taskString);
        StringTokenizer st = new StringTokenizer(taskString, "[],");

        switch (st.nextToken()) {
            case "tr":
                this.commandType = CommandType.TRANSPORT;
                this.gridId = UUID.fromString(st.nextToken());
                break;
            case "en":
                this.commandType = CommandType.ENTER;
                this.gridId = UUID.fromString(st.nextToken());
                break;
            case "no":
                this.commandType = CommandType.NORTH;
                this.numberOfSteps = Integer.parseInt(st.nextToken());
                break;
            case "we":
                this.commandType = CommandType.WEST;
                this.numberOfSteps = Integer.parseInt(st.nextToken());
                break;
            case "so":
                this.commandType = CommandType.SOUTH;
                this.numberOfSteps = Integer.parseInt(st.nextToken());
                break;
            case "ea":
                this.commandType = CommandType.EAST;
                this.numberOfSteps = Integer.parseInt(st.nextToken());
                break;
            default:
                throw new InvalidStringFormatException("This means that checkIfTaskStringIsValid() failed to catch an invalid string: " + taskString);
        }
        if (this.numberOfSteps != null && this.numberOfSteps < 0) {
            throw new NegativeStepNumberException("\"" + taskString + "\" included a negative number of steps.");
        }
    }
    public Command(CommandType commandType, Integer numberOfSteps) {
        if (numberOfSteps < 0) {
            throw new NegativeStepNumberException(numberOfSteps + " was given but numberOfSteps cannot be negative in Task().");
        }
        this.commandType = commandType;
        this.numberOfSteps = numberOfSteps;
    }
    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }
}
