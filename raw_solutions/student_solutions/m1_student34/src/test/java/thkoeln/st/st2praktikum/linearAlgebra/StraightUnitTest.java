package thkoeln.st.st2praktikum.linearAlgebra;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.data.util.Pair;

/*
 * The CuttingFactory with it's different Cutting implementations could not
 * be mocked, because the Straight uses the static getInstance method.
 */
public class StraightUnitTest {

    @Test
    public void testEntityVectorsShouldCutAt0() {
        // given
        var v1 = new ConcreteStraight(Vector.of(1, 0), Vector.of(0, 0));
        var v2 = new ConcreteStraight(Vector.of(0, 1), Vector.of(0, 0)
        );
        // when
        var intersection = v1.cut(v2);
        // then
        Assertions.assertThat(intersection).isNotEmpty();
        Assertions.assertThat(intersection).hasValue(Vector.of(0, 0));
    }

    @Test
    public void testParallelVectorsShouldNotCut() {
        // given
        var v1 = new ConcreteStraight(Vector.of(1, 0), Vector.of(0, 0)
        );
        var v2 = new ConcreteStraight(Vector.of(1, 0), Vector.of(1, 0)
        );
        // when
        var intersection = v1.cut(v2);
        // then
        Assertions.assertThat(intersection).isEmpty();
    }

    @Test
    public void testCutShouldReturnIntersection() {
        // given
        var v1 = new ConcreteStraight(Vector.of(4, -4), Vector.of(1, 1));
        var v2 = new ConcreteStraight(Vector.of(4, -8), Vector.of(3, 3));
        // when
        var intersection = v1.cut(v2);
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
        // when
        var intersection = v1.cut(v2);
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
        // when
        var intersection = v1.cut(v2);
        // then
        Assertions.assertThat(intersection).isEmpty();
    }

    @Test
    public void testAtShouldReturnStartPositionForLambda0() {
        //given
        var v1 = new ConcreteStraight(Vector.of(1, 0), Vector.of(3, 2));
        //when
        var result = v1.at(0.0);
        //then
        Assertions.assertThat(result).hasValue(Vector.of(3, 2));
    }

    @Test
    public void testAtShouldGo2BackwardsForLambdaMinusTwo() {
        //given
        var v1 = new ConcreteStraight(Vector.of(1, 0), Vector.of(3, 2));
        //when
        var result = v1.at(-2.0);
        //then
        Assertions.assertThat(result).hasValue(Vector.of(1, 2));
    }

    private ArgumentMatcher<double[][]> deepArrayEqualArgumentMatcher(double[][] expectedArray) {
        return new ArgumentMatcher<double[][]>() {
            @Override
            public boolean matches(double[][] doubles) {
                if (doubles.length == 0 && expectedArray.length == 0) {
                    return true;
                }
                if (doubles.length != expectedArray.length) {
                    return false;
                }
                if (doubles[0].length != expectedArray[0].length) {
                    return false;
                }
                for (int i = 0; i < expectedArray.length; i++) {
                    for (int j = 0; j < expectedArray[i].length; j++) {
                        if (doubles[i][j] != expectedArray[i][j]) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };
    }
}
