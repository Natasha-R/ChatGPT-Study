package thkoeln.st.st2praktikum.linearAlgebra;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.map.SpaceShipDeck;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class VectorUnitTest {

    @Test
    public void testOfShouldReturnVector() {
        //given
        var input = "(-10,2)";
        //when
        var result = Vector.of(input);
        //then
        Assertions.assertThat(result).isEqualTo(Vector.of(-10, 2));
    }

    @Test
    public void testOfShouldThrowIllegalArgumentExceptionWhenParenthesisIsMissing() {
        //given
        var input = "1,0)";
        //when
        //then
        Assertions.assertThatThrownBy(() -> Vector.of(input)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testOfShouldThrowIllegalArgumentExceptionWhenCommaisIsMissing() {
        //given
        var input = "(10)";
        //when
        //then
        Assertions.assertThatThrownBy(() -> Vector.of(input)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testAddShouldAddTwoVectors() {
        //given
        var v1 = Vector.of(1, 2);
        var v2 = Vector.of(-3, 4);
        //when
        var result = v1.add(v2);
        //then
        Assertions.assertThat(result).isEqualTo(Vector.of(-2, 6));
    }

    @Test
    public void testAddShouldNotModifyVectors() {
        //given
        var v1 = Vector.of(1, 2);
        var v2 = Vector.of(-3, 4);
        //when
        var result = v1.add(v2);
        //then
        Assertions.assertThat(v1).isEqualTo(Vector.of(1, 2));
        Assertions.assertThat(v2).isEqualTo(Vector.of(-3, 4));
    }

    @Test
    public void testInverseShouldReturnInverseVector() {
        //given
        var v1 = Vector.of(3, -2);
        //when
        var result = v1.inverse();
        //then
        Assertions.assertThat(result).isEqualTo(Vector.of(-3, 2));
    }

    @Test
    public void testSubtractShouldReturnDifference() {
        //given
        var v1 = Vector.of(1, 2);
        var v2 = Vector.of(-3, 4);
        //when
        var result = v1.subtract(v2);
        //then
        Assertions.assertThat(result).isEqualTo(Vector.of(4, -2));
    }

    @Test
    public void testScalarProductShouldReturnMinusTwo() {
        //given
        var v1 = Vector.of(4, 2);
        var v2 = Vector.of(-3, 5);
        //when
        var result = v1.scalarProduct(v2);
        //then
        Assertions.assertThat(result).isEqualTo(-2);
    }

    @Test
    public void testMultiplyShouldReturnTripledVector() {
        //given
        var v1 = Vector.of(-2, 4);
        //when
        var result = v1.multiply(3);
        //then
        Assertions.assertThat(result).isEqualTo(Vector.of(-6, 12));
    }

    @Test
    public void testAbsoluteValueShouldReturnFive() {
        //given
        var v1 = Vector.of(5, 6);
        //when
        var result = v1.absoluteValue();
        //then
        Assertions.assertThat(result).isCloseTo(7.8102, Offset.offset(0.0001));
    }
}
