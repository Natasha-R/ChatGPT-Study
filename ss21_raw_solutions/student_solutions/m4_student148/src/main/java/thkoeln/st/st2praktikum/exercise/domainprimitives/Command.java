package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.UUID;


@Getter
@Setter(AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Command(CommandType commandType, Integer numberOfSteps) {
        if (numberOfSteps > 0) {
            this.commandType = commandType;
            this.numberOfSteps = numberOfSteps;
        } else throw new IllegalArgumentException("You can't have negative steps, use the other direction!");
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

    public static Command fromString(String commandString) {
        String MoveCommandString = commandString.replace("[", "");
        MoveCommandString = MoveCommandString.replace("]", "");
        String[] moveArray = MoveCommandString.split(",");
        CommandType commandType = CommandType.of(moveArray[0]);
        if (commandType == CommandType.TRANSPORT || commandType == CommandType.ENTER) {
            UUID gridId = UUID.fromString(moveArray[1]);
            return new Command(commandType, gridId);
        } else {
            Integer numberOfSteps = Integer.parseInt(moveArray[1]);
            return new Command(commandType, numberOfSteps);
        }
    }

    protected Command() {
    }
}
