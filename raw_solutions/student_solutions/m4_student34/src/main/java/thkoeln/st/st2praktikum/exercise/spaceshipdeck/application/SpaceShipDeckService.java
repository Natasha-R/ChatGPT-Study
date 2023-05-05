package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;


import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@AllArgsConstructor
@Transactional
public class SpaceShipDeckService {

    private final SpaceShipDeckRepository spaceShipDeckRepository;
    
    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        var spaceShipDeck = new SpaceShipDeck(height, width);
        return this.spaceShipDeckRepository.save(spaceShipDeck).getId();
    }

    /**
     * This method adds a obstacle to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {
        var spaceShipDeck = this.spaceShipDeckRepository.findById(spaceShipDeckId)
                .orElseThrow(NoSuchElementException::new);
        spaceShipDeck.addObstacle(obstacle);
    }
}
