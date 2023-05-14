package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

import java.util.List;
import java.util.UUID;

public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
    public List<MaintenanceDroid> findBySpaceShipDeckId(UUID deckID);
}
