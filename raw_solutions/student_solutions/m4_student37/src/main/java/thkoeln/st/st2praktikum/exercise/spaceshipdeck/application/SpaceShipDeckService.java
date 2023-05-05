package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.extra.World;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;

import java.util.UUID;


@Service
public class SpaceShipDeckService {

    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;
    
    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height,width);
        World.getInstance().addSpace(spaceShipDeck);
        spaceShipDeckRepository.save(spaceShipDeck);
        return spaceShipDeck.getId();
    }

    /**
     * This method adds a obstacle to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {
        SpaceShipDeck spaceShipDeck = spaceShipDeckRepository.findById(spaceShipDeckId).orElseThrow(()->new RuntimeException());
        World.getInstance().addObstacleToSpace(spaceShipDeck,obstacle);
        spaceShipDeckRepository.save(spaceShipDeck);
    }
}
