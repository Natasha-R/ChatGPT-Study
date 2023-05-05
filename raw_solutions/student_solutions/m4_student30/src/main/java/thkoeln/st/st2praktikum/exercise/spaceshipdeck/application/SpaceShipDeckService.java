package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.ShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Wall;
import thkoeln.st.st2praktikum.repositories.ShipDeckRepository;

import java.util.UUID;


@Service
public class SpaceShipDeckService {
    @Autowired
    private ShipDeckRepository shipDeckRepository;
    
    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        if (height < 1 || width < 1)
            throw new UnsupportedOperationException();

        ShipDeck shipDeck = new ShipDeck(height,width);
        shipDeckRepository.save(shipDeck);
        return shipDeck.getId();
    }

    /**
     * This method adds a wall to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {
        ShipDeck shipDeck = shipDeckRepository.getShipDeckByid(spaceShipDeckId);

        if(shipDeck== null)
            throw new UnsupportedOperationException();

        shipDeck.buildWall(wall);
    }
}
