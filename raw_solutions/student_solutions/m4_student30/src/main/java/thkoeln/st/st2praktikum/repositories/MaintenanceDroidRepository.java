package thkoeln.st.st2praktikum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import java.util.UUID;

@Repository
public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
    public MaintenanceDroid getMaintenanceDroidByid(UUID id);
}
