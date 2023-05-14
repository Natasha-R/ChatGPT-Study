package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
}
