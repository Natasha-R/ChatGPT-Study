package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeckRepository;


import java.util.UUID;


@Service
public class SpaceShipDeckService {
    @Autowired
    public SpaceShipDeckService(SpaceShipDeckRepository spaceShipDecks) {
        this.spaceShipDecks = spaceShipDecks;
    }

    private final SpaceShipDeckRepository spaceShipDecks;
    
    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        SpaceShipDeck newSpaceShipDeck = new SpaceShipDeck(height, width);
        spaceShipDecks.save(newSpaceShipDeck);
        return newSpaceShipDeck.getSpaceShipDeckID();
    }

    /**
     * This method adds a obstacle to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {

        if (obstacle.getStart().getX().equals(obstacle.getEnd().getX())) {
            for (int i = obstacle.getStart().getY(); i < obstacle.getEnd().getY(); i++) {
                spaceShipDecks.findById(spaceShipDeckId).get().getAllObstacles().add(new Obstacle(new Coordinate(obstacle.getStart().getX(), i), new Coordinate(obstacle.getEnd().getX(), i + 1)));
            }
        }
        if (obstacle.getStart().getY().equals(obstacle.getEnd().getY())) {
            for (int i = obstacle.getStart().getX(); i < obstacle.getEnd().getX(); i++) {
                spaceShipDecks.findById(spaceShipDeckId).get().getAllObstacles().add(new Obstacle(new Coordinate(i, obstacle.getStart().getY()), new Coordinate(i + 1, obstacle.getEnd().getY())));
            }
        }
    }
}
