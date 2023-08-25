package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface SearchInterface {
    FieldInterface findField(UUID fieldId);
    MiningMachine findMiningMachine(UUID miningMachineId);
    String getCoordinates(UUID miningMachineId);
    UUID getMiningMachineFieldId(UUID miningMachineId);
}
