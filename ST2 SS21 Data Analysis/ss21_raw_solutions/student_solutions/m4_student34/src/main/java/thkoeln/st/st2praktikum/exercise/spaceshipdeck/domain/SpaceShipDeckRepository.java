package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpaceShipDeckRepository extends CrudRepository<SpaceShipDeck, UUID> {
    Optional<SpaceShipDeck> findFirstByDroidsContains(MaintenanceDroid maintenanceDroid);
}
