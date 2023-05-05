package thkoeln.st.st2praktikum.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MapService {

    private final SpaceShipDeckRepository spaceShipDeckRepository;
    private final TransportSystemRepository transportSystemRepository;
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

    public UUID addTransportSystem(String system) {
        return this.transportSystemRepository.save(new TransportSystem(system)).getId();
    }

    public UUID addConnection(UUID transportSystemId, UUID sourceMapId, Coordinate sourceCoordinates,
                              UUID targetMapId, Coordinate targetCoordinates) {
        var transportSystem = this.transportSystemRepository.findById(transportSystemId)
                .orElseThrow(NoSuchElementException::new);
        var connection = this.coordinateConnectionFactory.createConnection(
                sourceMapId, sourceCoordinates, targetMapId, targetCoordinates);
        if(!transportSystem.addConnection(connection)) {
            throw new IllegalStateException();
        }
        this.transportSystemRepository.save(transportSystem);
        return connection.getId();
    }
}
