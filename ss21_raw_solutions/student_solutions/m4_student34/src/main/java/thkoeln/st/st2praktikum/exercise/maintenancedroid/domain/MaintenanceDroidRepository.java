package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
}
