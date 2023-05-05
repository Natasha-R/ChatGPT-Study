package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceDroidRepository {
    void save(MaintenanceDroid droid);

    MaintenanceDroid findById(Long id);

    void delete(MaintenanceDroid droid);

    List<MaintenanceDroid> findAll();
}
