package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.HashMap;
import java.util.UUID;

public interface Initializable {
    boolean initialize(UUID miningMachine, HashMap<UUID, MiningMachine> miningMachines, UUID gritID);
}
