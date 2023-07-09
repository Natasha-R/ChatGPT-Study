package thkoeln.st.st2praktikum.droid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.Map;
import thkoeln.st.st2praktikum.map.MapPosition;
import thkoeln.st.st2praktikum.map.Position;
import thkoeln.st.st2praktikum.map.StartPosition;

import static org.mockito.Mockito.mock;

public class EnterMovementUnitTest {

    @Test
    public void testChangeTargetPositionShouldThrowException() {
        //given
        var testSubject = new EnterMovement(new StartPosition(),
                MapPosition.of(mock(Map.class), Vector.of(0, 0)));
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.changeTargetPosition(mock(Position.class)))
                .isInstanceOf(IllegalStateException.class);
    }
}
