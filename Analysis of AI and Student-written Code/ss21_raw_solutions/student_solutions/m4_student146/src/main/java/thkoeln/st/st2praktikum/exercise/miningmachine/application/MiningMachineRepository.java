package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.UUID;

@Repository
public interface MiningMachineRepository extends  CrudRepository<MiningMachine, UUID> {
}
