package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.Wall;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.Vector2D;

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

        service.addWall(space1, new Wall("(0,3)-(2,3)"));
        service.addWall(space1, new Wall("(3,0)-(3,3)"));
        service.addWall(space2, new Wall("(0,2)-(4,2)"));

        transportCategory1 = service.addTransportCategory("Staircase");
        transportCategory2 = service.addTransportCategory("Elevator");

        connection1 = service.addConnection(transportCategory1, space1, new Vector2D("(1,1)"), space2, new Vector2D("(0,1)"));
        connection2 = service.addConnection(transportCategory1, space2, new Vector2D("(1,0)"), space3, new Vector2D("(2,2)"));
        connection3 = service.addConnection(transportCategory2, space3, new Vector2D("(2,2)"), space2, new Vector2D("(1,0)"));
    }

    protected void assertPosition(UUID cleaningDeviceId, UUID expectedSpaceId, int expectedX, int expectedY) throws Exception {
        Object cleaningDevice = objectInfoRetriever.getRepository("thkoeln.st.st2praktikum.exercise.CleaningDevice").findById(cleaningDeviceId).get();

        // Assert Grid
        Method getSpaceMethod = cleaningDevice.getClass().getMethod("getSpaceId");
        assertEquals(expectedSpaceId,
                getSpaceMethod.invoke(cleaningDevice));

        // Assert Pos
        Method getVector2DMethod = cleaningDevice.getClass().getMethod("getVector2D");
        assertEquals(new Vector2D(expectedX, expectedY),
                getVector2DMethod.invoke(cleaningDevice));
    }

    protected void executeTasks(CleaningDeviceService service, UUID cleaningDevice, Task[] tasks) {
        for (Task task : tasks) {
            service.executeCommand(cleaningDevice, task);
        }
    }
}
