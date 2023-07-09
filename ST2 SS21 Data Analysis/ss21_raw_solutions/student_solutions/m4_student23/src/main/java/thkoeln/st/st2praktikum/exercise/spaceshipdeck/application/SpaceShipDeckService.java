package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;

import java.util.UUID;


@Service
public class SpaceShipDeckService {

    private final SpaceShipDeckRepository deckRepository;


    @Autowired
    public SpaceShipDeckService(SpaceShipDeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    /**
     * This method creates a new spaceship deck.
     *
     * @param height the height of the spaceship deck
     * @param width  the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck newDeck = new SpaceShipDeck(width, height);
        deckRepository.save(newDeck);
        return newDeck.getUuid();
    }

    /**
     * This method adds a barrier to a given spaceship deck.
     *
     * @param spaceShipDeckId the ID of the spaceship deck the barrier shall be placed on
     * @param barrier         the end points of the barrier
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        SpaceShipDeck deck = this.deckRepository.getSpaceShipDeckByUUID(spaceShipDeckId);
        deck.addBarrier(barrier);
    }
}
