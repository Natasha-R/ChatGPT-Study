package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpaceShipDeckRepository extends CrudRepository<SpaceShipDeck, UUID> {
    //SpaceShipDeck findSpaceShipDeckById(UUID spaceShipDeck);
    //Optional<SpaceShipDeck> findById(UUID spaceShipDeck);

}
