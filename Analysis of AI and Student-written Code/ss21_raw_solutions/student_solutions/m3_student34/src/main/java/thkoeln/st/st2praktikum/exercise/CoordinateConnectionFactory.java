package thkoeln.st.st2praktikum.exercise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CoordinateConnectionFactory {

    private final SpaceShipDeckRepository spaceShipDeckRepository;

    public Connection createConnection(UUID sourceMapId, Coordinate sourceCoordinate, UUID targetMapId, Coordinate targetCoordinate) {
        var sourceSpaceShipDeck = this.spaceShipDeckRepository.findById(sourceMapId)
                .orElseThrow(NoSuchElementException::new);
        var sourcePosition = MapPosition.of(
                sourceSpaceShipDeck, Vector.of(sourceCoordinate).add(Vector.of(0.5, 0.5)));
        if (!sourcePosition.getMap().isInBounds(sourcePosition)) {
            throw new IllegalArgumentException("source position is not in map" +
                    " bounds");
        }
        var targetSpaceShipDeck = this.spaceShipDeckRepository.findById(targetMapId)
                .orElseThrow(NoSuchElementException::new);
        var targetPosition = MapPosition.of(
                targetSpaceShipDeck, Vector.of(targetCoordinate).add(Vector.of(0.5, 0.5)));
        if (!targetPosition.getMap().isInBounds(targetPosition)) {
            throw new IllegalArgumentException("target position is not in map" +
                    " bounds");
        }
        return new Connection(sourcePosition, targetPosition);
    }
}
