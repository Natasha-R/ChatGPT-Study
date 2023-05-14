package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.UUID;

public interface MiningMachineRepo extends CrudRepository<MiningMachine, UUID> {
}
