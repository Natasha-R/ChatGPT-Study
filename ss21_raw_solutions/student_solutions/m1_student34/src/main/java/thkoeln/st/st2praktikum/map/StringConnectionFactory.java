package thkoeln.st.st2praktikum.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StringConnectionFactory {

    private final SpaceShipDeckRepository spaceShipDeckRepository;

    public Connection createConnection(UUID sourceMapId,
                                       String sourceCoordinateString,
                                       UUID targetMapId,
                                       String targetCoordinatesString) {
        var sourceCoordinates = Vector.of(sourceCoordinateString)
                .add(Vector.of(0.5, 0.5));
        var targetCoordinates = Vector.of(targetCoordinatesString)
                .add(Vector.of(0.5, 0.5));
        var sourcePosition = MapPosition.of(
                this.spaceShipDeckRepository.findById(sourceMapId)
                        .orElseThrow(NoSuchElementException::new),
                sourceCoordinates
        );
        if (!sourcePosition.getMap().isInBounds(sourcePosition)) {
            throw new IllegalArgumentException("source position is not in map" +
                    " bounds");
        }
        var targetPosition = MapPosition.of(
                this.spaceShipDeckRepository.findById(targetMapId)
                        .orElseThrow(NoSuchElementException::new),
                targetCoordinates
        );
        if (!targetPosition.getMap().isInBounds(targetPosition)) {
            throw new IllegalArgumentException("target position is not in map" +
                    " bounds");
        }
        return new Connection(sourcePosition, targetPosition);
    }
}
