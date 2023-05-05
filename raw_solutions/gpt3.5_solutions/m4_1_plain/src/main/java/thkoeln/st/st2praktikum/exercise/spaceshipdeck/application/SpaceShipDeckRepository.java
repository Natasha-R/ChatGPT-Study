package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpaceShipDeckRepository extends JpaRepository<SpaceShipDeck, Long> {
    Object save(SpaceShipDeck spaceShipDeck);
    Optional<SpaceShipDeck> findById(UUID id);
    void delete(SpaceShipDeck spaceShipDeck);
    List<SpaceShipDeck> findAll();
}
