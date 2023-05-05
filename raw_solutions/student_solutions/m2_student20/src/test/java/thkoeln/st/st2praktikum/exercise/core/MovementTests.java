package thkoeln.st.st2praktikum.exercise.core;

import thkoeln.st.st2praktikum.exercise.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class MovementTests {

    protected UUID spaceShipDeck1;
    protected UUID spaceShipDeck2;
    protected UUID spaceShipDeck3;

    protected UUID maintenanceDroid1;
    protected UUID maintenanceDroid2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;


    protected void createWorld(MaintenanceDroidService service) {
        spaceShipDeck1 = service.addSpaceShipDeck(6,6);
        spaceShipDeck2 = service.addSpaceShipDeck(5,5);
        spaceShipDeck3 = service.addSpaceShipDeck(3,3);

        maintenanceDroid1 = service.addMaintenanceDroid("marvin");
        maintenanceDroid2 = service.addMaintenanceDroid("darwin");

        service.addObstacle(spaceShipDeck1, new Obstacle("(0,3)-(2,3)"));
        service.addObstacle(spaceShipDeck1, new Obstacle("(3,0)-(3,3)"));
        service.addObstacle(spaceShipDeck2, new Obstacle("(0,2)-(4,2)"));

        connection1 = service.addConnection(spaceShipDeck1, new Vector2D("(1,1)"), spaceShipDeck2, new Vector2D("(0,1)"));
        connection2 = service.addConnection(spaceShipDeck2, new Vector2D("(1,0)"), spaceShipDeck3, new Vector2D("(2,2)"));
        connection3 = service.addConnection(spaceShipDeck3, new Vector2D("(2,2)"), spaceShipDeck2, new Vector2D("(1,0)"));
    }

    protected void assertPosition(MaintenanceDroidService service, UUID maintenanceDroidId, UUID expectedSpaceShipDeckId, int expectedX, int expectedY) {
        assertEquals(expectedSpaceShipDeckId,
                service.getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId));
        assertEquals(new Vector2D(expectedX, expectedY),
                service.getVector2D(maintenanceDroidId));
    }

    protected void executeOrders(MaintenanceDroidService service, UUID maintenanceDroid, Order[] Orders) {
        for (Order order : Orders) {
            service.executeCommand(maintenanceDroid, order);
        }
    }
}
