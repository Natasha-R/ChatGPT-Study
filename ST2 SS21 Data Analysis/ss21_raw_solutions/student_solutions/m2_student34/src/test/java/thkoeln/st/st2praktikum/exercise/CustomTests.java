package thkoeln.st.st2praktikum.exercise;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomTests {

    private MaintenanceDroidService maintenanceDroidService;

    @BeforeEach
    public void setup() {
        this.maintenanceDroidService = new MaintenanceDroidService();
    }

    @Test
    public void testSecondStatement() {
        // Trying to move out of bounds must not be possible
        /*
        Making sure, that the droid doesn't move past obstacles or the map boundaries, happens in the following tests:
        - MaintenanceDroidUnitTest
            - testDroidShouldOnlyMoveToBoundary
            - testDroidShouldReturnFalseWhenMovementIsNotPossible
        Collision detection for the obstacles on a SpaceShipDeck is tested
        in these tests(so that the droid cannot move past the map boundaries or the map's obstacles):
        - SpaceShipDeckUnitTest
            - testMaxMoveShouldReturnMovementWhenNoBoundariesAreGiven
            - testMaxMoveShouldReturnMaximalPossibleMoveWithBoundary
            - testMaxMoveShouldReturnMaximalPossibleMoveWithMultipleBoundaries
            - testMaxMoveShouldReturnEmptyIfExceptionIsThrown
        The actual calculation of collisions is tested in the *CuttingUnitTest classes:
        - PointPointCuttingUnitTest
        - RectangleBoundedStraightCuttingUnitTest
        - RectanglePointCuttingUnitTest
        - StraightCuttingUnitTest
        - StraightPointCuttingUnitTest
        - MapMovementUnitTest
            - testCutOwnDroidShouldReturnEmpty
        The matrix solving algorithm is tested in this class:
        - LinearSystemUnitTest

        If that is not sufficient, here is a test of the whole program
         */
        //given
        var spaceShipDeckId = maintenanceDroidService.addSpaceShipDeck(10, 10);
        var maintenanceDroidId = maintenanceDroidService.addMaintenanceDroid("marcus");
        maintenanceDroidService.executeCommand(maintenanceDroidId, new Task(TaskType.ENTER, spaceShipDeckId));
        //when
        var result = maintenanceDroidService.executeCommand(maintenanceDroidId, new Task(TaskType.EAST, 11));
        //then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(maintenanceDroidService.getCoordinate(maintenanceDroidId))
                .isEqualTo(new Coordinate(9, 0));
    }

    @Test
    public void testFirstStatement() {
        // Hitting another maintenanceDroid during a Move-Command has to interrupt the current movement
        /*
        see testSecondStatement. A MaintenanceDroid itself is a Obstacle and a Point.
        The following test makes sure, that the droid implements the Point correctly:
        - MaintenanceDroidUnitTest
            - testGetSitesShouldReturnCorrectSites

        If that is not sufficient, here is a test of the whole program
         */
        //given
        var spaceShipDeckId = maintenanceDroidService.addSpaceShipDeck(10, 10);
        var maintenanceDroidId1 = maintenanceDroidService.addMaintenanceDroid("marcus");
        var maintenanceDroidId2 = maintenanceDroidService.addMaintenanceDroid("eva");
        maintenanceDroidService.executeCommand(maintenanceDroidId1, new Task(TaskType.ENTER, spaceShipDeckId));
        maintenanceDroidService.executeCommand(maintenanceDroidId1, new Task(TaskType.EAST, 2));
        //when
        maintenanceDroidService.executeCommand(maintenanceDroidId2, new Task(TaskType.ENTER, spaceShipDeckId));
        var result = maintenanceDroidService.executeCommand(maintenanceDroidId2, new Task(TaskType.EAST, 2));
        //then
        Assertions.assertThat(result).isTrue();
        Assertions.assertThat(maintenanceDroidService.getCoordinate(maintenanceDroidId2))
                .isEqualTo(new Coordinate(1, 0));
    }

    @Test
    public void testForthStatement() {
        // Traversing a connection must not be possible in case the destination is occupied
        /*
        In case the destination is occupied, the Map will try to alter the EnterMovement's target position.
        Because that's not possible for a EnterMovement this leads to an Exception.
        The EnterMovement's behaviour is tested here:
        - EnterMovementUnitTest
            - testChangeTargetPositionShouldThrowException
        If the target position change throws an exception the Map has to return an empty Optional.
        This is tested in the following test case:
        - SpaceShipDeckUnitTest:
            - testMaxMoveShouldReturnEmptyIfExceptionIsThrown

        If that is not sufficient, here is a test of the whole program
         */
        //given
        var spaceShipDeckId1 = maintenanceDroidService.addSpaceShipDeck(10, 10);
        var spaceShipDeckId2 = maintenanceDroidService.addSpaceShipDeck(10, 10);
        var connectionId = maintenanceDroidService.addConnection(
                spaceShipDeckId1,
                new Coordinate(0, 0),
                spaceShipDeckId2,
                new Coordinate(2, 2)
        );
        var maintenanceDroidId1 = maintenanceDroidService.addMaintenanceDroid("marcus");
        maintenanceDroidService.executeCommand(maintenanceDroidId1, new Task(TaskType.ENTER, spaceShipDeckId2));
        maintenanceDroidService.executeCommand(maintenanceDroidId1, new Task(TaskType.EAST, 2));
        var maintenanceDroidId2 = maintenanceDroidService.addMaintenanceDroid("eva");
        maintenanceDroidService.executeCommand(maintenanceDroidId2, new Task(TaskType.ENTER, spaceShipDeckId1));
        //when
        var result = maintenanceDroidService.executeCommand(maintenanceDroidId2,
                new Task(TaskType.TRANSPORT, spaceShipDeckId1));
        //then
        Assertions.assertThat(result).isFalse();
        Assertions.assertThat(maintenanceDroidService.getCoordinate(maintenanceDroidId2))
                .isEqualTo(new Coordinate(0, 0));
    }

    @Test
    public void testSixthStatement() {
        // Placing a obstacle out of bounds must not be possible
        /*
        The SpaceShipDeck behaviour for adding an obstacle is tested in the following test cases:
        - SpaceShipDeckUnitTest
            - testAddRectangleObstacleShouldAddObstacle
            - testAddRectangleObstacleShouldThrowExceptionWhenOutOfBounds
            - testAddRectangleObstacleShouldThrowExceptionWhenPartlyOutOfBounds
        The cutting functionality for a Rectangle and Point is again tested in this class:
        - RectanglePointCuttingUnitTest

        If that is not sufficient, here is a test of the whole program
         */

        //given
        var spaceShipDeckId = maintenanceDroidService.addSpaceShipDeck(10, 10);
        //when
        //then
        Assertions.assertThatThrownBy(() ->
                maintenanceDroidService.addObstacle(spaceShipDeckId, new Obstacle(
                        new Coordinate(12, 0),
                        new Coordinate(12, 10))
                )
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
