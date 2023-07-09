package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.Point;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests {

    protected UUID room1;
    protected UUID room2;
    protected UUID room3;

    protected UUID tidyUpRobot1;
    protected UUID tidyUpRobot2;

    protected UUID transportCategory1;
    protected UUID transportCategory2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;

    private ObjectInfoRetriever objectInfoRetriever;


    protected MovementTests(WebApplicationContext appContext) {
        objectInfoRetriever = new ObjectInfoRetriever(appContext);
    }


    protected void createWorld(TidyUpRobotService service) {
        room1 = service.addRoom(6,6);
        room2 = service.addRoom(5,5);
        room3 = service.addRoom(3,3);

        tidyUpRobot1 = service.addTidyUpRobot("marvin");
        tidyUpRobot2 = service.addTidyUpRobot("darwin");

        service.addObstacle(room1, new Obstacle("(0,3)-(2,3)"));
        service.addObstacle(room1, new Obstacle("(3,0)-(3,3)"));
        service.addObstacle(room2, new Obstacle("(0,2)-(4,2)"));

        transportCategory1 = service.addTransportCategory("Hallway");
        transportCategory2 = service.addTransportCategory("Staircase");

        connection1 = service.addConnection(transportCategory1, room1, new Point("(1,1)"), room2, new Point("(0,1)"));
        connection2 = service.addConnection(transportCategory1, room2, new Point("(1,0)"), room3, new Point("(2,2)"));
        connection3 = service.addConnection(transportCategory2, room3, new Point("(2,2)"), room2, new Point("(1,0)"));
    }

    protected void assertPosition(UUID tidyUpRobotId, UUID expectedRoomId, int expectedX, int expectedY) throws Exception {
        Object tidyUpRobot = objectInfoRetriever.getRepository("thkoeln.st.st2praktikum.exercise.TidyUpRobot").findById(tidyUpRobotId).get();

        // Assert Grid
        Method getRoomMethod = tidyUpRobot.getClass().getMethod("getRoomId");
        assertEquals(expectedRoomId,
                getRoomMethod.invoke(tidyUpRobot));

        // Assert Pos
        Method getPointMethod = tidyUpRobot.getClass().getMethod("getPoint");
        assertEquals(new Point(expectedX, expectedY),
                getPointMethod.invoke(tidyUpRobot));
    }

    protected void executeTasks(TidyUpRobotService service, UUID tidyUpRobot, Task[] tasks) {
        for (Task task : tasks) {
            service.executeCommand(tidyUpRobot, task);
        }
    }
}
