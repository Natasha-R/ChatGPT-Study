package thkoeln.st.st2praktikum.exercise;
import thkoeln.st.st2praktikum.exercise.MiningMachine;
import java.util.HashMap;
import java.util.UUID;

public interface Initializable {
    boolean initialize(UUID miningMachine, HashMap<UUID,MiningMachine> miningMachines, UUID gritID);
}
