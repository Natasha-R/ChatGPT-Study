package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Wall;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.repository.SpaceShipDeckRepository;


import java.util.Optional;
import java.util.UUID;


@Service
public class SpaceShipDeckService {

    private SpaceShipDeckRepository spaceShipDeckRepository;

    @Autowired
    public void SpaceShipDeckRepository(SpaceShipDeckRepository spaceShipDeckRepository) {
        this.spaceShipDeckRepository = spaceShipDeckRepository;
    }

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height, width);
        this.spaceShipDeckRepository.save(spaceShipDeck);

        return spaceShipDeck.getId();
    }

    /**
     * This method adds a wall to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        Optional<SpaceShipDeck> optionalSpaceShipDeck = this.spaceShipDeckRepository.findById(spaceShipDeckId);
        if(optionalSpaceShipDeck.isEmpty()) {
            throw new RuntimeException();
        }

        SpaceShipDeck spaceShipDeck = optionalSpaceShipDeck.get();
        spaceShipDeck.addWall(wall);
    }

    public SpaceShipDeck findById(UUID id) {
        return spaceShipDeckRepository.findById(id).orElseThrow();
    }

    public Iterable<SpaceShipDeck> findAll() {
        return spaceShipDeckRepository.findAll();
    }
}
