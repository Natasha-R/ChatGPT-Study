package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface ExecutecommandInterface {
    Fieldminingmachinehashmap executecommand(String movecommand, String steps, String uuid, Miningmachinelist miningMachinelist, Fieldllist fieldlist,
                                             Connectionlist connectionlist, Fieldminingmachinehashmap fieldMiningMachineHashMap
            , UUID fieldwhereRobotisplacedon, UUID miningmachineuuid, Machinecoordinateshashmap machinecoordinateshashmap);
}
