package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Directionmoveable extends MoveInterface {
    Coordinate moveNorth(Integer steps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                         UUID fieldwhereRobotisplacedon);
    Coordinate moveSouth(Integer steps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                         UUID fieldwhereRobotisplacedon);
    Coordinate moveEast(Integer steps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                        UUID fieldwhereRobotisplacedon);
    Coordinate moveWest(Integer steps, MiningMachinelist miningMachinelist, Fieldllist fieldlist, MiningMachineRepository miningMachineRepository,
                        UUID fieldwhereRobotisplacedon);
}
