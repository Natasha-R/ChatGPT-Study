package thkoeln.st.st2praktikum.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MapService {

    private final SpaceShipDeckRepository spaceShipDeckRepository;
    private final ConnectionRepository connectionRepository;
    private final StringRectangleObstacleFactory stringRectangleObstacleFactory;
    private final StringConnectionFactory stringConnectionFactory;

    public UUID addMap(int height, int width) {
        var spaceShipDeck = SpaceShipDeck.SpaceShipDeckBuilder.builder()
                .setHeight(height)
                .setWidth(width)
                .build();
        return this.spaceShipDeckRepository.save(spaceShipDeck).getId();
    }

    public void addObstacle(UUID spaceShipDeckId, String obstacleString) {
        var spaceShipDeck =
                this.spaceShipDeckRepository.findById(spaceShipDeckId)
                        .orElseThrow(NoSuchElementException::new);
        var obstacle =
                this.stringRectangleObstacleFactory.createRectangleObstacle(obstacleString);
        spaceShipDeck.addRectangleObstacle(obstacle);
        this.spaceShipDeckRepository.save(spaceShipDeck);
    }

    public UUID addConnection(UUID sourceMapId, String sourceCoordinates,
                              UUID targetMapId, String targetCoordinates) {
        var connection =
                this.stringConnectionFactory.createConnection(sourceMapId,
                        sourceCoordinates, targetMapId, targetCoordinates);
        return this.connectionRepository.save(connection).getId();
    }
}
