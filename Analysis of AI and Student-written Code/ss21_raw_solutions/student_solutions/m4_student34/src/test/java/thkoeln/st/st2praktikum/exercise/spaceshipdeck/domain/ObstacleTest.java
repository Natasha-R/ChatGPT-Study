package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

public class ObstacleTest {

    @Test
    public void testCutsNorthFarWestShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.NORTH, new Coordinate(0, 1));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsNorthAtStartShouldCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.NORTH, new Coordinate(1, 1));
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testCutsNorthInMiddleShouldCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.NORTH, new Coordinate(2, 1));
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testCutsNorthAtEndShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.NORTH, new Coordinate(4, 1));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsNorthNorthShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.NORTH, new Coordinate(2, 2));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsNorthFarNorthShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.NORTH, new Coordinate(2, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsNorthFarSouthShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.NORTH, new Coordinate(2, 0));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsNorthShouldNotCutVertical() {
        //given
        var testSubject = new Obstacle(new Coordinate(5, 3), new Coordinate(5, 5));
        //when
        var result = testSubject.cuts(TaskType.NORTH, new Coordinate(5, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsEastFarWestShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.EAST, new Coordinate(1, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsEastWestShouldCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.EAST, new Coordinate(2, 3));
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testCutsEastEastShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.EAST, new Coordinate(3, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsEastFarEastShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.EAST, new Coordinate(4, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsEastAtEndShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.EAST, new Coordinate(2, 4));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsEastAtStartShouldCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.EAST, new Coordinate(2, 2));
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testCutsEastFarSouthShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.EAST, new Coordinate(2, 1));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsEastHorizontalShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(2, 5), new Coordinate(4, 5));
        //when
        var result = testSubject.cuts(TaskType.EAST, new Coordinate(2, 5));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsSouthFarNorthShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.SOUTH, new Coordinate(2, 4));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsSouthNorthShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.SOUTH, new Coordinate(2, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsSouthMiddleShouldCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.SOUTH, new Coordinate(2, 2));
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testCutsSouthFarSouthShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.SOUTH, new Coordinate(2, 1));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsSouthAtStartShouldCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.SOUTH, new Coordinate(1, 2));
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testCutsSouthFarWestShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.SOUTH, new Coordinate(0, 2));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsSouthAtEndShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(1, 2), new Coordinate(4, 2));
        //when
        var result = testSubject.cuts(TaskType.SOUTH, new Coordinate(4, 2));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsSouthVerticalShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(5, 3), new Coordinate(5, 5));
        //when
        var result = testSubject.cuts(TaskType.SOUTH, new Coordinate(5, 5));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsWestFarEastShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.WEST, new Coordinate(5, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsWestEastShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.WEST, new Coordinate(4, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsWestInMiddleShouldCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.WEST, new Coordinate(3, 3));
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testCutsWestFarWestShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.WEST, new Coordinate(2, 3));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsAtEndShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.WEST, new Coordinate(3, 4));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsWestAtStartShouldCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.WEST, new Coordinate(3, 2));
        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void testCutsWestFarSouthShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(3, 2), new Coordinate(3, 4));
        //when
        var result = testSubject.cuts(TaskType.WEST, new Coordinate(3, 1));
        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void testCutsWestHorizontalShouldNotCut() {
        //given
        var testSubject = new Obstacle(new Coordinate(2, 5), new Coordinate(4, 5));
        //when
        var result = testSubject.cuts(TaskType.WEST, new Coordinate(3, 5));
        //then
        Assertions.assertFalse(result);
    }
}
