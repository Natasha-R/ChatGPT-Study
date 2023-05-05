package thkoeln.st.st2praktikum.exercise;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class E2MoveOutOfBoundsTest
{

    UUID spaceShipDeck1;
    MaintenanceDroidService service;
    UUID maintenanceDroid1;

    @Before
    public void createWorld() {
        this.service = new MaintenanceDroidService();
        this.spaceShipDeck1 = service.addSpaceShipDeck(10,10);
        this.maintenanceDroid1 = service.addMaintenanceDroid("droid1");
    }

    @Test
    public void TestPositive() {
        service.executeCommand(maintenanceDroid1, new Order("[en," + this.spaceShipDeck1 + "]"));
        service.executeCommand(maintenanceDroid1, new Order("[we,1]"));
        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    public void TestNegative() {
        service.executeCommand(maintenanceDroid1, new Order("[en," + this.spaceShipDeck1 + "]"));
        service.executeCommand(maintenanceDroid1, new Order("[no,11]"));
        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 9);
    }

    protected void assertPosition(MaintenanceDroidService service, UUID maintenanceDroidId, UUID expectedSpaceShipDeckId, int expectedX, int expectedY) {
        assertEquals(expectedSpaceShipDeckId,
                service.getMaintenanceDroidSpaceShipDeckId(maintenanceDroidId));
        assertEquals(new Vector2D(expectedX, expectedY),
                service.getVector2D(maintenanceDroidId));
    }
}
