package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Deck;

import java.util.UUID;

public interface DeckRepository extends CrudRepository<Deck, UUID> {
}
