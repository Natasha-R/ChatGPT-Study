package thkoeln.st.st2praktikum.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MapService {

    private final SpaceShipDeckRepository spaceShipDeckRepository;
    private final ConnectionRepository connectionRepository;
    private final ObstacleRectangleObstacleFactory obstacleRectangleObstacleFactory;
    private final CoordinateConnectionFactory coordinateConnectionFactory;

    public UUID addMap(int height, int width) {
        var spaceShipDeck = SpaceShipDeck.SpaceShipDeckBuilder.builder()
                .setHeight(height)
                .setWidth(width)
                .build();
        return this.spaceShipDeckRepository.save(spaceShipDeck).getId();
    }

    public void addObstacle(UUID mapId, Obstacle obstacle) {
        var spaceShipDeck =
                this.spaceShipDeckRepository.findById(mapId)
                        .orElseThrow(NoSuchElementException::new);
        var newObstacle = this.obstacleRectangleObstacleFactory.createRectangleObstacle(obstacle);
        spaceShipDeck.addRectangleObstacle(newObstacle);
        this.spaceShipDeckRepository.save(spaceShipDeck);
    }

    public UUID addConnection(UUID sourceMapId, Coordinate sourceCoordinates,
                              UUID targetMapId, Coordinate targetCoordinates) {
        var connection = this.coordinateConnectionFactory.createConnection(
                sourceMapId, sourceCoordinates, targetMapId, targetCoordinates);
        return this.connectionRepository.save(connection).getId();
    }
}
