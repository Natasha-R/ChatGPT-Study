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

        assertTrue(service.executeCommand(tidyUpRobot1, new Command("[en," + room1 + "]")));
        assertFalse(service.executeCommand(tidyUpRobot2, new Command("[en," + room1 + "]")));
    }

    @Test
    public void moveToAnotherRoomAndBackTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room2 + "]"),
                new Command("[ea,1]"),
                new Command("[tr," + room3 + "]")
        });

        assertPosition(service, tidyUpRobot1, room3, 2, 2);

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[tr," + room2 + "]")
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 0);
    }

    @Test
    public void moveToAnotherRoomOnWrongPositionTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        service.executeCommand(tidyUpRobot1, new Command("[en," + room1 + "]"));
        assertFalse(service.executeCommand(tidyUpRobot1, new Command("[tr," + room2 + "]")));

        assertPosition(service, tidyUpRobot1, room1, 0, 0);
    }

    @Test
    public void bumpIntoObstacleTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room2 + "]"),
                new Command("[ea,2]"),
                new Command("[no,3]"),
                new Command("[we,1]"),
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 1);
    }

    @Test
    public void moveTwoTidyUpRobotsAtOnceTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,4]")
        });

        executeCommands(service, tidyUpRobot2, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[ea,2]"),
                new Command("[no,5]")
        });

        assertPosition(service, tidyUpRobot1, room1, 1, 2);
        assertPosition(service, tidyUpRobot2, room1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room3 + "]"),
                new Command("[no,5]"),
                new Command("[ea,5]"),
                new Command("[so,5]"),
                new Command("[we,5]"),
                new Command("[no,1]")
        });

        assertPosition(service, tidyUpRobot1, room3, 0, 1);
    }

    @Test
    public void traverseAllRoomsTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + room2 + "]"),
                new Command("[so,2]"),
                new Command("[ea,1]"),
                new Command("[tr," + room3 + "]"),
                new Command("[we,5]"),
                new Command("[ea,2]"),
                new Command("[tr," + room2 + "]"),
                new Command("[no,4]"),
                new Command("[ea,3]")
        });

        assertPosition(service, tidyUpRobot1, room2, 4, 1);
    }
}
