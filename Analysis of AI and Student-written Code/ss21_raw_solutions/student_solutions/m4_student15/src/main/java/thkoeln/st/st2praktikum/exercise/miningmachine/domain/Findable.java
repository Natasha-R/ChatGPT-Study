package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.field.application.Field;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategory;

import java.util.UUID;

public interface Findable {
    Field findField(UUID fieldId);
    MiningMachine findMiningMachine(UUID miningMachineId);
    TransportCategory findTransportCategory(UUID categoryId);
}

