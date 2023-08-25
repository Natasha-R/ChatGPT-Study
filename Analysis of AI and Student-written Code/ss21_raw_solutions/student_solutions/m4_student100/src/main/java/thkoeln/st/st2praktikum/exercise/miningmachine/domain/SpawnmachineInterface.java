package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Machinecoordinateshashmap;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.util.UUID;

public interface SpawnmachineInterface {
    Coordinate spawnMiningmachine
            (UUID fielduuid, MiningMachinelist miningMachineList,
             MiningMachineRepository miningMachineRepository,
             UUID miningmachineuuid,
             Machinecoordinateshashmap machinecoordinatehashmap
            );

}
