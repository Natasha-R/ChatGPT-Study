package thkoeln.st.st2praktikum.droid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.map.BoundedStraight;
import thkoeln.st.st2praktikum.map.LinearSystem;
import thkoeln.st.st2praktikum.map.Map;
import thkoeln.st.st2praktikum.parser.Direction;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MaintenanceDroidUnitTest {

    @Test
    public void testDroidShouldMoveNorth() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(map, new double[]{0, 0}, mock(LinearSystem.class));
        when(map.maxMove(Mockito.any())).then(
                (Answer<BoundedStraight>) invocationOnMock -> invocationOnMock.getArgument(0));
        //when
        droid.move(Pair.of(Direction.NORTH, 2));
        //then
        Assertions.assertThat(droid.getPosition()).isEqualTo(new double[]{0.5, 2.5});
    }
    @Test
    public void testDroidShouldMoveEast() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(map, new double[]{0, 0}, mock(LinearSystem.class));
        when(map.maxMove(Mockito.any())).then(
                (Answer<BoundedStraight>) invocationOnMock -> invocationOnMock.getArgument(0));
        //when
        droid.move(Pair.of(Direction.EAST, 2));
        //then
        Assertions.assertThat(droid.getPosition()).isEqualTo(new double[]{2.5, 0.5});
    }

    @Test
    public void testDroidShouldMoveSouth() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(map, new double[]{0, 0}, mock(LinearSystem.class));
        when(map.maxMove(Mockito.any())).then(
                (Answer<BoundedStraight>) invocationOnMock -> invocationOnMock.getArgument(0));
        //when
        droid.move(Pair.of(Direction.SOUTH, 2));
        //then
        Assertions.assertThat(droid.getPosition()).isEqualTo(new double[]{0.5, -1.5});
    }

    @Test
    public void testDroidShouldMoveWest() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(map, new double[]{0, 0}, mock(LinearSystem.class));
        when(map.maxMove(Mockito.any())).then(
                (Answer<BoundedStraight>) invocationOnMock -> invocationOnMock.getArgument(0));
        //when
        droid.move(Pair.of(Direction.WEST, 2));
        //then
        Assertions.assertThat(droid.getPosition()).isEqualTo(new double[]{-1.5, 0.5});
    }

    @Test
    public void testDroidShouldOnlyMoveToBoundary() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(map, new double[]{0, 0}, mock(LinearSystem.class));
        var boundStraightMock = mock(BoundedStraight.class);
        when(boundStraightMock.getEndLambda()).thenReturn(1.0);
        when(boundStraightMock.at(1.0)).thenReturn(Optional.of(new double[]{-5, 0}));
        when(map.maxMove(Mockito.any()))
                .thenReturn(boundStraightMock);
        //when
        droid.move(Pair.of(Direction.WEST, 10));
        //then
        Assertions.assertThat(droid.getPosition()).isEqualTo(new double[]{-4.5, 0.5});
    }
}
