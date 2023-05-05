package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Wall;



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
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck();
        spaceShipDeck.setWidth(width);
        spaceShipDeck.setHeight(height);

        spaceShipDeckRepository.save(spaceShipDeck);

        return spaceShipDeck.getId();
    }

    /**
     * This method adds a wall to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        spaceShipDeckRepository.findById(spaceShipDeckId).get().setWalls(wall);
    }
}
