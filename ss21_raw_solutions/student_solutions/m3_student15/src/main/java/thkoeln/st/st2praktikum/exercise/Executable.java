package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Executable {
    Boolean executeCommand(UUID miningMachineId, Task task);
}

