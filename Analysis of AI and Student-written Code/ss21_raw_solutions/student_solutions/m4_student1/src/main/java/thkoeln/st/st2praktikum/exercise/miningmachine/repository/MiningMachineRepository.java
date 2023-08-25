package thkoeln.st.st2praktikum.exercise.miningmachine.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.UUID;

public interface MiningMachineRepository extends CrudRepository<MiningMachine, UUID> {
}
