package thkoeln.st.st2praktikum.exercise.spaceshipdeck.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface SpaceShipDeckRepository extends CrudRepository<SpaceShipDeck, UUID> {
}
