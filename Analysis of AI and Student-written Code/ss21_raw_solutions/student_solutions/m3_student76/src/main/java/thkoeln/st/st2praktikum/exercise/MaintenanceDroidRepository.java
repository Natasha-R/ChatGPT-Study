package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;

import java.util.Map;
import java.util.UUID;

public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, Long> {

    MaintenanceDroid findById(UUID maintenanceDroid);
    MaintenanceDroid getMaintenanceDroidById(UUID maintenanceDroid);
    MaintenanceDroid findMaintenanceDroidByName (String name);


}
