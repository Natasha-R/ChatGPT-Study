package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

public class LinearSystemUnitTest {

    private LinearSystem ls;

    @BeforeEach
    public void setup() {
        this.ls = new LinearSystem();
    }

    @Test
    public void testConstructorShouldThrowExceptionWhenMatrixIsUnderSpecified() {
        //given
        double[][] matrix = new double[][]{
                {1, 1, 1, 1},
                {1, 1, 1, 1}
        };
        //when
        //then
        Assertions.assertThatThrownBy(() -> new LinearSystem().solve(matrix))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSolveShouldSolveFor2Variables() {
        //given
        var matrix = new double[][]{
                {5, 1, 25},
                {10, 4, 14}
        };
        //when
        var result = ls.solve(matrix);
        //then
        Assertions.assertThat(result).extracting(Pair::getFirst)
                .isEqualTo(new double[]{8.6, -18});
        Assertions.assertThat(result).extracting(Pair::getSecond)
                .isEqualTo(1);
    }

    @Test
    public void testSolveShouldSolveForOverSpecified() {
        //given
        var matrix = new double[][]{
                {5, 1, 25},
                {10, 4, 14},
                {20, 8, 28}
        };
        //when
        var result = ls.solve(matrix);
        //then
        Assertions.assertThat(result).extracting(Pair::getFirst)
                .isEqualTo(new double[]{8.6, -18});
        Assertions.assertThat(result).extracting(Pair::getSecond)
                .isEqualTo(1);
    }

    @Test
    public void testSolveShouldReturnInfinitySolutions() {
        //given
        var matrix = new double[][]{
                {1, 1, 1},
                {0, 0, 0}
        };
        //when
        var result = ls.solve(matrix);
        //then
        Assertions.assertThat(result).extracting(Pair::getSecond)
                .isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    public void testSolveShouldReturnZeroSolutions() {
        //given
        var matrix = new double[][]{
                {1, 1, 1},
                {1, 1, 2}
        };
        //when
        var result = ls.solve(matrix);
        //then
        Assertions.assertThat(result).extracting(Pair::getSecond)
                .isEqualTo(0);

    }

    @Test
    public void testSolveShouldSolveLSWithRowStartingWithZero() {
        //given
        var matrix = new double[][] {
                {0, 1, -2.75},
                {2, 0, 2.25}
        };
        //when
        var result = ls.solve(matrix);
        //then
        Assertions.assertThat(result).extracting(Pair::getSecond)
                .isEqualTo(1);
        Assertions.assertThat(result).extracting(Pair::getFirst)
                .isEqualTo(new double[]{1.125, -2.75});
    }

    @Test
    public void testSolveShouldSolveLSWithMultipleRowsStartingWithZero() {
        //given
        var matrix = new double[][] {
                {0, 1, 0, 6},
                {0, 0, 1, 5},
                {2, 0, 0, 4}
        };
        //when
        var result = ls.solve(matrix);
        //then
        Assertions.assertThat(result).extracting(Pair::getSecond)
                .isEqualTo(1);
        Assertions.assertThat(result).extracting(Pair::getFirst)
                .isEqualTo(new double[]{2, 6, 5});
    }
}
