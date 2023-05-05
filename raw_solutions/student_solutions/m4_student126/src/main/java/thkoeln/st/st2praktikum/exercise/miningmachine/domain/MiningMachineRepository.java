package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MiningMachineRepository extends CrudRepository<MiningMachine, UUID> {

    void deleteById(UUID miningMachineId);
}
