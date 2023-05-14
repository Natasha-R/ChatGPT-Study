package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;


public class E2Tests extends MovementTests {

    private MaintenanceDroidService service;

    @BeforeEach
    public void initService() {
        this.service = new MaintenanceDroidService();
        createWorld(this.service);
    }

    @Test
    public void movingOutOfBoundsShouldNotBePossible() {
        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck3 + "]"),
                new Task("[so,1]"),
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 0);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[we,1]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 0);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[no,3]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 2);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[ea,3]")
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 2, 2);
    }

    @Test
    public void trCommandShouldNotWorkIfCurrentFieldHasNoConnection() {
        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck3 + "]"),
                new Task("[tr," + spaceShipDeck1 + "]"),
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck3, 0, 0);
    }

    @Test
    public void traversingAConnectionOntoAnotherDroidShouldNotWork() {
        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck1 + "]"),
                new Task("[no,1]"),
                new Task("[ea,1]"),
                new Task("[tr," + spaceShipDeck2 + "]"),
        });

        assertPosition(service, maintenanceDroid1, spaceShipDeck2, 0, 1);

        executeTasks(service, maintenanceDroid2, new Task[]{
                new Task("[en," + spaceShipDeck1 + "]"),
                new Task("[no,1]"),
                new Task("[ea,1]"),
                new Task("[tr," + spaceShipDeck2 + "]"),
        });

        assertPosition(service, maintenanceDroid2, spaceShipDeck1, 1, 1);
    }

}
