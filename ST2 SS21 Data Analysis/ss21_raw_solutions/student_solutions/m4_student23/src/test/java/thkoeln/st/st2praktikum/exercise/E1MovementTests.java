package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.application.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystemService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private MaintenanceDroidService maintenanceDroidService;
    private SpaceShipDeckService spaceShipDeckService;
    private TransportSystemService transportSystemService;


    @Autowired
    public E1MovementTests(WebApplicationContext appContext,
                           MaintenanceDroidService maintenanceDroidService,
                           SpaceShipDeckService spaceShipDeckService,
                           TransportSystemService transportSystemService) {
        super(appContext);

        this.maintenanceDroidService = maintenanceDroidService;
        this.spaceShipDeckService = spaceShipDeckService;
        this.transportSystemService = transportSystemService;
    }

    @BeforeEach
    public void init() {
        createWorld(maintenanceDroidService, spaceShipDeckService, transportSystemService);
    }

    @Test
    @Transactional
    public void maintenanceDroidsSpawnOnSamePositionTest() {
        assertTrue(maintenanceDroidService.executeCommand(maintenanceDroid1, Task.fromString("[en," + spaceShipDeck1 + "]")));
        assertFalse(maintenanceDroidService.executeCommand(maintenanceDroid2, Task.fromString("[en," + spaceShipDeck1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceShipDeckAndBackTest() throws Exception {
        executeTasks(maintenanceDroidService, maintenanceDroid1, new Task[]{
                Task.fromString("[en," + spaceShipDeck2 + "]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + spaceShipDeck3 + "]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck3, 2, 2);

        executeTasks(maintenanceDroidService, maintenanceDroid1, new Task[]{
                Task.fromString("[tr," + spaceShipDeck2 + "]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceShipDeckOnWrongPositionTest() throws Exception {
        maintenanceDroidService.executeCommand(maintenanceDroid1, Task.fromString("[en," + spaceShipDeck1 + "]"));
        assertFalse(maintenanceDroidService.executeCommand(maintenanceDroid1, Task.fromString("[tr," + spaceShipDeck2 + "]")));

        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoBarrierTest() throws Exception {
        executeTasks(maintenanceDroidService, maintenanceDroid1, new Task[]{
                Task.fromString("[en," + spaceShipDeck2 + "]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[no,3]"),
                Task.fromString("[we,1]"),
        });

        assertPosition(maintenanceDroid1, spaceShipDeck2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoMaintenanceDroidsAtOnceTest() throws Exception {
        executeTasks(maintenanceDroidService, maintenanceDroid1, new Task[]{
                Task.fromString("[en," + spaceShipDeck1 + "]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[no,4]")
        });

        executeTasks(maintenanceDroidService, maintenanceDroid2, new Task[]{
                Task.fromString("[en," + spaceShipDeck1 + "]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[no,5]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck1, 1, 2);
        assertPosition(maintenanceDroid2, spaceShipDeck1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeTasks(maintenanceDroidService, maintenanceDroid1, new Task[]{
                Task.fromString("[en," + spaceShipDeck3 + "]"),
                Task.fromString("[no,5]"),
                Task.fromString("[ea,5]"),
                Task.fromString("[so,5]"),
                Task.fromString("[we,5]"),
                Task.fromString("[no,1]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneSpaceShipDeckTest() throws Exception {
        maintenanceDroidService.executeCommand(maintenanceDroid1, Task.fromString("[en," + spaceShipDeck1 + "]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 0);

        maintenanceDroidService.executeCommand(maintenanceDroid1, Task.fromString("[ea,2]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 2, 0);

        maintenanceDroidService.executeCommand(maintenanceDroid1, Task.fromString("[no,4]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 2, 4);

        maintenanceDroidService.executeCommand(maintenanceDroid1, Task.fromString("[we,5]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllSpaceShipDecksTest() throws Exception {
        executeTasks(maintenanceDroidService, maintenanceDroid1, new Task[]{
                Task.fromString("[en," + spaceShipDeck1 + "]"),
                Task.fromString("[no,1]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + spaceShipDeck2 + "]"),
                Task.fromString("[so,2]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + spaceShipDeck3 + "]"),
                Task.fromString("[we,5]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[tr," + spaceShipDeck2 + "]"),
                Task.fromString("[no,4]"),
                Task.fromString("[ea,3]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck2, 4, 1);
    }
}
