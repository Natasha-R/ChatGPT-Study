package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.application.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceService;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private CleaningDeviceService cleaningDeviceService;
    private SpaceService spaceService;
    private TransportCategoryService transportCategoryService;


    @Autowired
    public E1MovementTests(WebApplicationContext appContext,
                           CleaningDeviceService cleaningDeviceService,
                           SpaceService spaceService,
                           TransportCategoryService transportCategoryService) {
        super(appContext);

        this.cleaningDeviceService = cleaningDeviceService;
        this.spaceService = spaceService;
        this.transportCategoryService = transportCategoryService;
    }

    @BeforeEach
    public void init() {
        createWorld(cleaningDeviceService, spaceService, transportCategoryService);
    }

    @Test
    @Transactional
    public void cleaningDevicesSpawnOnSamePositionTest() {
        assertTrue(cleaningDeviceService.executeCommand(cleaningDevice1, Task.fromString("[en," + space1 + "]")));
        assertFalse(cleaningDeviceService.executeCommand(cleaningDevice2, Task.fromString("[en," + space1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceAndBackTest() throws Exception {
        executeTasks(cleaningDeviceService, cleaningDevice1, new Task[]{
                Task.fromString("[en," + space2 + "]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + space3 + "]")
        });

        this.printDebug();

        assertPosition(cleaningDevice1, space3, 2, 2);

        executeTasks(cleaningDeviceService, cleaningDevice1, new Task[]{
                Task.fromString("[tr," + space2 + "]")
        });

        assertPosition(cleaningDevice1, space2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceOnWrongPositionTest() throws Exception {
        cleaningDeviceService.executeCommand(cleaningDevice1, Task.fromString("[en," + space1 + "]"));
        assertFalse(cleaningDeviceService.executeCommand(cleaningDevice1, Task.fromString("[tr," + space2 + "]")));

        assertPosition(cleaningDevice1, space1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoObstacleTest() throws Exception {
        executeTasks(cleaningDeviceService, cleaningDevice1, new Task[]{
                Task.fromString("[en," + space2 + "]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[no,3]"),
                Task.fromString("[we,1]"),
        });

        assertPosition(cleaningDevice1, space2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoCleaningDevicesAtOnceTest() throws Exception {
        executeTasks(cleaningDeviceService, cleaningDevice1, new Task[]{
                Task.fromString("[en," + space1 + "]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[no,4]")
        });

        executeTasks(cleaningDeviceService, cleaningDevice2, new Task[]{
                Task.fromString("[en," + space1 + "]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[no,5]")
        });

        assertPosition(cleaningDevice1, space1, 1, 2);
        assertPosition(cleaningDevice2, space1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeTasks(cleaningDeviceService, cleaningDevice1, new Task[]{
                Task.fromString("[en," + space3 + "]"),
                Task.fromString("[no,5]"),
                Task.fromString("[ea,5]"),
                Task.fromString("[so,5]"),
                Task.fromString("[we,5]"),
                Task.fromString("[no,1]")
        });

        assertPosition(cleaningDevice1, space3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneSpaceTest() throws Exception {
        cleaningDeviceService.executeCommand(cleaningDevice1, Task.fromString("[en," + space1 + "]"));
        assertPosition(cleaningDevice1, space1, 0, 0);

        cleaningDeviceService.executeCommand(cleaningDevice1, Task.fromString("[ea,2]"));
        assertPosition(cleaningDevice1, space1, 2, 0);

        cleaningDeviceService.executeCommand(cleaningDevice1, Task.fromString("[no,4]"));
        assertPosition(cleaningDevice1, space1, 2, 4);

        cleaningDeviceService.executeCommand(cleaningDevice1, Task.fromString("[we,5]"));
        assertPosition(cleaningDevice1, space1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllSpacesTest() throws Exception {
        executeTasks(cleaningDeviceService, cleaningDevice1, new Task[]{
                Task.fromString("[en," + space1 + "]"),
                Task.fromString("[no,1]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + space2 + "]"),
                Task.fromString("[so,2]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + space3 + "]"),
                Task.fromString("[we,5]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[tr," + space2 + "]"),
                Task.fromString("[no,4]"),
                Task.fromString("[ea,3]")
        });

        this.printDebug();

        assertPosition(cleaningDevice1, space2, 4, 1);
    }
}