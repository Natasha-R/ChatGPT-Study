package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.data.util.Pair;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StraightUnitTest {

    private LinearSystem linearSystem;

    @BeforeEach
    public void setup() {
        this.linearSystem = mock(LinearSystem.class);
    }

    @Test
    public void testEntityVectorsShouldCutAt0() {
        // given
        var v1 = new Straight(new int[]{1, 0}, new int[]{0, 0}, this.linearSystem);
        var v2 = new Straight(new int[]{0, 1}, new int[]{0, 0}, this.linearSystem);
        when(linearSystem.solve(
                argThat(this.deepArrayEqualArgumentMatcher(new double[][]{
                                {1, 0, 0}, {0, -1, 0}
                        })
                ))
        ).thenReturn(Pair.of(new double[]{0, 0}, 1));
        // when
        var intersection = v1.cut(v2);
        // then
        Assertions.assertThat(intersection).isNotEmpty();
        Assertions.assertThat(intersection).hasValue(new double[]{0.0, 0.0});
    }

    @Test
    public void testParallelVectorsShouldNotCut() {
        // given
        var v1 = new Straight(new int[]{1, 0}, new int[]{0, 0}, this.linearSystem);
        var v2 = new Straight(new int[]{1, 0}, new int[]{1, 0}, this.linearSystem);
        when(linearSystem.solve(
                argThat(this.deepArrayEqualArgumentMatcher(new double[][]{
                                {1, -1, 1}, {0, 0, 0}
                        })
                ))
        ).thenReturn(Pair.of(new double[]{}, Integer.MAX_VALUE));
        // when
        var intersection = v1.cut(v2);
        // then
        Assertions.assertThat(intersection).isEmpty();
    }

    @Test
    public void testCutShouldReturnIntersection() {
        // given
        var v1 = new Straight(new int[]{2, 16}, new int[]{2, 4}, this.linearSystem);
        var v2 = new Straight(new int[]{-4, 8}, new int[]{8, 8}, this.linearSystem);
        when(linearSystem.solve(
                argThat(this.deepArrayEqualArgumentMatcher(new double[][]{
                                {2, 4, 6}, {16, -8, 4}
                        })
                ))
        ).thenReturn(Pair.of(
                new double[]{26.0 / 5, -11.0 / 10},
                1
        ));
        // when
        var intersection = v1.cut(v2);
        // then
        Assertions.assertThat(intersection).isNotEmpty();
        Assertions.assertThat(intersection).hasValue(new double[]{12.4, 87.2});
    }

    @Test
    public void testCutShouldReturnEmptyIfLambdaIsOutOfBoundsForThisVector() {
        // given
        var v1 = new BoundedStraight(new int[]{1, 0}, new int[]{0, 0}, this.linearSystem, 1, 2);
        var v2 = new Straight(new int[]{0, 1}, new int[]{0, 0}, this.linearSystem);
        when(linearSystem.solve(
                argThat(this.deepArrayEqualArgumentMatcher(new double[][]{
                                {1, 0, 0}, {0, -1, 0}
                        })
                ))
        ).thenReturn(Pair.of(new double[]{0, 0}, 1));
        // when
        var intersection = v1.cut(v2);
        // then
        Assertions.assertThat(intersection).isEmpty();
    }

    @Test
    public void testCutShouldReturnEmptyIfLambdaIsOutOfBoundsForOtherVector() {
        // given
        var v1 = new Straight(new int[]{0, 1}, new int[]{0, 0}, this.linearSystem);
        var v2 = new BoundedStraight(new int[]{1, 0}, new int[]{0, 0}, this.linearSystem, 1, 2);
        var pair = Pair.of(new double[]{0.0, 0.0}, 1);
        when(linearSystem.solve(
                argThat(this.deepArrayEqualArgumentMatcher(new double[][]{
                                {0.0, -1.0, 0.0}, {1.0, -0.0, 0.0}
                        })
                ))
        ).thenReturn(Pair.of(new double[]{0.0, 0.0}, 1));
        // when
        var intersection = v1.cut(v2);
        // then
        Assertions.assertThat(intersection).isEmpty();
    }

    @Test
    public void testAtShouldReturnStartPositionForLambda0() {
        //given
        var v1 = new Straight(new int[]{1, 0}, new int[]{3, 2}, this.linearSystem);
        //when
        var result = v1.at(0.0);
        //then
        Assertions.assertThat(result).hasValue(new double[]{3.0, 2.0});
    }

    @Test
    public void testAtShouldGo2BackwardsForLambdaMinusTwo() {
        //given
        var v1 = new Straight(new int[]{1, 0}, new int[]{3, 2}, this.linearSystem);
        //when
        var result = v1.at(-2.0);
        //then
        Assertions.assertThat(result).hasValue(new double[]{1.0, 2.0});
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
