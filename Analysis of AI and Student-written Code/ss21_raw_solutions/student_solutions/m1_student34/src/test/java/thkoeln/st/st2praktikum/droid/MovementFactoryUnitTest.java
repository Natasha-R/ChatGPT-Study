package thkoeln.st.st2praktikum.droid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.Connection;
import thkoeln.st.st2praktikum.map.Map;
import thkoeln.st.st2praktikum.map.MapPosition;


import static org.mockito.Mockito.mock;

public class MovementFactoryUnitTest {

    @Test
    public void testShouldThrowExceptionWhenSourcePositionAreNotEqualForConnection() {
        //given
        var sourcePosition = MapPosition.of(mock(Map.class),
                Vector.of(1, 2));
        var connection =
                new Connection(
                        MapPosition.of(
                                mock(Map.class),
                                Vector.of(0, 0)),
                        MapPosition.of(
                                mock(Map.class),
                                Vector.of(3, 4))
                );
        var command = Command.createConnectionCommand(connection);
        //when
        //then
        Assertions.assertThatThrownBy(() -> MovementFactory.getInstance()
                .createMovement(sourcePosition, command, mock(Droid.class)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
