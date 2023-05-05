package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.GenericTests;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.application.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import java.lang.reflect.Method;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class MovementTests extends GenericTests {

    protected static final String ROBOT_NAME_1 = "Marvin";
    protected static final String ROBOT_NAME_2 = "Darvin";

    protected UUID spaceShipDeck1;
    protected UUID spaceShipDeck2;
    protected UUID spaceShipDeck3;

    protected UUID maintenanceDroid1;
    protected UUID maintenanceDroid2;

    protected UUID transportCategory1;
    protected UUID transportCategory2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;


    protected MovementTests(WebApplicationContext appContext) {
        super(appContext);
    }


    protected void createWorld(MaintenanceDroidService maintenanceDroidService, SpaceShipDeckService spaceShipDeckService, TransportCategoryService transportCategoryService) {
        spaceShipDeck1 = spaceShipDeckService.addSpaceShipDeck(6,6);
        spaceShipDeck2 = spaceShipDeckService.addSpaceShipDeck(5,5);
        spaceShipDeck3 = spaceShipDeckService.addSpaceShipDeck(3,3);

        spaceShipDeckService.addBarrier(spaceShipDeck1, Barrier.fromString("(0,3)-(2,3)"));
        spaceShipDeckService.addBarrier(spaceShipDeck1, Barrier.fromString("(3,0)-(3,3)"));
        spaceShipDeckService.addBarrier(spaceShipDeck2, Barrier.fromString("(0,2)-(4,2)"));

        maintenanceDroid1 = maintenanceDroidService.addMaintenanceDroid(ROBOT_NAME_1);
        maintenanceDroid2 = maintenanceDroidService.addMaintenanceDroid(ROBOT_NAME_2);

        transportCategory1 = transportCategoryService.addTransportCategory("Internal tube");
        transportCategory2 = transportCategoryService.addTransportCategory("Lift");

        connection1 = transportCategoryService.addConnection(transportCategory1, spaceShipDeck1, Point.fromString("(1,1)"), spaceShipDeck2, Point.fromString("(0,1)"));
        connection2 = transportCategoryService.addConnection(transportCategory1, spaceShipDeck2, Point.fromString("(1,0)"), spaceShipDeck3, Point.fromString("(2,2)"));
        connection3 = transportCategoryService.addConnection(transportCategory2, spaceShipDeck3, Point.fromString("(2,2)"), spaceShipDeck2, Point.fromString("(1,0)"));
    }

    protected void assertPosition(UUID maintenanceDroidId, UUID expectedSpaceShipDeckId, int expectedX, int expectedY) throws Exception {
        Object maintenanceDroid = getMaintenanceDroidRepository().findById(maintenanceDroidId).get();

        // Assert Grid
        Method getSpaceShipDeckMethod = maintenanceDroid.getClass().getMethod("getSpaceShipDeckId");
        assertEquals(expectedSpaceShipDeckId,
                getSpaceShipDeckMethod.invoke(maintenanceDroid));

        // Assert Pos
        Method getPointMethod = maintenanceDroid.getClass().getMethod("getPoint");
        assertEquals(new Point(expectedX, expectedY),
                getPointMethod.invoke(maintenanceDroid));
    }

    protected CrudRepository<Object, UUID> getMaintenanceDroidRepository() throws Exception {
        return oir.getRepository("thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid");
    }

    protected void executeCommands(MaintenanceDroidService service, UUID maintenanceDroid, Command[] commands) {
        for (Command command : commands) {
            service.executeCommand(maintenanceDroid, command);
        }
    }
}
