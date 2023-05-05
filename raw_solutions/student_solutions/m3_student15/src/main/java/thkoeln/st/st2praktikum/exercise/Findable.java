package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Findable {
    Field findField(UUID fieldId);
    MiningMachine findMiningMachine(UUID miningMachineId);
    TransportCategory findTransportCategory(UUID categoryId);
}

