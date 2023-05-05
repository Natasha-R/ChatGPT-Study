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

        assertTrue(service.executeCommand(maintenanceDroid1, new Command("[en," + spaceShipDeck1 + "]")));
        assertFalse(service.executeCommand(maintenanceDroid2, new Command("[en," + spaceShipDeck1 + "]")));
    }

    @Test
    public void moveToAnotherSpaceShipDeckAndBackTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new Command[]{
                new Command("[en," + spaceShipDeck2 + "]"),
                new Command("[ea,1]"),
                new Command("[tr," + spaceShipDeck3 + "]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 2, 2);

        executeCommands(service, maintenanceDroid1, new Command[]{
                new Command("[tr," + spaceShipDeck2 + "]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceShipDeckOnWrongPositionTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        service.executeCommand(maintenanceDroid1, new Command("[en," + spaceShipDeck1 + "]"));
        assertFalse(service.executeCommand(maintenanceDroid1, new Command("[tr," + spaceShipDeck2 + "]")));

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    public void bumpIntoBarrierTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new Command[]{
                new Command("[en," + spaceShipDeck2 + "]"),
                new Command("[ea,2]"),
                new Command("[no,3]"),
                new Command("[we,1]"),
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 1);
    }

    @Test
    public void moveTwoMaintenanceDroidsAtOnceTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new Command[]{
                new Command("[en," + spaceShipDeck1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,4]")
        });

        executeCommands(service, maintenanceDroid2, new Command[]{
                new Command("[en," + spaceShipDeck1 + "]"),
                new Command("[ea,2]"),
                new Command("[no,5]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 1, 2);
        assertPosition(service, maintenanceDroid2, spaceShipDeck1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new Command[]{
                new Command("[en," + spaceShipDeck3 + "]"),
                new Command("[no,5]"),
                new Command("[ea,5]"),
                new Command("[so,5]"),
                new Command("[we,5]"),
                new Command("[no,1]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 1);
    }

    @Test
    public void traverseAllSpaceShipDecksTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new Command[]{
                new Command("[en," + spaceShipDeck1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + spaceShipDeck2 + "]"),
                new Command("[so,2]"),
                new Command("[ea,1]"),
                new Command("[tr," + spaceShipDeck3 + "]"),
                new Command("[we,5]"),
                new Command("[ea,2]"),
                new Command("[tr," + spaceShipDeck2 + "]"),
                new Command("[no,4]"),
                new Command("[ea,3]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 4, 1);
    }
}
