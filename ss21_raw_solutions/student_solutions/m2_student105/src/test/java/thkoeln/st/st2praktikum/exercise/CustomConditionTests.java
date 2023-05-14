package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomConditionTests {

    protected UUID spaceShipDeck1;
    protected UUID spaceShipDeck2;
    protected UUID spaceShipDeck3;

    protected UUID maintenanceDroid1;
    protected UUID maintenanceDroid2;

    protected UUID connection1;
    protected UUID connection2;
    protected UUID connection3;

    protected MaintenanceDroidService service;

    @BeforeEach
    public void init() {
        service = new MaintenanceDroidService();
        createWorld(service);
    }

    @Test
    public void stopMovementWhenHittingOtherDroid() {


        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck2 + "]"),
                new Order("[ea,5]")
        });

        executeOrders(service, maintenanceDroid2, new Order[]{
                new Order("[en," + spaceShipDeck2 + "]"),
                new Order("[ea,5]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 5, 0);
        assertPosition(service, maintenanceDroid2, spaceShipDeck2, 4, 0);
    }

    @Test
    public void tryingToMoveOutOfBoundsNotPossible() {

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck2 + "]"),
                new Order("[ea,5]"),
                new Order("[no,10]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 5, 5);
    }

    @Test
    public void cannotPlaceConnectionEndpointsOutOfBoundsOfGrid() {
        assertThrows(RuntimeException.class, () -> service.addConnection(spaceShipDeck1, new Coordinate("(-1, 10"), spaceShipDeck2, new Coordinate("4, 80")));
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

        connection1 = service.addConnection(spaceShipDeck1, new Coordinate("(1,1)"), spaceShipDeck2, new Coordinate("(0,1)"));
        connection2 = service.addConnection(spaceShipDeck2, new Coordinate("(1,0)"), spaceShipDeck3, new Coordinate("(2,2)"));
        connection3 = service.addConnection(spaceShipDeck3, new Coordinate("(2,2)"), spaceShipDeck2, new Coordinate("(1,0)"));
    }

    protected void assertPosition(MaintenanceDroidService service, UUID maintenanceDroidId, UUID expectedSpaceShipDeckId, int expectedX, int expectedY) {
        assertEquals(expectedSpaceShipDeckId,
                service.getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId));
        assertEquals(new Coordinate(expectedX, expectedY),
                service.getCoordinate(maintenanceDroidId));
    }

    protected void executeOrders(MaintenanceDroidService service, UUID maintenanceDroid, Order[] Orders) {
        for (Order order : Orders) {
            service.executeCommand(maintenanceDroid, order);
        }
    }
}
