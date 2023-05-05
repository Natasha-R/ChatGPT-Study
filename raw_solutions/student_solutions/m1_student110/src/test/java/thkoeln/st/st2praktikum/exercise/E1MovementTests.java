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

        assertTrue(service.executeCommand(tidyUpRobot1, "[en," + room1 + "]"));
        assertFalse(service.executeCommand(tidyUpRobot2, "[en," + room1 + "]"));
    }


    @Test
    public void moveToAnotherRoomAndBackTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new String[]{
                "[en," + room2 + "]",
                "[ea,1]",
                "[tr," + room3 + "]"
        });

        assertPosition(service, tidyUpRobot1, room3, 2, 2);

        executeCommands(service, tidyUpRobot1, new String[]{
                "[tr," + room2 + "]"
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 0);
    }
    
    @Test
    public void moveToAnotherRoomOnWrongPositionTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        service.executeCommand(tidyUpRobot1, "[en," + room1 + "]");
        assertFalse(service.executeCommand(tidyUpRobot1, "[tr," + room2 + "]"));

        assertPosition(service, tidyUpRobot1, room1, 0, 0);
    }

    @Test
    public void bumpIntoBarrierTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new String[]{
                "[en," + room2 + "]",
                "[ea,2]",
                "[no,3]",
                "[we,1]"
        });

        assertPosition(service, tidyUpRobot1, room2, 1, 1);
    }

    @Test
    public void moveTwoTidyUpRobotsAtOnceTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new String[]{
                "[en," + room1 + "]",
                "[ea,1]",
                "[no,4]"
        });

        executeCommands(service, tidyUpRobot2, new String[]{
                "[en," + room1 + "]",
                "[ea,2]",
                "[no,5]"
        });


        assertPosition(service, tidyUpRobot1, room1, 1, 2);
        assertPosition(service, tidyUpRobot2, room1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new String[]{
                "[en," + room3 + "]",
                "[no,5]",
                "[ea,5]",
                "[so,5]",
                "[we,5]",
                "[no,1]"
        });

        assertPosition(service, tidyUpRobot1, room3, 0, 1);
    }

    @Test
    public void traverseAllRoomsTest() {
        TidyUpRobotService service = new TidyUpRobotService();
        createWorld(service);

        executeCommands(service, tidyUpRobot1, new String[]{
                "[en," + room1 + "]",
                "[no,1]",
                "[ea,1]",
                "[tr," + room2 + "]",
                "[so,2]",
                "[ea,1]",
                "[tr," + room3 + "]",
                "[we,5]",
                "[ea,2]",
                "[tr," + room2 + "]",
                "[no,4]",
                "[ea,3]"
        });

        assertPosition(service, tidyUpRobot1, room2, 4, 1);
    }
}
