package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class E1MovementTests extends MovementTests {

    @Test
    public void tidyUpRobotSpawnOnSamePositionTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        assertTrue(service.executeCommand(tidyUpRobot1, new Order("[en," + room1 + "]")));
        assertFalse(service.executeCommand(tidyUpRobot2, new Order("[en," + room1 + "]")));
    }

    @Test
    public void moveToAnotherRoomAndBackTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + room3 + "]")
        });

        assertPosition(service, tidyUpRobot1, room3, 2, 2);

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[tr," + room2 + "]")
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 0);
    }

    @Test
    public void moveToAnotherRoomOnWrongPositionTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        service.executeCommand(tidyUpRobot1, new Order("[en," + room1 + "]"));
        assertFalse(service.executeCommand(tidyUpRobot1, new Order("[tr," + room2 + "]")));

        assertPosition(service, tidyUpRobot1, room1, 0, 0);
    }

    @Test
    public void bumpIntoObstacleTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,2]"),
                new Order("[no,3]"),
                new Order("[we,1]"),
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 1);
    }

    @Test
    public void moveTwoTidyUpRobotsAtOnceTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,1]"),
                new Order("[no,4]")
        });

        executeOrders(service, tidyUpRobot2, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,2]"),
                new Order("[no,5]")
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 2);
        assertPosition(service, tidyUpRobot2, room1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room3 + "]"),
                new Order("[no,5]"),
                new Order("[ea,5]"),
                new Order("[so,5]"),
                new Order("[we,5]"),
                new Order("[no,1]")
        });

        assertPosition(service, tidyUpRobot1, room3, 0, 1);
    }

    @Test
    public void traverseAllRoomsTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[no,1]"),
                new Order("[ea,1]"),
                new Order("[tr," + room2 + "]"),
                new Order("[so,2]"),
                new Order("[ea,1]"),
                new Order("[tr," + room3 + "]"),
                new Order("[we,5]"),
                new Order("[ea,2]"),
                new Order("[tr," + room2 + "]"),
                new Order("[no,4]"),
                new Order("[ea,3]")
        });

        assertPosition(service, tidyUpRobot1, room2, 4, 1);
    }
}