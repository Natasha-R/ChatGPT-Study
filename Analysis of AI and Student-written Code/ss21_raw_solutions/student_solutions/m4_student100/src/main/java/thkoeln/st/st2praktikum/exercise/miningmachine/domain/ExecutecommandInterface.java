package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Machinecoordinateshashmap;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;

import java.util.UUID;

public interface ExecutecommandInterface {


    Coordinate executeCommand(OrderType moveCommand, Integer steps, UUID uuid, MiningMachinelist miningMachineList,  MiningMachineRepository miningMachineRepository
            , UUID fieldwhereRobotisplacedon, UUID miningmachineuuid, Machinecoordinateshashmap machinecoordinateshashmap ,
                              FieldRepository fieldRepository, TransportCategoryRepository transportCategoryRepository);
}
