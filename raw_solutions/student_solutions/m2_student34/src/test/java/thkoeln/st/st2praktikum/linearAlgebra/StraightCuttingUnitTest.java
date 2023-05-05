package thkoeln.st.st2praktikum.linearAlgebra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

public class StraightCuttingUnitTest {

    @Test
    public void testEntityVectorsShouldCutAt0() {
        // given
        var v1 = new ConcreteStraight(Vector.of(1, 0), Vector.of(0, 0));
        var v2 = new ConcreteStraight(Vector.of(0, 1), Vector.of(0, 0));

        var testSubject = new StraightCutting(v1, v2);
        // when
        var intersection = testSubject.calculateCut();
        // then
        Assertions.assertThat(intersection).isNotEmpty();
        Assertions.assertThat(intersection).hasValue(Vector.of(0, 0));
    }

    @Test
    public void testParallelVectorsShouldNotCut() {
        // given
        var v1 = new ConcreteStraight(Vector.of(1, 0), Vector.of(0, 0));
        var v2 = new ConcreteStraight(Vector.of(1, 0), Vector.of(1, 0));
        var testSubject = new StraightCutting(v1, v2);
        // when
        var intersection = testSubject.calculateCut();
        // then
        Assertions.assertThat(intersection).isEmpty();
    }

    @Test
    public void testCutShouldReturnIntersection() {
        // given
        var v1 = new ConcreteStraight(Vector.of(4, -4), Vector.of(1, 1));
        var v2 = new ConcreteStraight(Vector.of(4, -8), Vector.of(3, 3));
        var testSubject = new StraightCutting(v1, v2);
        // when
        var intersection = testSubject.calculateCut();
        // then
        Assertions.assertThat(intersection).isNotEmpty();
        Assertions.assertThat(intersection).hasValue(Vector.of(7, -5));
    }

    @Test
    public void testCutShouldReturnEmptyIfLambdaIsOutOfBoundsForThisVector() {
        // given
        var v1 = new ConcreteBoundedStraight(Vector.of(1, 0), Vector
                .of(0, 0), 1, 2);
        var v2 = new ConcreteStraight(Vector.of(0, 1), Vector.of(0, 0));
        var testSubject = new StraightCutting(v1, v2);
        // when
        var intersection = testSubject.calculateCut();
        // then
        Assertions.assertThat(intersection).isEmpty();
    }

    @Test
    public void testCutShouldReturnEmptyIfLambdaIsOutOfBoundsForOtherVector() {
        // given
        var v1 = new ConcreteStraight(Vector.of(0, 1), Vector.of(0, 0));
        var v2 = new ConcreteBoundedStraight(Vector.of(1, 0), Vector
                .of(0, 0), 1, 2);
        var pair = Pair.of(Vector.of(0, 0), 1);
        var testSubject = new StraightCutting(v1, v2);
        // when
        var intersection = testSubject.calculateCut();
        // then
        Assertions.assertThat(intersection).isEmpty();
    }
}
