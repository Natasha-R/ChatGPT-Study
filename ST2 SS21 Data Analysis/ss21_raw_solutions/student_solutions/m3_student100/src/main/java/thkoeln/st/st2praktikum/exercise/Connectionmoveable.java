package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Connectionmoveable extends MoveInterface {
    Coordinate moveInconnection(UUID miningmachineuuid, MiningMachinelist miningMachinelist, ConnectionList connectionslist, MiningMachineRepository miningMachineRepository, UUID fieldwhereRobotisplacedon);
}