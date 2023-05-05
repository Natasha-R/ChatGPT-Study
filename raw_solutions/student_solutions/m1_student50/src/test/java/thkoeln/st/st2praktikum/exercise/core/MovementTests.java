package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.TidyUpRobotService;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class MovementTests {

    protected UUID room1;
    protected UUID room2;
    protected UUID room3;

    protected UUID tidyUpRobot1;
    protected UUID tidyUpRobot2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;

    protected void createWorld(TidyUpRobotService service) {
        room1=service.addRoom(6,6);
        room2=service.addRoom(5,5);
        room3=service.addRoom(3,3);

        tidyUpRobot1=service.addTidyUpRobot("marvin");
        tidyUpRobot2=service.addTidyUpRobot("darwin");

        service.addWall(room1,"(0,3)-(2,3)");
        service.addWall(room1,"(3,0)-(3,3)");
        service.addWall(room2,"(0,2)-(4,2)");

        connection1=service.addConnection(room1,"(1,1)", room2,"(0,1)");
        connection2=service.addConnection(room2,"(1,0)", room3,"(2,2)");
        connection3=service.addConnection(room3,"(2,2)", room2,"(1,0)");
    }

    protected void assertPosition(TidyUpRobotService service, UUID tidyUpRobotId, UUID expectedRoomId, int expectedX, int expectedY) {
        assertEquals(expectedRoomId,
                service.getTidyUpRobotRoomId(tidyUpRobotId));
        assertEquals("(" + expectedX + "," + expectedY + ")",
                service.getCoordinates(tidyUpRobotId));
    }

    protected void executeCommands(TidyUpRobotService service, UUID tidyUpRobot, String[] commands) {
        for (String command : commands) {
            service.executeCommand(tidyUpRobot, command);
        }
    }
}
