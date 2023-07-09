package thkoeln.st.st2praktikum.droid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.Position;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapMovementUnitTest {

    @Test
    public void changeTargetPositionShouldChangeTargetPosition() {
        //given
        var sourcePosition = mock(Position.class);
        var targetPosition = mock(Position.class);
        when(sourcePosition.getCoordinates()).thenReturn(Vector.of(0, 0));
        when(targetPosition.getCoordinates()).thenReturn(Vector.of(0, 0));
        var testSubject = new MapMovement(sourcePosition, targetPosition);
        var expectedTargetPosition = mock(Position.class);
        when(expectedTargetPosition.getCoordinates())
                .thenReturn(Vector.of(2, 3));
        //when
        var result = testSubject.changeTargetPosition(expectedTargetPosition);
        //then
        Assertions.assertThat(result).extracting(Movement::getTargetPosition)
                .isEqualTo(expectedTargetPosition);
        Assertions.assertThat(result).isNotSameAs(testSubject);
    }

    @Test
    public void testCutOwnDroidShouldReturnEmpty() {
        //given
        var droid = mock(Droid.class);

        var sourcePosition = mock(Position.class);
        var targetPosition = mock(Position.class);
        when(sourcePosition.getCoordinates()).thenReturn(Vector.of(0, 0));
        when(targetPosition.getCoordinates()).thenReturn(Vector.of(0, 0));
        var testSubject = new MapMovement(sourcePosition, targetPosition, droid);
        //when
        var result = testSubject.cut(droid);
        //then
        Assertions.assertThat(result).isEmpty();
    }
}
