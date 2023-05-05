package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface MiningMachineRepository extends CrudRepository<MiningMachine, UUID> {
    List<MiningMachine> findByFieldId(UUID fieldId);

    MiningMachine findByName(String name);
}
