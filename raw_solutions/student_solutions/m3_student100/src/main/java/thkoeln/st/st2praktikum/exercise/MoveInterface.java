package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface MoveInterface {
    Coordinate moveInconnection(UUID miningmachineuuid, MiningMachinelist miningMachinelist, ConnectionList connectionslist,
                                MiningMachineRepository miningMachineRepository, UUID fieldwhereRobotisplacedon);
    Coordinate moveNorth(Integer steps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                         UUID fieldwhereRobotisplacedon);
    Coordinate moveSouth(Integer steps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                         UUID fieldwhereRobotisplacedon);
    Coordinate moveEast(Integer steps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                        UUID fieldwhereRobotisplacedon);
    Coordinate moveWest(Integer steps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                        UUID fieldwhereRobotisplacedon);
}
