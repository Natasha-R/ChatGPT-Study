package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Movable {

    Boolean moveTo(UUID miningMachineId, Command moveCommandString);

}
