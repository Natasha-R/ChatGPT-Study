package thkoeln.st.st2praktikum.exercise.command;

import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.DataStorage;
import thkoeln.st.st2praktikum.exercise.field.Field;

@Getter
public class CommandProzessing {
    private CommandType commandType;
    private UUID fieldId;
    private int steps;

    @Setter
    boolean status;

    /**
     * @param commandString format [commandType,steps] or [commandType,field-id]
     */
    public CommandProzessing(String commandString) {
        try {
            String[] commands =
                    commandString.replace("[", "").replace("]", "").split(",");
            commandType = CommandType.valueOf(commands[0]);
            if (commandType.isMoveCommand()) {
                steps = Integer.parseInt(commands[1]);
                status = true;
            } else {
                fieldId = UUID.fromString(commands[1]);
                status = checkIfFieldIdExists();
            }
        } catch (Exception e) {
            status = false;
        }
    }

    public boolean checkIfFieldIdExists() {
        for (Map.Entry<UUID, Field> entry : DataStorage.getFieldMap().entrySet()) {
            if (fieldId.equals(entry.getValue().getUuid())) {
                return true;
            }
        }
        return false;
    }
}
