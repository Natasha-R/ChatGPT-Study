package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface SpawnmachineInterface {
    Coordinate spawnMiningmachine
            (UUID fielduuid, Fieldllist fieldllist,
             MiningMachineRepository miningMachineRepository,
             UUID miningmachineuuid,
             Machinecoordinateshashmap machinecoordinatehashmap, MiningMachinelist miningMachineList
             );

}
