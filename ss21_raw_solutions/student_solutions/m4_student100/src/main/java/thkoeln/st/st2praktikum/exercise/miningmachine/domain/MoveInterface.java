package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;

import java.util.UUID;

public interface MoveInterface {
    Coordinate moveInconnection(UUID miningmachineuuid, MiningMachinelist miningMachinelist,
                                MiningMachineRepository miningMachineRepository,UUID fieldwhereRobotisplacedon, TransportCategoryRepository transportCategoryRepository);
    Coordinate moveNorth(Integer steps,  MiningMachinelist miningMachinelist, MiningMachineRepository miningMachineRepository,
                         UUID fieldwhereRobotisplacedon, FieldRepository fieldRepository);
    Coordinate moveSouth(Integer steps,  MiningMachinelist miningMachinelist,MiningMachineRepository miningMachineRepository,
                         UUID fieldwhereRobotisplacedon, FieldRepository fieldRepository);
    Coordinate moveEast(Integer steps,  MiningMachinelist miningMachinelist, MiningMachineRepository miningMachineRepository,
                        UUID fieldwhereRobotisplacedon, FieldRepository fieldRepository);
    Coordinate moveWest(Integer steps, MiningMachinelist miningMachinelist, MiningMachineRepository miningMachineRepository,
                        UUID fieldwhereRobotisplacedon, FieldRepository fieldRepository );
}
