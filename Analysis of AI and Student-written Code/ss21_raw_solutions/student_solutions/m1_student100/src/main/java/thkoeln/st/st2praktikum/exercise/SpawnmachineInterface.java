package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface SpawnmachineInterface {

    Fieldminingmachinehashmap spawnminingmachine
            (String fielduuid, Fieldllist fieldllist,
             Fieldminingmachinehashmap fieldMiningMachineHashMap,
             UUID miningmachineuuid,
             Machinecoordinateshashmap machinecoordinatehashmap,
             Miningmachinelist miningMachineList);

}
