package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface MoveInterface {
    void moveInconnection(UUID miningmachineuuid, Miningmachinelist miningMachinelist, Connectionlist connectionslist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    void moveNorth(Integer steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    void moveSouth(Integer steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    void moveEast(Integer steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    void moveWest(Integer steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
}
