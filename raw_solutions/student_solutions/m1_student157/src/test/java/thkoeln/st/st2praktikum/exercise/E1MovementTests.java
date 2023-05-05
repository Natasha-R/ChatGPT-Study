package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class E1MovementTests extends MovementTests {

    @Test
    public void maintenanceDroidSpawnOnSamePositionTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        assertTrue(service.executeCommand(maintenanceDroid1, "[en," + spaceShipDeck1 + "]"));
        assertFalse(service.executeCommand(maintenanceDroid2, "[en," + spaceShipDeck1 + "]"));
    }

    @Test
    public void movementTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new String[]{
                "[en," + spaceShipDeck2 + "]",
                "[ea,1]"
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceShipDeckAndBackTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new String[]{
                "[en," + spaceShipDeck2 + "]",
                "[ea,1]",
                "[tr," + spaceShipDeck3 + "]"
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 2, 2);

        executeCommands(service, maintenanceDroid1, new String[]{
                "[tr," + spaceShipDeck2 + "]"
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceShipDeckOnWrongPositionTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        service.executeCommand(maintenanceDroid1, "[en," + spaceShipDeck1 + "]");
        assertFalse(service.executeCommand(maintenanceDroid1, "[tr," + spaceShipDeck2 + "]"));

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    public void bumpIntoWallTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        // "(0,2)-(4,2)"

        executeCommands(service, maintenanceDroid1, new String[]{
                "[en," + spaceShipDeck2 + "]",
        });

        // 0 0

        executeCommands(service, maintenanceDroid1, new String[]{
                "[ea,2]",
        });

        // 2 0
        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 2, 0);

        executeCommands(service, maintenanceDroid1, new String[]{
                "[no,3]",
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 2, 1);

        executeCommands(service, maintenanceDroid1, new String[]{
                "[we,1]"
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 1, 1);
    }

    @Test
    public void moveTwoMaintenanceDroidsAtOnceTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new String[]{
                "[en," + spaceShipDeck1 + "]",
                "[ea,1]",
                "[no,4]"
        });

        executeCommands(service, maintenanceDroid2, new String[]{
                "[en," + spaceShipDeck1 + "]",
                "[ea,2]",
                "[no,5]"
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck1, 1, 2);
        assertPosition(service, maintenanceDroid2, spaceShipDeck1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new String[]{
                "[en," + spaceShipDeck3 + "]",
                "[no,5]",
                "[ea,5]",
                "[so,5]",
                "[we,5]",
                "[no,1]"
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 1);
    }

    @Test
    public void traverseAllSpaceShipDecksTest() {
        MaintenanceDroidService service = new MaintenanceDroidService();
        createWorld(service);

        executeCommands(service, maintenanceDroid1, new String[]{
                "[en," + spaceShipDeck1 + "]",
                "[no,1]",
                "[ea,1]",
                "[tr," + spaceShipDeck2 + "]",
                "[so,2]",
                "[ea,1]",
                "[tr," + spaceShipDeck3 + "]",
                "[we,5]",
                "[ea,2]",
                "[tr," + spaceShipDeck2 + "]",
                "[no,4]",
                "[ea,3]"
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 4, 1);
    }
}
