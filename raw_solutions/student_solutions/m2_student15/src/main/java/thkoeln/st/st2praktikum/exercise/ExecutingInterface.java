package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface ExecutingInterface {
    Boolean executeCommand(UUID miningMachineId, Task task);
}
