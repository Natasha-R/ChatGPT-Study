package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceDroidRepository {
    MaintenanceDroid save(MaintenanceDroid droid);

    Optional<MaintenanceDroid> findById(Long id);

    void delete(MaintenanceDroid droid);

    List<MaintenanceDroid> findAll();
}
