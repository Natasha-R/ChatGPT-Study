package thkoeln.st.st2praktikum.linearAlgebra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PointUnitTest {

    @Test
    public void testCutShouldCutStraight() {
        //given
        var point = new ConcretePoint(Vector.of(-1, -1));
        var straight = new ConcreteStraight(Vector.of(1, 1), Vector.of(0, 0));
        //when
        var result = point.cut(straight);
        //then
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result).hasValue(Vector.of(-1, -1));
    }

    @RequiredArgsConstructor
    @Getter
    private class ConcretePoint extends Point {
        private final Vector coordinates;
    }
}
