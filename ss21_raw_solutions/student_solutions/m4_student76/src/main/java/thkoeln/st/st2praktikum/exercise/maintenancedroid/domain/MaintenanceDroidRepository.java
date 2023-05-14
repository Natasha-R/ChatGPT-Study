package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import org.jboss.jandex.Main;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {

    MaintenanceDroid findMaintenanceDroidById(UUID maintenanceDroid);
    void deleteById(UUID maintenanceDroid);

    MaintenanceDroid getMaintenanceDroidById(UUID maintenanceDroid);
    MaintenanceDroid findMaintenanceDroidByName(String name);


}
