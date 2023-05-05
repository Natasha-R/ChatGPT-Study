package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface Initializable {
    boolean initialize(HashMap<UUID,MiningMachine> miningMachines, String initialzeCommand);
}
