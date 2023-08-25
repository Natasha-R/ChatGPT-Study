package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;


import java.util.UUID;


@Service
public class SpaceShipDeckService {


    private SpaceShipDeckRepository spaceShipDeckRepository;

    @Autowired
    public SpaceShipDeckService( SpaceShipDeckRepository spaceShipDeckRepository) {
        this.spaceShipDeckRepository = spaceShipDeckRepository;


    }


    public SpaceShipDeck SSDfindById( UUID id ) {
        SpaceShipDeck spaceShipDeck = spaceShipDeckRepository.findById( id ).orElseThrow( () ->
                new RuntimeException( "No dungeon with ID " + id + " can be found.") );
        return spaceShipDeck;
    }
    
    /**
     * This method creates a new spaceship deck.
     * @param height the height of the spaceship deck
     * @param width the width of the spaceship deck
     * @return the UUID of the created spaceship deck
     */
    public UUID addSpaceShipDeck(Integer height, Integer width) {
        UUID spaceShipDeskId = UUID.randomUUID();
        spaceShipDeckRepository.save(new SpaceShipDeck(height, width, spaceShipDeskId));
        return spaceShipDeskId;
    }

    /**
     * This method adds a obstacle to a given spaceship deck.
     * @param spaceShipDeckId the ID of the spaceship deck the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceShipDeckId, Obstacle obstacle) {
                SpaceShipDeck spaceShipDeck = SSDfindById(spaceShipDeckId);
                if (spaceShipDeck.getHeight() < obstacle.getStart().getY() || spaceShipDeck.getWidth() < obstacle.getStart().getX() ||
                        spaceShipDeck.getHeight() < obstacle.getEnd().getY() ||spaceShipDeck.getWidth() < obstacle.getEnd().getX()) {
                    throw new RuntimeException("Obstacle invalid");
                }
                spaceShipDeck.addObstacleToList(obstacle);
                spaceShipDeckRepository.save(spaceShipDeck);
            }
        }



