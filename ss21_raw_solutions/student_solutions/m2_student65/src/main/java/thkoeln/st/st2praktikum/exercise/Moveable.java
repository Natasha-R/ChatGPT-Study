package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface Moveable {
    boolean move(Field field, Task task, HashMap<UUID,MiningMachine> miningMachines);
    void moveNorth(int numberOfSteps, Field field, HashMap<UUID,MiningMachine> miningMachines);
    void moveEast(int numberOfSteps, Field field, HashMap<UUID,MiningMachine> miningMachines);
    void moveSouth(int numberOfSteps, Field field, HashMap<UUID,MiningMachine> miningMachines);
    void moveWest(int numberOfSteps, Field field, HashMap<UUID,MiningMachine> miningMachines);
}
