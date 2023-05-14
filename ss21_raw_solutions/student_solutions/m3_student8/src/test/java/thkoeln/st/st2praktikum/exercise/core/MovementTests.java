package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.Wall;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.Coordinate;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests {

    protected UUID room1;
    protected UUID room2;
    protected UUID room3;

    protected UUID tidyUpRobot1;
    protected UUID tidyUpRobot2;

    protected UUID transportTechnology1;
    protected UUID transportTechnology2;

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

        service.addWall(room1, new Wall("(0,3)-(2,3)"));
        service.addWall(room1, new Wall("(3,0)-(3,3)"));
        service.addWall(room2, new Wall("(0,2)-(4,2)"));

        transportTechnology1 = service.addTransportTechnology("Hallway");
        transportTechnology2 = service.addTransportTechnology("Staircase");

        connection1 = service.addConnection(transportTechnology1, room1, new Coordinate("(1,1)"), room2, new Coordinate("(0,1)"));
        connection2 = service.addConnection(transportTechnology1, room2, new Coordinate("(1,0)"), room3, new Coordinate("(2,2)"));
        connection3 = service.addConnection(transportTechnology2, room3, new Coordinate("(2,2)"), room2, new Coordinate("(1,0)"));
    }

    protected void assertPosition(UUID tidyUpRobotId, UUID expectedRoomId, int expectedX, int expectedY) throws Exception {
        Object tidyUpRobot = objectInfoRetriever.getRepository("thkoeln.st.st2praktikum.exercise.TidyUpRobot").findById(tidyUpRobotId).get();

        // Assert Grid
        Method getRoomMethod = tidyUpRobot.getClass().getMethod("getRoomId");
        assertEquals(expectedRoomId,
                getRoomMethod.invoke(tidyUpRobot));

        // Assert Pos
        Method getCoordinateMethod = tidyUpRobot.getClass().getMethod("getCoordinate");
        assertEquals(new Coordinate(expectedX, expectedY),
                getCoordinateMethod.invoke(tidyUpRobot));
    }

    protected void executeTasks(TidyUpRobotService service, UUID tidyUpRobot, Task[] tasks) {
        for (Task task : tasks) {
            service.executeCommand(tidyUpRobot, task);
        }
    }
}
