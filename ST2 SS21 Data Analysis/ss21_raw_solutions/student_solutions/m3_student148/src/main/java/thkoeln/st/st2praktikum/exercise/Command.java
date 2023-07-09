package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Command {

    private final CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;


    public Command(CommandType commandType, Integer numberOfSteps) {
        if (numberOfSteps > 1) {
            this.commandType = commandType;
            this.numberOfSteps = numberOfSteps;
        } else throw new IllegalArgumentException("You can't have negative steps, time travel is not yet possible!");
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        String MoveCommandString = commandString.replace("[", "");
        MoveCommandString = MoveCommandString.replace("]", "");
        String[] moveArray = MoveCommandString.split(",");
        this.commandType = CommandType.of(moveArray[0]);
        if (this.commandType == CommandType.TRANSPORT || this.commandType == CommandType.ENTER) {
            this.gridId = UUID.fromString(moveArray[1]);
        } else this.numberOfSteps = Integer.parseInt(moveArray[1]);
    }
}
