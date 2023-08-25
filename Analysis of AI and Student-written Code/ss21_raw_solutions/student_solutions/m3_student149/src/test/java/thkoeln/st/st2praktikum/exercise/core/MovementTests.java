package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.Command;
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

    protected UUID transportTechnology1;
    protected UUID transportTechnology2;

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

        service.addBarrier(space1, new Barrier("(0,3)-(2,3)"));
        service.addBarrier(space1, new Barrier("(3,0)-(3,3)"));
        service.addBarrier(space2, new Barrier("(0,2)-(4,2)"));

        transportTechnology1 = service.addTransportTechnology("Staircase");
        transportTechnology2 = service.addTransportTechnology("Elevator");

        connection1 = service.addConnection(transportTechnology1, space1, new Vector2D("(1,1)"), space2, new Vector2D("(0,1)"));
        connection2 = service.addConnection(transportTechnology1, space2, new Vector2D("(1,0)"), space3, new Vector2D("(2,2)"));
        connection3 = service.addConnection(transportTechnology2, space3, new Vector2D("(2,2)"), space2, new Vector2D("(1,0)"));
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

    protected void executeCommands(CleaningDeviceService service, UUID cleaningDevice, Command[] commands) {
        for (Command command : commands) {
            service.executeCommand(cleaningDevice, command);
        }
    }
}
