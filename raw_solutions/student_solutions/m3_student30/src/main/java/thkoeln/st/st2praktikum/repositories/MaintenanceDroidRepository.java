package thkoeln.st.st2praktikum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.ShipDeck;

import java.util.UUID;
@Repository
public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
    public MaintenanceDroid getMaintenanceDroidByid(UUID id);
}
