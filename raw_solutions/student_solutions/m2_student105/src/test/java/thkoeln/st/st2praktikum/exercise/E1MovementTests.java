package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class E1MovementTests extends MovementTests {

    @Test
    public void maintenanceDroidSpawnOnSamePositionTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        assertTrue(service.executeCommand(maintenanceDroid1, new Order("[en," + spaceShipDeck1 + "]")));
        assertFalse(service.executeCommand(maintenanceDroid2, new Order("[en," + spaceShipDeck1 + "]")));
    }

    @Test
    public void moveToAnotherSpaceShipDeckAndBackTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + spaceShipDeck3 + "]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 2, 2);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[tr," + spaceShipDeck2 + "]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceShipDeckOnWrongPositionTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        service.executeCommand(maintenanceDroid1, new Order("[en," + spaceShipDeck1 + "]"));
        assertFalse(service.executeCommand(maintenanceDroid1, new Order("[tr," + spaceShipDeck2 + "]")));

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    public void bumpIntoBarrierTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck2 + "]"),
                new Order("[ea,2]"),
                new Order("[no,3]"),
                new Order("[we,1]"),
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 1);
    }

    @Test
    public void moveTwoMaintenanceDroidsAtOnceTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck1 + "]"),
                new Order("[ea,1]"),
                new Order("[no,4]")
        });

        executeOrders(service, maintenanceDroid2, new Order[]{
                new Order("[en," + spaceShipDeck1 + "]"),
                new Order("[ea,2]"),
                new Order("[no,5]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 1, 2);
        assertPosition(service, maintenanceDroid2, spaceShipDeck1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck3 + "]"),
                new Order("[no,5]"),
                new Order("[ea,5]"),
                new Order("[so,5]"),
                new Order("[we,5]"),
                new Order("[no,1]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 1);
    }

    @Test
    public void traverseAllSpaceShipDecksTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeOrders(service, maintenanceDroid1, new Order[]{
                new Order("[en," + spaceShipDeck1 + "]"),
                new Order("[no,1]"),
                new Order("[ea,1]"),
                new Order("[tr," + spaceShipDeck2 + "]"),
                new Order("[so,2]"),
                new Order("[ea,1]"),
                new Order("[tr," + spaceShipDeck3 + "]"),
                new Order("[we,5]"),
                new Order("[ea,2]"),
                new Order("[tr," + spaceShipDeck2 + "]"),
                new Order("[no,4]"),
                new Order("[ea,3]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 4, 1);
    }
}
