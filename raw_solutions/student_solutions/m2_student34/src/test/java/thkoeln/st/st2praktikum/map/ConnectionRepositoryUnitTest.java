package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.Arrays;

import static org.mockito.Mockito.mock;

public class ConnectionRepositoryUnitTest {

    private ConnectionRepository testSubject;

    @BeforeEach
    public void setup() {
        this.testSubject = new ConnectionRepository();
    }

    @Test
    public void testFindAllBySourceShouldFilterForSource() {
        //given
        var spaceShipDeck = mock(Map.class);
        var connections = Arrays.asList(
                new Connection(
                        MapPosition.of(spaceShipDeck, Vector.of(1, 1)),
                        MapPosition.of(spaceShipDeck, Vector.of(0, 0))
                ), new Connection(
                        MapPosition.of(mock(Map.class), Vector.of(1, 1)),
                        MapPosition.of(spaceShipDeck, Vector.of(0, 0))
                ), new Connection(
                        MapPosition.of(spaceShipDeck, Vector.of(2, 2)),
                        MapPosition.of(spaceShipDeck, Vector.of(1, 1))
                )
        );
        connections.forEach(this.testSubject::save);
        //when
        var result = this.testSubject.findAllBySource(
                MapPosition.of(spaceShipDeck, Vector.of(1, 1))
        );
        //then
        Assertions.assertThat(result).containsOnly(connections.get(0));
    }
}
