package thkoeln.st.st2praktikum.exercise.core;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.GenericTests;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.application.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Wall;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

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

    protected UUID transportTechnology1;
    protected UUID transportTechnology2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;


    protected MovementTests(WebApplicationContext appContext) {
        super(appContext);
    }


    protected void createWorld(MaintenanceDroidService maintenanceDroidService, SpaceShipDeckService spaceShipDeckService, TransportTechnologyService transportTechnologyService) {
        spaceShipDeck1 = spaceShipDeckService.addSpaceShipDeck(6,6);
        spaceShipDeck2 = spaceShipDeckService.addSpaceShipDeck(5,5);
        spaceShipDeck3 = spaceShipDeckService.addSpaceShipDeck(3,3);

        spaceShipDeckService.addWall(spaceShipDeck1, Wall.fromString("(0,3)-(2,3)"));
        spaceShipDeckService.addWall(spaceShipDeck1, Wall.fromString("(3,0)-(3,3)"));
        spaceShipDeckService.addWall(spaceShipDeck2, Wall.fromString("(0,2)-(4,2)"));

        maintenanceDroid1 = maintenanceDroidService.addMaintenanceDroid(ROBOT_NAME_1);
        maintenanceDroid2 = maintenanceDroidService.addMaintenanceDroid(ROBOT_NAME_2);

        transportTechnology1 = transportTechnologyService.addTransportTechnology("Internal tube");
        transportTechnology2 = transportTechnologyService.addTransportTechnology("Lift");

        connection1 = transportTechnologyService.addConnection(transportTechnology1, spaceShipDeck1, Vector2D.fromString("(1,1)"), spaceShipDeck2, Vector2D.fromString("(0,1)"));
        connection2 = transportTechnologyService.addConnection(transportTechnology1, spaceShipDeck2, Vector2D.fromString("(1,0)"), spaceShipDeck3, Vector2D.fromString("(2,2)"));
        connection3 = transportTechnologyService.addConnection(transportTechnology2, spaceShipDeck3, Vector2D.fromString("(2,2)"), spaceShipDeck2, Vector2D.fromString("(1,0)"));
    }

    protected void assertPosition(UUID maintenanceDroidId, UUID expectedSpaceShipDeckId, int expectedX, int expectedY) throws Exception {
        Object maintenanceDroid = getMaintenanceDroidRepository().findById(maintenanceDroidId).get();

        // Assert Grid
        Method getSpaceShipDeckMethod = maintenanceDroid.getClass().getMethod("getSpaceShipDeckId");
        assertEquals(expectedSpaceShipDeckId,
                getSpaceShipDeckMethod.invoke(maintenanceDroid));

        // Assert Pos
        Method getVector2DMethod = maintenanceDroid.getClass().getMethod("getVector2D");
        assertEquals(new Vector2D(expectedX, expectedY),
                getVector2DMethod.invoke(maintenanceDroid));
    }

    protected CrudRepository<Object, UUID> getMaintenanceDroidRepository() throws Exception {
        return oir.getRepository("thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid");
    }

    protected void executeOrders(MaintenanceDroidService service, UUID maintenanceDroid, Order[] orders) {
        for (Order order : orders) {
            service.executeCommand(maintenanceDroid, order);
        }
    }
}
