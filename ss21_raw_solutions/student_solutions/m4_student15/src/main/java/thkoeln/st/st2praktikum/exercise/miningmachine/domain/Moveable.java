package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;

import java.util.UUID;

public interface Moveable {
    void walk(UUID miningMachineId, int steps, int moveX, int moveY, MiningMachineRepository m);
    Boolean spawnMiningMachine(UUID miningMachineId, UUID fieldId, MiningMachineRepository m);
    Boolean transportMiningMachine(UUID miningMachineId, UUID fieldId, MiningMachineRepository m);
}


