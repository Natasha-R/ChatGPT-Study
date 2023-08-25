package thkoeln.st.st2praktikum.linearAlgebra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class StraightPointCuttingUnitTest {

    @Test
    public void testStraightShouldCutPoint() {
        //given
        var straight = new ConcreteStraight(Vector.of(1, 1), Vector.of(0, 0));
        var point = new ConcretePoint(Vector.of(2, 2));

        var testSubject = new StraightPointCutting(straight, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result).hasValue(Vector.of(2, 2));
    }

    @Test
    public void testStraightShouldNotCutPoint() {
        //given
        var straight = new ConcreteStraight(Vector.of(1, 2), Vector.of(0, 0));
        var point = new ConcretePoint(Vector.of(2, 2));

        var testSubject = new StraightPointCutting(straight, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testStraightShouldNotCutPointOutOfBounds() {
        //given
        var straight = new ConcreteBoundedStraight(Vector.of(1, 1), Vector.of(0,
                0), 0, 1);
        var point = new ConcretePoint(Vector.of(2, 2));

        var testSubject = new StraightPointCutting(straight, point);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).isEmpty();
    }
}
