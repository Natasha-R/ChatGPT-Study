package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface ExecutecommandInterface {
    Fieldminingmachinehashmap executeCommand(OrderType moveCommand, Integer steps, UUID uuid, Miningmachinelist miningMachinelist, Fieldllist fieldlist,
                                             Connectionlist connectionlist, Fieldminingmachinehashmap fieldMiningMachineHashMap
            , UUID fieldwhereRobotisplacedon, UUID miningmachineuuid, Machinecoordinateshashmap machinecoordinateshashmap);
}
