package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

import java.util.UUID;

public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
}
