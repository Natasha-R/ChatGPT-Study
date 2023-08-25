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

        assertTrue(service.executeCommand(tidyUpRobot1, new Task("[en," + room1 + "]")));
        assertFalse(service.executeCommand(tidyUpRobot2, new Task("[en," + room1 + "]")));
    }

    @Test
    public void moveToAnotherRoomAndBackTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[ea,1]"),
                new Task("[tr," + room3 + "]")
        });

        assertPosition(service, tidyUpRobot1, room3, 2, 2);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[tr," + room2 + "]")
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 0);
    }

    @Test
    public void moveToAnotherRoomOnWrongPositionTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        service.executeCommand(tidyUpRobot1, new Task("[en," + room1 + "]"));
        assertFalse(service.executeCommand(tidyUpRobot1, new Task("[tr," + room2 + "]")));

        assertPosition(service, tidyUpRobot1, room1, 0, 0);
    }

    @Test
    public void bumpIntoObstacleTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room2 + "]"),
                new Task("[ea,2]"),
                new Task("[no,3]"),
                new Task("[we,1]"),
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 1);
    }

    @Test
    public void moveTwoTidyUpRobotsAtOnceTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[ea,1]"),
                new Task("[no,4]")
        });

        executeTasks(service, tidyUpRobot2, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[ea,2]"),
                new Task("[no,5]")
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 2);
        assertPosition(service, tidyUpRobot2, room1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room3 + "]"),
                new Task("[no,5]"),
                new Task("[ea,5]"),
                new Task("[so,5]"),
                new Task("[we,5]"),
                new Task("[no,1]")
        });

        assertPosition(service, tidyUpRobot1, room3, 0, 1);
    }

    @Test
    public void traverseAllRoomsTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeTasks(service, tidyUpRobot1, new Task[]{
                new Task("[en," + room1 + "]"),
                new Task("[no,1]"),
                new Task("[ea,1]"),
                new Task("[tr," + room2 + "]"),
                new Task("[so,2]"),
                new Task("[ea,1]"),
                new Task("[tr," + room3 + "]"),
                new Task("[we,5]"),
                new Task("[ea,2]"),
                new Task("[tr," + room2 + "]"),
                new Task("[no,4]"),
                new Task("[ea,3]")
        });

        assertPosition(service, tidyUpRobot1, room2, 4, 1);
    }
}
