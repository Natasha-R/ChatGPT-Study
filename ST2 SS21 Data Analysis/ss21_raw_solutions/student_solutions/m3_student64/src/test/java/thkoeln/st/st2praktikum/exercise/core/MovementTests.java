package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.Point;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests {

    protected UUID space1;
    protected UUID space2;
    protected UUID space3;

    protected UUID cleaningDevice1;
    protected UUID cleaningDevice2;

    protected UUID transportCategory1;
    protected UUID transportCategory2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;

    private ObjectInfoRetriever objectInfoRetriever;


    protected MovementTests(WebApplicationContext appContext) {
        objectInfoRetriever = new ObjectInfoRetriever(appContext);
    }


    protected void createWorld(CleaningDeviceService service) {
        space1 = service.addSpace(6,6);
        space2 = service.addSpace(5,5);
        space3 = service.addSpace(3,3);

        cleaningDevice1 = service.addCleaningDevice("marvin");
        cleaningDevice2 = service.addCleaningDevice("darwin");

        service.addObstacle(space1, new Obstacle("(0,3)-(2,3)"));
        service.addObstacle(space1, new Obstacle("(3,0)-(3,3)"));
        service.addObstacle(space2, new Obstacle("(0,2)-(4,2)"));

        transportCategory1 = service.addTransportCategory("Staircase");
        transportCategory2 = service.addTransportCategory("Elevator");

        connection1 = service.addConnection(transportCategory1, space1, new Point("(1,1)"), space2, new Point("(0,1)"));
        connection2 = service.addConnection(transportCategory1, space2, new Point("(1,0)"), space3, new Point("(2,2)"));
        connection3 = service.addConnection(transportCategory2, space3, new Point("(2,2)"), space2, new Point("(1,0)"));
    }

    protected void assertPosition(UUID cleaningDeviceId, UUID expectedSpaceId, int expectedX, int expectedY) throws Exception {
        Object cleaningDevice = objectInfoRetriever.getRepository("thkoeln.st.st2praktikum.exercise.CleaningDevice").findById(cleaningDeviceId).get();

        // Assert Grid
        Method getSpaceMethod = cleaningDevice.getClass().getMethod("getSpaceId");
        assertEquals(expectedSpaceId,
                getSpaceMethod.invoke(cleaningDevice));

        // Assert Pos
        Method getPointMethod = cleaningDevice.getClass().getMethod("getPoint");
        assertEquals(new Point(expectedX, expectedY),
                getPointMethod.invoke(cleaningDevice));
    }

    protected void executeTasks(CleaningDeviceService service, UUID cleaningDevice, Task[] tasks) {
        for (Task task : tasks) {
            service.executeCommand(cleaningDevice, task);
        }
    }
}
