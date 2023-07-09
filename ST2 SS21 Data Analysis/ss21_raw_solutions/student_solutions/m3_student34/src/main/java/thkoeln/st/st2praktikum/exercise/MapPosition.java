package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@EqualsAndHashCode(callSuper = true)
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MapPosition extends Point implements Position {
    public static MapPosition NULL = new MapPosition(null, Vector.of(Double.MAX_VALUE, Double.MAX_VALUE)) {
    };

    @Embedded
    private Vector coordinates;
    /* only for persisting */
    //TODO: refactor this association
    @Getter(AccessLevel.PRIVATE)
    @ManyToOne
    private SpaceShipDeck spaceShipDeck;

    private MapPosition(Map map, Vector coordinates) {
        this.spaceShipDeck = (SpaceShipDeck) map;
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
        if (!this.getMap().equals(otherPosition.getMap())) {
            return Double.MAX_VALUE;
        }
        return otherPosition.getCoordinates().subtract(this.coordinates)
                .absoluteValue();
    }

    public Map getMap() {
        return this.spaceShipDeck;
    }
}
