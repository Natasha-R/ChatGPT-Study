package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.application.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private MaintenanceDroidService maintenanceDroidService;
    private SpaceShipDeckService spaceShipDeckService;
    private TransportCategoryService transportCategoryService;


    @Autowired
    public E1MovementTests(WebApplicationContext appContext,
                           MaintenanceDroidService maintenanceDroidService,
                           SpaceShipDeckService spaceShipDeckService,
                           TransportCategoryService transportCategoryService) {
        super(appContext);

        this.maintenanceDroidService = maintenanceDroidService;
        this.spaceShipDeckService = spaceShipDeckService;
        this.transportCategoryService = transportCategoryService;
    }

    @BeforeEach
    public void init() {
        createWorld(maintenanceDroidService, spaceShipDeckService, transportCategoryService);
    }

    @Test
    @Transactional
    public void maintenanceDroidsSpawnOnSamePositionTest() {
        assertTrue(maintenanceDroidService.executeCommand(maintenanceDroid1, Command.fromString("[en," + spaceShipDeck1 + "]")));
        assertFalse(maintenanceDroidService.executeCommand(maintenanceDroid2, Command.fromString("[en," + spaceShipDeck1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceShipDeckAndBackTest() throws Exception {
        executeCommands(maintenanceDroidService, maintenanceDroid1, new Command[]{
                Command.fromString("[en," + spaceShipDeck2 + "]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + spaceShipDeck3 + "]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck3, 2, 2);

        executeCommands(maintenanceDroidService, maintenanceDroid1, new Command[]{
                Command.fromString("[tr," + spaceShipDeck2 + "]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceShipDeckOnWrongPositionTest() throws Exception {
        maintenanceDroidService.executeCommand(maintenanceDroid1, Command.fromString("[en," + spaceShipDeck1 + "]"));
        assertFalse(maintenanceDroidService.executeCommand(maintenanceDroid1, Command.fromString("[tr," + spaceShipDeck2 + "]")));

        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoBarrierTest() throws Exception {
        executeCommands(maintenanceDroidService, maintenanceDroid1, new Command[]{
                Command.fromString("[en," + spaceShipDeck2 + "]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[no,3]"),
                Command.fromString("[we,1]"),
        });

        assertPosition(maintenanceDroid1, spaceShipDeck2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoMaintenanceDroidsAtOnceTest() throws Exception {
        executeCommands(maintenanceDroidService, maintenanceDroid1, new Command[]{
                Command.fromString("[en," + spaceShipDeck1 + "]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[no,4]")
        });

        executeCommands(maintenanceDroidService, maintenanceDroid2, new Command[]{
                Command.fromString("[en," + spaceShipDeck1 + "]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[no,5]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck1, 1, 2);
        assertPosition(maintenanceDroid2, spaceShipDeck1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeCommands(maintenanceDroidService, maintenanceDroid1, new Command[]{
                Command.fromString("[en," + spaceShipDeck3 + "]"),
                Command.fromString("[no,5]"),
                Command.fromString("[ea,5]"),
                Command.fromString("[so,5]"),
                Command.fromString("[we,5]"),
                Command.fromString("[no,1]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneSpaceShipDeckTest() throws Exception {
        maintenanceDroidService.executeCommand(maintenanceDroid1, Command.fromString("[en," + spaceShipDeck1 + "]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 0);

        maintenanceDroidService.executeCommand(maintenanceDroid1, Command.fromString("[ea,2]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 2, 0);

        maintenanceDroidService.executeCommand(maintenanceDroid1, Command.fromString("[no,4]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 2, 4);

        maintenanceDroidService.executeCommand(maintenanceDroid1, Command.fromString("[we,5]"));
        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllSpaceShipDecksTest() throws Exception {
        executeCommands(maintenanceDroidService, maintenanceDroid1, new Command[]{
                Command.fromString("[en," + spaceShipDeck1 + "]"),
                Command.fromString("[no,1]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + spaceShipDeck2 + "]"),
                Command.fromString("[so,2]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + spaceShipDeck3 + "]"),
                Command.fromString("[we,5]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[tr," + spaceShipDeck2 + "]"),
                Command.fromString("[no,4]"),
                Command.fromString("[ea,3]")
        });

        assertPosition(maintenanceDroid1, spaceShipDeck2, 4, 1);
    }
}
