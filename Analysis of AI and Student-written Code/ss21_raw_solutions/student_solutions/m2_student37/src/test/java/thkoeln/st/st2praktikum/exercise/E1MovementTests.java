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

        assertTrue(service.executeCommand(maintenanceDroid1, new Task("[en," + spaceShipDeck1 + "]")));
        assertFalse(service.executeCommand(maintenanceDroid2, new Task("[en," + spaceShipDeck1 + "]")));
    }

    @Test
    public void moveToAnotherSpaceShipDeckAndBackTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck2 + "]"),
                new Task("[ea,1]"),
                new Task("[tr," + spaceShipDeck3 + "]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 2, 2);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[tr," + spaceShipDeck2 + "]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceShipDeckOnWrongPositionTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        service.executeCommand(maintenanceDroid1, new Task("[en," + spaceShipDeck1 + "]"));
        assertFalse(service.executeCommand(maintenanceDroid1, new Task("[tr," + spaceShipDeck2 + "]")));

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    public void bumpIntoObstacleTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck2 + "]"),
                new Task("[ea,2]"),
                new Task("[no,3]"),
                new Task("[we,1]"),
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 1);
    }

    @Test
    public void moveTwoMaintenanceDroidsAtOnceTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck1 + "]"),
                new Task("[ea,1]"),
                new Task("[no,4]")
        });

        executeTasks(service, maintenanceDroid2, new Task[]{
                new Task("[en," + spaceShipDeck1 + "]"),
                new Task("[ea,2]"),
                new Task("[no,5]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 1, 2);
        assertPosition(service, maintenanceDroid2, spaceShipDeck1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck3 + "]"),
                new Task("[no,5]"),
                new Task("[ea,5]"),
                new Task("[so,5]"),
                new Task("[we,5]"),
                new Task("[no,1]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 1);
    }

    @Test
    public void traverseAllSpaceShipDecksTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck1 + "]"),
                new Task("[no,1]"),
                new Task("[ea,1]"),
                new Task("[tr," + spaceShipDeck2 + "]"),
                new Task("[so,2]"),
                new Task("[ea,1]"),
                new Task("[tr," + spaceShipDeck3 + "]"),
                new Task("[we,5]"),
                new Task("[ea,2]"),
                new Task("[tr," + spaceShipDeck2 + "]"),
                new Task("[no,4]"),
                new Task("[ea,3]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 4, 1);
    }
}
