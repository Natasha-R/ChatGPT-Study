package thkoeln.st.st2praktikum.exercise.spaceshipdeck.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Obstacle;


import java.util.*;


@Service
public class SpaceShipDeckService {
    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;

    private Map<UUID, SpaceShipDeck> spaceShipDecks;

    public SpaceShipDeckService() {
        spaceShipDecks = new HashMap<>();
    }

    public UUID addSpaceShipDeck(Integer height, Integer width) {
        UUID spaceShipDeckId = UUID.randomUUID();
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(spaceShipDeckId, height, width);
        spaceShipDecks.put(spaceShipDeckId, spaceShipDeck);
        return spaceShipDeckId;
    }

    public void addObstacle(UUID spaceShipDeckId, String obstacleString) {
        Obstacle obstacle = Obstacle.parseObstacleString(obstacleString);
        SpaceShipDeck spaceShipDeck = spaceShipDecks.get(spaceShipDeckId);
        if (spaceShipDeck != null) {
            // Extract obstacle coordinates
            int startX = obstacle.getStartX();
            int startY = obstacle.getStartY();
            int endX = obstacle.getEndX();
            int endY = obstacle.getEndY();
            // Add obstacle to SpaceShipDeck
            spaceShipDeck.addObstacle(startX, startY, endX, endY);
        } else {
            throw new IllegalArgumentException("SpaceShipDeck not found with ID: " + spaceShipDeckId);
        }
    }

    public UUID addConnection(UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate, String transportCategory) {
        return null; // Implement this method for TransportCategoryService
    }

    public void saveSpaceShipDeck(SpaceShipDeck spaceShipDeck) {
        spaceShipDeckRepository.save(spaceShipDeck);
    }

    public Optional<SpaceShipDeck> findSpaceShipDeckById(UUID id) {
        return spaceShipDeckRepository.findById(id);
    }

    public void deleteSpaceShipDeck(SpaceShipDeck spaceShipDeck) {
        spaceShipDeckRepository.delete(spaceShipDeck);
    }

    public List<SpaceShipDeck> findAllSpaceShipDecks() {
        return spaceShipDeckRepository.findAll();
    }
}
