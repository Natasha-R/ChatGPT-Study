package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.DeckRepository;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Wall;



import java.util.UUID;

@Service
public class SpaceShipDeckService {

    @Getter
    private final DeckRepository decks;

    @Autowired
    public SpaceShipDeckService(DeckRepository decks) {
        this.decks = decks;
    }
    
    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        final SpaceShipDeck spaceShipDeck = new SpaceShipDeck(height, width);
        this.decks.save(spaceShipDeck);
        return spaceShipDeck.getSpaceShipDeckId();
    }


    /**
     * This method adds a wall to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID spaceShipDeckId, Wall wall) {


        SpaceShipDeck spaceShipDeck = decks.findById(spaceShipDeckId).get();
        spaceShipDeck.addWall(wall);
        this.decks.save(spaceShipDeck);
    }
}
