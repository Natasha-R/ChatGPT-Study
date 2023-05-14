package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface MoveInterface {
    Fieldminingmachinehashmap moveinconnection(UUID miningmachineuuid, Miningmachinelist miningMachinelist, Connectionlist connectionslist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    Fieldminingmachinehashmap movenorth(String steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    Fieldminingmachinehashmap movesouth(String steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    Fieldminingmachinehashmap moveeast(String steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    Fieldminingmachinehashmap movewest(String steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
}
