package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Map;
import java.util.UUID;

@Embeddable
@Getter
@Setter
public class Command {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;

    public Command(){}
    public Command(CommandType commandType, Integer numberOfSteps) {
        if (numberOfSteps < 0)
            throw new RuntimeException();
        else {
            this.commandType = commandType;
            this.numberOfSteps = numberOfSteps;
        }
    }

    public Command(CommandType commandType, UUID gridId) {
        this.commandType = commandType;
        this.gridId = gridId;
    }

    /**
     * @param commandString the command in form of a string e.g. [no, 3], [en, <uuid>], [tr, <uuid>]
     */
    public Command(String commandString) {
        String[] paramDecomposition = commandString.split(",");
        Map<String, CommandType> deplacement = Map.of("no", CommandType.NORTH, "so", CommandType.SOUTH, "ea", CommandType.EAST, "we", CommandType.WEST);
        if (paramDecomposition.length == 2) {
            if (deplacement.containsKey(paramDecomposition[0].substring(1))) {
                this.commandType = deplacement.get(paramDecomposition[0].substring(1));
                this.numberOfSteps = Integer.valueOf(paramDecomposition[1].substring(0, 1));
            } else if (paramDecomposition[0].substring(1).equals("tr") || paramDecomposition[0].substring(1).equals("en")) {
                this.gridId = UUID.fromString(paramDecomposition[1].substring(0, paramDecomposition[1].length() - 1));
                if (paramDecomposition[0].substring(1).equals("tr"))
                    this.commandType = CommandType.TRANSPORT;
                if (paramDecomposition[0].substring(1).equals("en"))
                    this.commandType = CommandType.ENTER;
            } else
                throw new RuntimeException();
        } else
            throw new RuntimeException();
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
