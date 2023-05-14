package thkoeln.st.st2praktikum.map;

import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.droid.*;
import thkoeln.st.st2praktikum.linearAlgebra.ConcreteStraight;
import thkoeln.st.st2praktikum.linearAlgebra.Cuttable;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class SpaceShipDeckUnitTest {

    @Test
    public void testMaxMoveShouldReturnMovementWhenNoBoundariesAreGiven() {
        //given
        SpaceShipDeck spaceShipDeck =
                new SpaceShipDeck(Collections.emptyList(), 10, 10);
        var expectedTargetPosition = MapPosition.of(spaceShipDeck,
                Vector.of(0, 0));
        var move = mock(Movement.class);
        when(move.getSourcePosition()).thenReturn(MapPosition.of(spaceShipDeck,
                Vector.of(1, 1)));
        when(move.getTargetPosition()).thenReturn(expectedTargetPosition);
        when(move.changeTargetPosition(expectedTargetPosition))
                .thenReturn(move);
        //when
        var result = spaceShipDeck.maxMove(move);
        //then
        Assertions.assertThat(result.orElseThrow()).isEqualTo(move);
    }

    @Test
    public void testMaxMoveShouldReturnMaximalPossibleMoveWithBoundary() {
        //given
        var boundary = mock(Obstacle.class);
        var testSubject = new SpaceShipDeck(Collections
                .singletonList(boundary), 10, 10);

        var sourcePosition = spy(MapPosition
                .of(testSubject, Vector.of(0, 0)));
        var targetPosition = MapPosition.of(testSubject, Vector.of(2, 2));
        var expectedTargetPosition = MapPosition.of(testSubject, Vector.of(1,
                1));
        when(sourcePosition.distance(targetPosition)).thenReturn(2.0);
        when(sourcePosition.distance(expectedTargetPosition)).thenReturn(1.0);

        var move = spy(new MovementStub());
        move.setSourcePosition(sourcePosition);
        move.setTargetPosition(targetPosition);
        when(move.cut(boundary))
                .thenReturn(Optional
                        .of(expectedTargetPosition.getCoordinates()));

        //when
        var result = testSubject.maxMove(move);
        //then
        Assertions.assertThat(result.orElseThrow())
                .extracting(Movement::getTargetPosition)
                .extracting(Position::getCoordinates)
                .isEqualTo(expectedTargetPosition.getCoordinates());
    }

    @Test
    public void testMaxMoveShouldReturnMaximalPossibleMoveWithMultipleBoundaries() {
        //given
        var boundaryNear = mock(Obstacle.class);
        var boundaryFurther = mock(Obstacle.class);
        var testSubject = new SpaceShipDeck(Collections
                .singletonList(boundaryNear), 10, 10);

        var sourcePosition = spy(MapPosition
                .of(testSubject, Vector.of(0, 0)));
        var targetPositionMove = MapPosition
                .of(testSubject, Vector.of(3, 3));
        var targetPositionFurther = MapPosition
                .of(testSubject, Vector.of(2, 2));
        var expectedTargetPosition = MapPosition
                .of(testSubject, Vector.of(1, 1));
        when(sourcePosition.distance(targetPositionMove)).thenReturn(3.0);
        when(sourcePosition.distance(targetPositionFurther)).thenReturn(2.0);
        when(sourcePosition.distance(expectedTargetPosition)).thenReturn(1.0);

        var move = spy(new MovementStub());
        move.setSourcePosition(sourcePosition);
        move.setTargetPosition(targetPositionMove);
        when(move.cut(boundaryNear))
                .thenReturn(Optional
                        .of(expectedTargetPosition.getCoordinates()));
        when(move.cut(boundaryFurther)).thenReturn(Optional
                .of(targetPositionFurther.getCoordinates()));

        //when
        var result = testSubject.maxMove(move);
        //then
        Assertions.assertThat(result.orElseThrow())
                .extracting(Movement::getTargetPosition)
                .extracting(Position::getCoordinates)
                .isEqualTo(expectedTargetPosition.getCoordinates());
    }

    @Test
    public void testMaxMoveShouldReturnEmptyIfTargetIsNotOnSpaceShipDeck() {
        //given
        var testSubject = new SpaceShipDeck(Collections.emptyList(), 10, 10);
        var otherSpaceShipDeck = new SpaceShipDeck(Collections.emptyList(),
                10 , 10);
        //when
        var result = testSubject.maxMove(MovementFactory.getInstance()
                .createMovement(MapPosition.of(otherSpaceShipDeck,
                        Vector.of(0, 0)),
                        Command.createMapCommand(Direction.EAST, 2),
                        mock(Droid.class)
                )
        );
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testMaxMoveShouldReturnEmptyIfExceptionIsThrown() {
        //given
        var obstacle = mock(Obstacle.class);
        var testSubject = new SpaceShipDeck(Collections
                .singletonList(obstacle), 10, 10);

        var movement = mock(Movement.class);
        when(movement.getSourcePosition()).thenReturn(new StartPosition());
        when(movement.getTargetPosition())
                .thenReturn(MapPosition.of(testSubject, Vector.of(1, 1)));
        when(movement.cut(obstacle))
                .thenReturn(Optional.of(Vector.of(2, 2)));
        when(movement.changeTargetPosition(any()))
                .thenThrow(IllegalStateException.class);
        //when
        var result = testSubject.maxMove(movement);
        //then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testEnterShouldAddDroidToObstacles() {
        //given
        var droid = mock(Droid.class);
        var testSubject = new SpaceShipDeck(10, 10);
        //when
        var result = testSubject.enter(droid, MapPosition.of(
                testSubject, Vector.of(1, 1)
        ));
        //then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(testSubject.getObstacles()).contains(droid);
    }

    @Test
    public void testEnterShouldNotAddDroidIfDroidIsAlreadyOnSpaceDeck() {
        //given
        var droid = mock(Droid.class);
        var obstacle = mock(InfiniteObstacle.class);
        when(obstacle.cut(any())).thenReturn(Optional.empty());
        var testSubject = new SpaceShipDeck(Collections
                .singletonList(obstacle), 10, 10);
        testSubject.enter(droid, MapPosition.of(testSubject, Vector.of(1, 1)));
        //when
        when(droid.getPosition()).thenReturn(MapPosition.of(testSubject,
                Vector.of(1, 1)));
        var result = testSubject.enter(droid, MapPosition.of(
                testSubject, Vector.of(4, 4)
        ));
        //then
        Assertions.assertThat(result).isFalse();
        Assertions.assertThat(testSubject.getObstacles())
                .containsExactlyInAnyOrder(droid, obstacle);
    }

    @Test
    public void testEnterShouldNotAddDroidIfEntranceIsBlocked() {
        //given
        var droid = mock(Droid.class);
        var obstacle = mock(InfiniteObstacle.class);
        when(obstacle.cut(any())).thenReturn(Optional.of(Vector.of(0, 0)));
        var testSubject = new SpaceShipDeck(Collections
                .singletonList(obstacle), 10, 10);
        //when
        var result = testSubject.enter(droid, MapPosition.of(
                testSubject, Vector.of(5, 5)));
        //then
        Assertions.assertThat(result).isFalse();
        Assertions.assertThat(testSubject.getObstacles())
                .containsExactly(obstacle);
    }

    @Test
    public void testEnterShouldNotAddDroidIfPositionIsOutOfBounds() {
        //given
        var droid = mock(Droid.class);
        var testSubject = new SpaceShipDeck(10, 10);
        //when
        var result = testSubject.enter(droid, MapPosition.of(
                testSubject, Vector.of(12, 12)
        ));
        //then
        Assertions.assertThat(result).isFalse();
        Assertions.assertThat(testSubject.getObstacles())
                .doesNotContain(droid);
    }

    @Test
    public void testLeaveShouldRemoveDroidFromObstacles() {
        //given
        var droid = mock(Droid.class);
        var testSubject = new SpaceShipDeck(10, 10);
        testSubject.enter(droid, MapPosition.of(
                testSubject, Vector.of(1, 1)
        ));
        //when
        var result = testSubject.leave(droid);
        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testAddRectangleObstacleShouldAddObstacle() {
        //given
        var obstacle = new RectangleObstacle(
                Vector.of(1, 2),
                Vector.of(1, 1),
                Vector.of(2, 2),
                Vector.of(2, 1)
        );
        var testSubject = new SpaceShipDeck(10, 10);
        //when
        testSubject.addRectangleObstacle(obstacle);
        //then
        Assertions.assertThat(testSubject)
                .extracting(SpaceShipDeck::getObstacles)
                .asList().contains(obstacle);
    }

    @Test
    @DisplayName("addRectangleObstacle should throw an " +
            "IllegalArgumentException when the obstacle is out the map bounds.")
    public void testAddRectangleObstacleShouldThrowExceptionWhenOutOfBounds() {
        //given
        var obstacle = new RectangleObstacle(
                Vector.of(-2, 2),
                Vector.of(-2, 1),
                Vector.of(-1, 2),
                Vector.of(-1, 1)
        );
        var testSubject = new SpaceShipDeck.SpaceShipDeckBuilder()
                .setHeight(10)
                .setWidth(10)
                .build();
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.addRectangleObstacle(obstacle))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("addRectangleObstacle should throw an " +
            "IllegalArgumentException when the obstacle is partly out the map" +
            " bounds.")
    public void testAddRectangleObstacleShouldThrowExceptionWhenPartlyOutOfBounds() {
        //given
        var obstacle = new RectangleObstacle(
                Vector.of(-1, 2),
                Vector.of(-1, 1),
                Vector.of(2, 2),
                Vector.of(2, 1)
        );
        var testSubject = new SpaceShipDeck.SpaceShipDeckBuilder()
                .setHeight(10)
                .setWidth(10)
                .build();
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.addRectangleObstacle(obstacle))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Data
    private class MovementStub implements Movement {

        private Position sourcePosition;
        private Position targetPosition;

        @Override
        public Movement changeTargetPosition(Position targetPosition) {
            this.targetPosition = targetPosition;
            return this;
        }

        @Override
        public Optional<Vector> cut(Cuttable otherStraight) {
            return Optional.empty();
        }
    }

    @Nested
    public class SpaceShipDeckBuilderUnitTest {

        private SpaceShipDeck.SpaceShipDeckBuilder builder;

        @BeforeEach
        public void setup() {
            this.builder = SpaceShipDeck.SpaceShipDeckBuilder.builder();
        }

        @Test
        public void testBuildShouldCreateMapWithFourInfiniteObstacles() {
            //given
            this.builder.setHeight(2);
            this.builder.setWidth(3);
            var expectedBoundaries = Arrays.asList(
                    new ConcreteStraight(Vector.of(1, 0), Vector.of(0, 0)),
                    new ConcreteStraight(Vector.of(1, 0), Vector.of(0, 1.75)),
                    new ConcreteStraight(Vector.of(0, 1), Vector.of(0, 0)),
                    new ConcreteStraight(Vector.of(0, 1), Vector.of(2.75, 0))
            );
            //when
            var result = this.builder.build();
            //then
            TestUtil.assertSites(result.getObstacles(),
                    expectedBoundaries);
        }

        @Test
        public void testBuildShouldThrowExceptionWhenNotEnoughInformationIsProvided() {
            //given
            //when
            //then
            Assertions.assertThatThrownBy(() -> builder.build())
                    .isInstanceOf(IllegalStateException.class);
        }
    }
}
