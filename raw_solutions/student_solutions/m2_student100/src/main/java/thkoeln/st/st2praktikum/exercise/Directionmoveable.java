package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Directionmoveable extends MoveInterface {
    void moveNorth(Integer steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    void moveSouth(Integer steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    void moveEast(Integer steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
    void moveWest(Integer steps, Miningmachinelist miningMachinelist, Fieldllist fieldlist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
}
