package thkoeln.st.st2praktikum.linearAlgebra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointPointCuttingUnitTest {

    @Test
    public void testCalculateCutShouldReturnCutWhenIdentical() {
        //given
        var p1 = new ConcretePoint(Vector.of(1, 1));
        var p2 = new ConcretePoint(Vector.of(1, 1));
        var testSubject = new PointPointCutting(p1, p2);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).hasValue(Vector.of(1, 1));
    }

    @Test
    public void testCalculateCutShouldReturnEmptyWhenDifferent() {
        //given
        var p1 = new ConcretePoint(Vector.of(1, 1));
        var p2 = new ConcretePoint(Vector.of(1, 2));
        var testSubject = new PointPointCutting(p1, p2);
        //when
        var result = testSubject.calculateCut();
        //then
        Assertions.assertThat(result).isEmpty();
    }
}
