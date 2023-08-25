package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.Collections;

public class MapPositionUnitTest {

    @Test
    public void testDistanceShouldCalculateDistance() {
        //given
        var spaceShipDeck = new SpaceShipDeck(Collections.emptyList(), 10, 10);
        var p1 = MapPosition.of(spaceShipDeck, Vector.of(5, 4));
        var p2 = MapPosition.of(spaceShipDeck, Vector.of(8, 7));
        //when
        var distance = p1.distance(p2);
        //then
        Assertions.assertThat(distance).isCloseTo(4.2426,
                Offset.offset(0.0001));
    }

    @Test
    public void testDistanceShouldReturnInfinityWhenNotOnSameMap() {
        //given
        var p1 = MapPosition
                .of(new SpaceShipDeck(Collections.emptyList(), 10, 10), Vector
                        .of(5, 4));
        var p2 = MapPosition
                .of(new SpaceShipDeck(Collections.emptyList(), 10, 10), Vector
                        .of(8, 7));
        //when
        var distance = p1.distance(p2);
        //then
        Assertions.assertThat(distance).isEqualTo(Double.MAX_VALUE);
    }
}
