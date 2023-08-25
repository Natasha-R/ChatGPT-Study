package thkoeln.st.st2praktikum.linearAlgebra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class BoundedStraightUnitTest {

    @Test
    public void testAtShouldReturnEmptyForLowerThanBegin() {
        //given
        var vector = new ConcreteBoundedStraight(
                Vector.of(1, 1),
                Vector.of(0, 0),
                0,
                1
        );
        //when
        var result = vector.at(-1);
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testAtShouldReturnEmptyForGreaterThanEnd() {
        //given
        var vector = new ConcreteBoundedStraight(
                Vector.of(1, 1),
                Vector.of(0, 0),
                0,
                1
        );
        //when
        var result = vector.at(2);
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testAtShouldReturnCorrectPosition() {
        //given
        var vector = new ConcreteBoundedStraight(
                Vector.of(1, 1),
                Vector.of(0, 0),
                0,
                1
        );
        //when
        var result = vector.at(0.5);
        //then
        Assertions.assertThat(result).hasValue(Vector.of(0.5, 0.5));
    }
}
