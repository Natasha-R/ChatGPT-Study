package thkoeln.st.st2praktikum.droid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.map.Connection;
import thkoeln.st.st2praktikum.map.Position;

import static org.mockito.Mockito.mock;

public class ConnectionMovementUnitTest {

    @Test
    public void testChangeTargetPositionShouldThrowException() {
        //given
        var connection = mock(Connection.class);
        var testSubject = new ConnectionMovement(connection);
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject
                .changeTargetPosition(mock(Position.class)))
                .isInstanceOf(IllegalStateException.class);
    }
}
