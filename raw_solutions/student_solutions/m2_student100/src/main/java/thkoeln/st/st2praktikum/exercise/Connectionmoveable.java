package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Connectionmoveable extends MoveInterface {
    void moveInconnection(UUID miningmachineuuid, Miningmachinelist miningMachinelist, Connectionlist connectionslist, Fieldminingmachinehashmap FieldMiningMachineHashMap, UUID fieldwhereRobotisplacedon);
}