package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface ExecutecommandInterface {
    Coordinate executeCommand(OrderType moveCommand, Integer steps, UUID uuid, MiningMachinelist miningMachinelist, Fieldllist fieldlist,
                              ConnectionList connectionlist, MiningMachineRepository miningMachineRepository
            , UUID fieldwhereRobotisplacedon, UUID miningmachineuuid, Machinecoordinateshashmap machinecoordinateshashmap);
}
