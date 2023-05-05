package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.ObjectInfoRetriever;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.Point;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests {

    protected UUID spaceShipDeck1;
    protected UUID spaceShipDeck2;
    protected UUID spaceShipDeck3;

    protected UUID maintenanceDroid1;
    protected UUID maintenanceDroid2;

    protected UUID transportSystem1;
    protected UUID transportSystem2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;

    private ObjectInfoRetriever objectInfoRetriever;


    protected MovementTests(WebApplicationContext appContext) {
        objectInfoRetriever = new ObjectInfoRetriever(appContext);
    }


    protected void createWorld(MaintenanceDroidService service) {
        spaceShipDeck1 = service.addSpaceShipDeck(6,6);
        spaceShipDeck2 = service.addSpaceShipDeck(5,5);
        spaceShipDeck3 = service.addSpaceShipDeck(3,3);

        maintenanceDroid1 = service.addMaintenanceDroid("marvin");
        maintenanceDroid2 = service.addMaintenanceDroid("darwin");

        service.addBarrier(spaceShipDeck1, new Barrier("(0,3)-(2,3)"));
        service.addBarrier(spaceShipDeck1, new Barrier("(3,0)-(3,3)"));
        service.addBarrier(spaceShipDeck2, new Barrier("(0,2)-(4,2)"));

        transportSystem1 = service.addTransportSystem("Internal tube");
        transportSystem2 = service.addTransportSystem("Lift");

        connection1 = service.addConnection(transportSystem1, spaceShipDeck1, new Point("(1,1)"), spaceShipDeck2, new Point("(0,1)"));
        connection2 = service.addConnection(transportSystem1, spaceShipDeck2, new Point("(1,0)"), spaceShipDeck3, new Point("(2,2)"));
        connection3 = service.addConnection(transportSystem2, spaceShipDeck3, new Point("(2,2)"), spaceShipDeck2, new Point("(1,0)"));
    }

    protected void assertPosition(UUID maintenanceDroidId, UUID expectedSpaceShipDeckId, int expectedX, int expectedY) throws Exception {
        Object maintenanceDroid = objectInfoRetriever.getRepository("thkoeln.st.st2praktikum.exercise.MaintenanceDroid").findById(maintenanceDroidId).get();

        // Assert Grid
        Method getSpaceShipDeckMethod = maintenanceDroid.getClass().getMethod("getSpaceShipDeckId");
        assertEquals(expectedSpaceShipDeckId,
                getSpaceShipDeckMethod.invoke(maintenanceDroid));

        // Assert Pos
        Method getPointMethod = maintenanceDroid.getClass().getMethod("getPoint");
        assertEquals(new Point(expectedX, expectedY),
                getPointMethod.invoke(maintenanceDroid));
    }

    protected void executeTasks(MaintenanceDroidService service, UUID maintenanceDroid, Task[] tasks) {
        for (Task task : tasks) {
            service.executeCommand(maintenanceDroid, task);
        }
    }
}
