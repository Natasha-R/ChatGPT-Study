package thkoeln.st.st2praktikum.map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.linearAlgebra.Point;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

@EqualsAndHashCode(callSuper = true)
@Getter
public class MapPosition extends Point implements Position {
    private final Map map;
    private final Vector coordinates;

    private MapPosition(Map map, Vector coordinates) {
        this.map = map;
        this.coordinates = coordinates;
    }

    public static MapPosition of(Map map,
                                 Vector coordinates) {
        return new MapPosition(map, coordinates);
    }

    public static MapPosition of(Position position, Map map) {
        return new MapPosition(map, position.getCoordinates());
    }

    public static MapPosition of(Position position, Vector coordinates) {
        return new MapPosition(position.getMap(),
                coordinates);
    }

    public double distance(Position otherPosition) {
        if (!this.map.equals(otherPosition.getMap())) {
            return Double.MAX_VALUE;
        }
        return otherPosition.getCoordinates().subtract(this.coordinates)
                .absoluteValue();
    }
}
