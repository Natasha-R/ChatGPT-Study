package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MiningMachineRepository extends CrudRepository<MiningMachine, UUID> {
    List<MiningMachine> findByFieldIdAndFieldIdIsNotNull(UUID uuid);
}
