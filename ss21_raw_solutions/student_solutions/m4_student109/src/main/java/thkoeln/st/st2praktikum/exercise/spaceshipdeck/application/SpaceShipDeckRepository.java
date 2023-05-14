package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;

import java.util.UUID;

public interface SpaceShipDeckRepository extends CrudRepository<SpaceShipDeck, UUID> {
}
