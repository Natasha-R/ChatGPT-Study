package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface SpawnmachineInterface {
    void spawnMiningmachine
            (UUID fielduuid, Fieldllist fieldllist,
             Fieldminingmachinehashmap fieldMiningMachineHashMap,
             UUID miningmachineuuid,
             Machinecoordinateshashmap machinecoordinatehashmap,
             Miningmachinelist miningMachineList);

}
