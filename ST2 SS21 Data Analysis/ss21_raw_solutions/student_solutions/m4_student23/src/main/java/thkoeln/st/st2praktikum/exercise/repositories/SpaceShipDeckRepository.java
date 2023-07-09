package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.UUID;


public interface SpaceShipDeckRepository extends CrudRepository<SpaceShipDeck, UUID> {

    default SpaceShipDeck getSpaceShipDeckByUUID(UUID deckId) {
        Optional<SpaceShipDeck> deck = this.findById(deckId);
        if (deck.isPresent()) {
            return deck.get();
        }
        throw new InvalidParameterException("No SpaceShipDeck exists with the given UUID");
    }
}
