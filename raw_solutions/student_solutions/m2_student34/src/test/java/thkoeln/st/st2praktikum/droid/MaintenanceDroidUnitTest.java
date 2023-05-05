package thkoeln.st.st2praktikum.droid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import thkoeln.st.st2praktikum.linearAlgebra.ConcreteBoundedStraight;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.*;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MaintenanceDroidUnitTest {

    @Test
    public void testDroidShouldMoveNorth() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(MapPosition
                .of(map, Vector.of(0, 0)), "test");
        when(map.maxMove(Mockito.any())).then(
                (Answer<Optional<Movement>>) invocationOnMock -> Optional
                        .of(invocationOnMock
                                .getArgument(0)));
        //when
        var result = droid.move(Command.createMapCommand(Direction.NORTH, 2));
        //then
        Assertions.assertThat(droid.getPosition())
                .extracting(Position::getCoordinates)
                .isEqualTo(Vector.of(0.5, 2.5));
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testDroidShouldMoveEast() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(MapPosition
                .of(map, Vector.of(0, 0)), "test");
        when(map.maxMove(Mockito.any())).then(
                (Answer<Optional<Movement>>) invocationOnMock -> Optional
                        .of(invocationOnMock
                                .getArgument(0)));
        //when
        var result = droid.move(Command.createMapCommand(Direction.EAST, 2));
        //then
        Assertions.assertThat(droid.getPosition())
                .extracting(Position::getCoordinates)
                .isEqualTo(Vector.of(2.5, 0.5));
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testDroidShouldMoveSouth() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(MapPosition
                .of(map, Vector.of(0, 0)), "test");
        when(map.maxMove(Mockito.any())).then(
                (Answer<Optional<Movement>>) invocationOnMock -> Optional
                        .of(invocationOnMock
                                .getArgument(0)));
        //when
        var result = droid.move(Command.createMapCommand(Direction.SOUTH, 2));
        //then
        Assertions.assertThat(droid.getPosition())
                .extracting(Position::getCoordinates)
                .isEqualTo(Vector.of(0.5, -1.5));
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testDroidShouldMoveWest() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(MapPosition
                .of(map, Vector.of(0, 0)), "test");
        when(map.maxMove(Mockito.any())).then(
                (Answer<Optional<Movement>>) invocationOnMock -> Optional
                        .of(invocationOnMock
                                .getArgument(0)));
        //when
        var result = droid.move(Command.createMapCommand(Direction.WEST, 2));
        //then
        Assertions.assertThat(droid.getPosition())
                .extracting(Position::getCoordinates)
                .isEqualTo(Vector.of(-1.5, 0.5));
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testDroidShouldOnlyMoveToBoundary() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(MapPosition
                .of(map, Vector.of(0, 0)), "test");

        var movement = mock(MapMovement.class);
        when(movement.getTargetPosition()).thenReturn(
                MapPosition.of(map, Vector.of(3.75, 2.5))
        );
        when(movement.getSourcePosition()).thenReturn(
                MapPosition.of(map, Vector.of(0, 0))
        );

        when(map.maxMove(Mockito.any()))
                .thenReturn(Optional.of(movement));
        //when
        var result = droid.move(Command.createMapCommand(Direction.WEST, 10));
        //then
        Assertions.assertThat(droid.getPosition())
                .extracting(Position::getCoordinates)
                .isEqualTo(Vector.of(3.5, 2.5));
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testDroidShouldReturnFalseWhenMovementIsNotPossible() {
        //given
        var map = mock(Map.class);
        when(map.maxMove(Mockito.any())).thenReturn(Optional.empty());
        var droid = new MaintenanceDroid(MapPosition.of(map, Vector.of(2, 0)),
                "test");

        //when
        var result = droid.move(Command.createMapCommand(Direction.NORTH, 2));
        //then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testDroidShouldEnterMapWhenEnterCommand() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(new StartPosition(), "test");
        when(map.maxMove(Mockito.any())).then(
                (Answer<Optional<Movement>>) invocationOnMock -> Optional
                        .of(invocationOnMock
                                .getArgument(0)));
        //when
        droid.move(Command.createEnterCommand(map));
        //then
        verify(map, times(1))
                .enter(droid, MapPosition.of(map, Vector.of(0.5, 0.5)));
    }

    @Test
    public void testDroidShouldLeaveAndEnterForConnectionCommand() {
        //given
        var sourceMap = mock(Map.class);
        var targetMap = mock(Map.class);
        var droid = new MaintenanceDroid(MapPosition
                .of(sourceMap, Vector.of(0, 0)), "test");
        when(targetMap.maxMove(Mockito.any())).then(
                (Answer<Optional<Movement>>) invocationOnMock -> Optional
                        .of(invocationOnMock
                                .getArgument(0)));
        when(sourceMap.leave(any())).thenReturn(true);
        when(targetMap.enter(any(), any())).thenReturn(true);
        //when
        droid.move(Command.createConnectionCommand(new Connection(
                MapPosition.of(sourceMap, Vector.of(0, 0)),
                MapPosition.of(targetMap, Vector.of(1, 1))
        )));
        //then
        verify(sourceMap, times(1)).leave(droid);
        verify(targetMap, times(1))
                .enter(droid, MapPosition.of(targetMap, Vector.of(1, 1)));
    }

    @Test
    public void testDroidShouldNotChangePositionWhenLeavingFailed() {
        //given
        var sourceMap = mock(Map.class);
        var targetMap = mock(Map.class);
        var droid = new MaintenanceDroid(MapPosition
                .of(sourceMap, Vector.of(0, 0)), "test");
        when(targetMap.maxMove(Mockito.any())).then(
                (Answer<Optional<Movement>>) invocationOnMock -> Optional
                        .of(invocationOnMock
                                .getArgument(0)));
        when(sourceMap.leave(any())).thenReturn(false);
        when(targetMap.enter(any(), any())).thenReturn(true);
        //when
        droid.move(Command.createConnectionCommand(new Connection(
                MapPosition.of(sourceMap, Vector.of(0, 0)),
                MapPosition.of(targetMap, Vector.of(1, 1))
        )));
        //then
        Assertions.assertThat(droid.getPosition())
                .isEqualTo(MapPosition.of(sourceMap, Vector.of(0, 0)));
    }

    @Test
    public void testDroidShouldNotChangePositionWhenEnteringFailed() {
        //given
        var targetMap = mock(Map.class);
        var droid = new MaintenanceDroid(new StartPosition(), "test");
        when(targetMap.maxMove(Mockito.any())).then(
                (Answer<Optional<Movement>>) invocationOnMock -> Optional
                        .of(invocationOnMock
                                .getArgument(0)));
        when(targetMap.enter(any(), any())).thenReturn(false);
        //when
        droid.move(Command.createEnterCommand(targetMap));
        //then
        Assertions.assertThat(droid.getPosition())
                .isEqualTo(new StartPosition());
    }

    @Test
    public void testGetSitesShouldReturnCorrectSites() {
        //given
        var map = mock(Map.class);
        var droid = new MaintenanceDroid(MapPosition.of(map, Vector.of(1.5,
                1.5)), "marcus");
        //when
        var result = droid.getSites();
        //then
        TestUtil.assertSites(Arrays.asList(result),
                Arrays.asList(
                        new ConcreteBoundedStraight(Vector.of(0.75, 2.25),
                                Vector.of(2.25, 2.25)),
                        new ConcreteBoundedStraight(Vector.of(0.75, 0.75),
                                Vector.of(2.25, 0.75)),
                        new ConcreteBoundedStraight(Vector.of(0.75, 0.75),
                                Vector.of(0.75, 2.25)),
                        new ConcreteBoundedStraight(Vector.of(2.25, 0.75),
                                Vector.of(2.25, 2.25))
                )
        );
    }
}
