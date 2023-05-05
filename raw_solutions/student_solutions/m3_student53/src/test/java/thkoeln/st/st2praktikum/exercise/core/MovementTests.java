package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Command;
import thkoeln.st.st2praktikum.exercise.Coordinate;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests {

    protected UUID space1;
    protected UUID space2;
    protected UUID space3;

    protected UUID cleaningDevice1;
    protected UUID cleaningDevice2;

    protected UUID transportSystem1;
    protected UUID transportSystem2;

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

        transportSystem1 = service.addTransportSystem("Staircase");
        transportSystem2 = service.addTransportSystem("Elevator");

        connection1 = service.addConnection(transportSystem1, space1, new Coordinate("(1,1)"), space2, new Coordinate("(0,1)"));
        connection2 = service.addConnection(transportSystem1, space2, new Coordinate("(1,0)"), space3, new Coordinate("(2,2)"));
        connection3 = service.addConnection(transportSystem2, space3, new Coordinate("(2,2)"), space2, new Coordinate("(1,0)"));
    }

    protected void assertPosition(UUID cleaningDeviceId, UUID expectedSpaceId, int expectedX, int expectedY) throws Exception {
        Object cleaningDevice = objectInfoRetriever.getRepository("thkoeln.st.st2praktikum.exercise.CleaningDevice").findById(cleaningDeviceId).get();

        // Assert Grid
        Method getSpaceMethod = cleaningDevice.getClass().getMethod("getSpaceId");
        assertEquals(expectedSpaceId,
                getSpaceMethod.invoke(cleaningDevice));

        // Assert Pos
        Method getCoordinateMethod = cleaningDevice.getClass().getMethod("getCoordinate");
        assertEquals(new Coordinate(expectedX, expectedY),
                getCoordinateMethod.invoke(cleaningDevice));
    }

    protected void executeCommands(CleaningDeviceService service, UUID cleaningDevice, Command[] commands) {
        for (Command command : commands) {
            service.executeCommand(cleaningDevice, command);
        }
    }
}
