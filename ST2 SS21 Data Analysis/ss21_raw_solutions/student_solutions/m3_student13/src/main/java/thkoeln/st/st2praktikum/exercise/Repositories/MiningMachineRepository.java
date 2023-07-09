package thkoeln.st.st2praktikum.exercise.Repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.MiningMachine;

import java.util.UUID;

public interface MiningMachineRepository extends CrudRepository<MiningMachine, UUID> {
}
