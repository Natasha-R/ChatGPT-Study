package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private MaintenanceDroidService service;

    @Autowired
    public E1MovementTests(WebApplicationContext appContext, MaintenanceDroidService service) {
        super(appContext);

        this.service = service;
    }

    @BeforeEach
    public void init() {
        createWorld(service);
    }

    @Test
    @Transactional
    public void maintenanceDroidsSpawnOnSamePositionTest() {
        assertTrue(service.executeCommand(maintenanceDroid1, new Task("[en," + spaceShipDeck1 + "]")));
        assertFalse(service.executeCommand(maintenanceDroid2, new Task("[en," + spaceShipDeck1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceShipDeckAndBackTest() throws Exception {
        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck2 + "]"),
                new Task("[ea,1]"),
                new Task("[tr," + spaceShipDeck3 + "]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck3, 2, 2);

        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[tr," + spaceShipDeck2 + "]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceShipDeckOnWrongPositionTest() throws Exception {
        service.executeCommand(maintenanceDroid1, new Task("[en," + spaceShipDeck1 + "]"));
        assertFalse(service.executeCommand(maintenanceDroid1, new Task("[tr," + spaceShipDeck2 + "]")));

        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoBarrierTest() throws Exception {
        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck2 + "]"),
                new Task("[ea,2]"),
                new Task("[no,3]"),
                new Task("[we,1]"),
        });

        assertPosition(maintenanceDroid1, spaceShipDeck2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoMaintenanceDroidsAtOnceTest() throws Exception {
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

        assertPosition(maintenanceDroid1, spaceShipDeck1, 1, 2);
        assertPosition(maintenanceDroid2, spaceShipDeck1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeTasks(service, maintenanceDroid1, new Task[]{
                new Task("[en," + spaceShipDeck3 + "]"),
                new Task("[no,5]"),
                new Task("[ea,5]"),
                new Task("[so,5]"),
                new Task("[we,5]"),
                new Task("[no,1]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneSpaceShipDeckTest() throws Exception {
        service.executeCommand(maintenanceDroid1, new Task("[en," + spaceShipDeck1 + "]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 0);

        service.executeCommand(maintenanceDroid1, new Task("[ea,2]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 2, 0);

        service.executeCommand(maintenanceDroid1, new Task("[no,4]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 2, 4);

        service.executeCommand(maintenanceDroid1, new Task("[we,5]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllSpaceShipDecksTest() throws Exception {
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

        assertPosition(maintenanceDroid1, spaceShipDeck2, 4, 1);
    }
}
