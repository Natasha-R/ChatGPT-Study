package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class BoundedStraightUnitTest {

    @Test
    public void testAtShouldReturnEmptyForLowerThanBegin() {
        //given
        var vector = new BoundedStraight(
                new int[]{1, 1},
                new int[] {0, 0},
                mock(LinearSystem.class),
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
        var vector = new BoundedStraight(
                new int[]{1, 1},
                new int[] {0, 0},
                mock(LinearSystem.class),
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
        var vector = new BoundedStraight(
                new int[]{1, 1},
                new int[] {0, 0},
                mock(LinearSystem.class),
                0,
                1
        );
        //when
        var result = vector.at(0.5);
        //then
        Assertions.assertThat(result).hasValue(new double[]{0.5, 0.5});
    }
}
