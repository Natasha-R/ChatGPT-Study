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

    private CleaningDeviceService service;

    @Autowired
    public E1MovementTests(WebApplicationContext appContext, CleaningDeviceService service) {
        super(appContext);

        this.service = service;
    }

    @BeforeEach
    public void init() {
        createWorld(service);
    }

    @Test
    @Transactional
    public void cleaningDevicesSpawnOnSamePositionTest() {
        assertTrue(service.executeCommand(cleaningDevice1, new Command("[en," + space1 + "]")));
        assertFalse(service.executeCommand(cleaningDevice2, new Command("[en," + space1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceAndBackTest() throws Exception {
        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space2 + "]"),
                new Command("[ea,1]"),
                new Command("[tr," + space3 + "]")
        });

        assertPosition(cleaningDevice1, space3, 2, 2);

        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[tr," + space2 + "]")
        });

        assertPosition(cleaningDevice1, space2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceOnWrongPositionTest() throws Exception {
        service.executeCommand(cleaningDevice1, new Command("[en," + space1 + "]"));
        assertFalse(service.executeCommand(cleaningDevice1, new Command("[tr," + space2 + "]")));

        assertPosition(cleaningDevice1, space1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoObstacleTest() throws Exception {
        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space2 + "]"),
                new Command("[ea,2]"),
                new Command("[no,3]"),
                new Command("[we,1]"),
        });

        assertPosition(cleaningDevice1, space2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoCleaningDevicesAtOnceTest() throws Exception {
        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,4]")
        });

        executeCommands(service, cleaningDevice2, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[ea,2]"),
                new Command("[no,5]")
        });

        assertPosition(cleaningDevice1, space1, 1, 2);
        assertPosition(cleaningDevice2, space1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space3 + "]"),
                new Command("[no,5]"),
                new Command("[ea,5]"),
                new Command("[so,5]"),
                new Command("[we,5]"),
                new Command("[no,1]")
        });

        assertPosition(cleaningDevice1, space3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneSpaceTest() throws Exception {
        service.executeCommand(cleaningDevice1, new Command("[en," + space1 + "]"));
        assertPosition(cleaningDevice1, space1, 0, 0);

        service.executeCommand(cleaningDevice1, new Command("[ea,2]"));
        assertPosition(cleaningDevice1, space1, 2, 0);

        service.executeCommand(cleaningDevice1, new Command("[no,4]"));
        assertPosition(cleaningDevice1, space1, 2, 4);

        service.executeCommand(cleaningDevice1, new Command("[we,5]"));
        assertPosition(cleaningDevice1, space1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllSpacesTest() throws Exception {
        executeCommands(service, cleaningDevice1, new Command[]{
                new Command("[en," + space1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + space2 + "]"),
                new Command("[so,2]"),
                new Command("[ea,1]"),
                new Command("[tr," + space3 + "]"),
                new Command("[we,5]"),
                new Command("[ea,2]"),
                new Command("[tr," + space2 + "]"),
                new Command("[no,4]"),
                new Command("[ea,3]")
        });

        assertPosition(cleaningDevice1, space2, 4, 1);
    }
}
