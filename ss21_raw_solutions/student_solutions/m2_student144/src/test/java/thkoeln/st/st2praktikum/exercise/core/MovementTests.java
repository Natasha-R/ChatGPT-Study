package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.Wall;
import thkoeln.st.st2praktikum.exercise.Command;
import thkoeln.st.st2praktikum.exercise.Point;

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
        room1 = service.addRoom(6,6);
        room2 = service.addRoom(5,5);
        room3 = service.addRoom(3,3);

        tidyUpRobot1 = service.addTidyUpRobot("marvin");
        tidyUpRobot2 = service.addTidyUpRobot("darwin");

        service.addWall(room1, new Wall("(0,3)-(2,3)"));
        service.addWall(room1, new Wall("(3,0)-(3,3)"));
        service.addWall(room2, new Wall("(0,2)-(4,2)"));

        connection1 = service.addConnection(room1, new Point("(1,1)"), room2, new Point("(0,1)"));
        connection2 = service.addConnection(room2, new Point("(1,0)"), room3, new Point("(2,2)"));
        connection3 = service.addConnection(room3, new Point("(2,2)"), room2, new Point("(1,0)"));
    }

    protected void assertPosition(TidyUpRobotService service, UUID tidyUpRobotId, UUID expectedRoomId, int expectedX, int expectedY) {
        assertEquals(expectedRoomId,
                service.getTidyUpRobotRoomId(tidyUpRobotId));
        assertEquals(new Point(expectedX, expectedY),
                service.getPoint(tidyUpRobotId));
    }

    protected void executeCommands(TidyUpRobotService service, UUID tidyUpRobot, Command[] Commands) {
        for (Command command : Commands) {
            service.executeCommand(tidyUpRobot, command);
        }
    }
}
