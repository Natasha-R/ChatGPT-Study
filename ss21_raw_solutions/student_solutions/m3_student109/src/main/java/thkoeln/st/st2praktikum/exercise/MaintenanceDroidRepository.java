package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
}
