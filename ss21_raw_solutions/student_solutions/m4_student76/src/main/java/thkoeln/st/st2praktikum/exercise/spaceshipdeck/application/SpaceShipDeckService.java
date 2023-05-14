package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.World;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;



import java.util.UUID;


@Service
public class SpaceShipDeckService {
    @Autowired
    private World world;

    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
       return world.createSpaceShipDeck(height, width);
    }

    /**
     * This method adds a barrier to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID spaceShipDeckId, Barrier barrier) {
        world.createBarrier(spaceShipDeckId,barrier);
    }
}
