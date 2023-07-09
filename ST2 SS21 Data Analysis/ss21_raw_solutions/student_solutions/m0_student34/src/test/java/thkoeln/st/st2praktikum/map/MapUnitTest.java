package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapUnitTest {

    @Test
    public void testMaxMoveShouldReturnBoundedVectorWhenNoBoundariesAreGiven() {
        //given
        Map map = new Map(Collections.emptyList(), new LinearSystem());
        var move = mock(BoundedStraight.class);

        when(move.getBeginLambda()).thenReturn(1.0);
        when(move.at(1.0)).thenReturn(Optional.of(new double[]{2, 0}));
        when(move.getEndLambda()).thenReturn(7.0);
        when(move.at(7.0)).thenReturn(Optional.of(new double[]{7, 1}));
        //when
        var result = map.maxMove(move);
        //then
        Assertions.assertThat(result).extracting(Straight::getOffsetVector)
                .isEqualTo(new double[]{2, 0});
        Assertions.assertThat(result).extracting(Straight::getDirectionVector)
                .isEqualTo(new double[]{5, 1});
        Assertions.assertThat(result).extracting(BoundedStraight::getBeginLambda)
                .isEqualTo(0.0);
        Assertions.assertThat(result).extracting(BoundedStraight::getEndLambda)
                .isEqualTo(1.0);
    }

    @Test
    public void testMaxMoveShouldReturnMaximalPossibleMoveWithBoundaries() {
        //given
        var boundary = mock(Straight.class);
        var move = mock(BoundedStraight.class);

        when(move.cut(boundary)).thenReturn(Optional.of(new double[]{2, 2}));
        when(move.getBeginLambda()).thenReturn(0.0);
        when(move.at(0.0)).thenReturn(Optional.of(new double[]{0, 0}));

        Map map = new Map(Collections.singletonList(boundary), new LinearSystem());
        //when
        var result = map.maxMove(move);
        //then
        Assertions.assertThat(result).extracting(Straight::getOffsetVector)
                .isEqualTo(new double[]{0, 0});
        Assertions.assertThat(result).extracting(Straight::getDirectionVector)
                .isEqualTo(new double[]{2, 2});
        Assertions.assertThat(result).extracting(BoundedStraight::getBeginLambda)
                .isEqualTo(0.0);
        Assertions.assertThat(result).extracting(BoundedStraight::getEndLambda)
                .isEqualTo(1.0);
    }

    @Test
    public void testMaxMoveShouldReturnMaximalPossibleMoveWithMultipleBoundaries() {
        //given
        List<Straight> boundaries = Arrays.asList(
                mock(BoundedStraight.class),
                mock(BoundedStraight.class)
        );
        var move = mock(BoundedStraight.class);

        when(move.cut(boundaries.get(0))).thenReturn(Optional.of(new double[]{4, 0}));
        when(move.cut(boundaries.get(1))).thenReturn(Optional.of(new double[]{2, 2}));
        when(move.getBeginLambda()).thenReturn(0.0);
        when(move.at(0.0)).thenReturn(Optional.of(new double[]{0, 0}));

        Map map = new Map(boundaries, new LinearSystem());
        //when
        var result = map.maxMove(move);
        //then
        Assertions.assertThat(result).extracting(Straight::getOffsetVector)
                .isEqualTo(new double[]{0, 0});
        Assertions.assertThat(result).extracting(Straight::getDirectionVector)
                .isEqualTo(new double[]{2, 2});
        Assertions.assertThat(result).extracting(BoundedStraight::getBeginLambda)
                .isEqualTo(0.0);
        Assertions.assertThat(result).extracting(BoundedStraight::getEndLambda)
                .isEqualTo(1.0);
    }
}
