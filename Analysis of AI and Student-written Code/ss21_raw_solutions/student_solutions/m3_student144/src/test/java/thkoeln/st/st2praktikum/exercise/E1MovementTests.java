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

    private TidyUpRobotService service;

    @Autowired
    public E1MovementTests(WebApplicationContext appContext, TidyUpRobotService service) {
        super(appContext);

        this.service = service;
    }

    @BeforeEach
    public void init() {
        createWorld(service);
    }

    @Test
    @Transactional
    public void tidyUpRobotsSpawnOnSamePositionTest() {
        assertTrue(service.executeCommand(tidyUpRobot1, new Command("[en," + room1 + "]")));
        assertFalse(service.executeCommand(tidyUpRobot2, new Command("[en," + room1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherRoomAndBackTest() throws Exception {
        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room2 + "]"),
                new Command("[ea,1]"),
                new Command("[tr," + room3 + "]")
        });

        assertPosition(tidyUpRobot1, room3, 2, 2);

        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[tr," + room2 + "]")
        });

        assertPosition(tidyUpRobot1, room2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherRoomOnWrongPositionTest() throws Exception {
        service.executeCommand(tidyUpRobot1, new Command("[en," + room1 + "]"));
        assertFalse(service.executeCommand(tidyUpRobot1, new Command("[tr," + room2 + "]")));

        assertPosition(tidyUpRobot1, room1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoWallTest() throws Exception {
        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room2 + "]"),
                new Command("[ea,2]"),
                new Command("[no,3]"),
                new Command("[we,1]"),
        });

        assertPosition(tidyUpRobot1, room2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoTidyUpRobotsAtOnceTest() throws Exception {
        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[ea,1]"),
                new Command("[no,4]")
        });

        executeCommands(service, tidyUpRobot2, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[ea,2]"),
                new Command("[no,5]")
        });

        assertPosition(tidyUpRobot1, room1, 1, 2);
        assertPosition(tidyUpRobot2, room1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room3 + "]"),
                new Command("[no,5]"),
                new Command("[ea,5]"),
                new Command("[so,5]"),
                new Command("[we,5]"),
                new Command("[no,1]")
        });

        assertPosition(tidyUpRobot1, room3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneRoomTest() throws Exception {
        service.executeCommand(tidyUpRobot1, new Command("[en," + room1 + "]"));
        assertPosition(tidyUpRobot1, room1, 0, 0);

        service.executeCommand(tidyUpRobot1, new Command("[ea,2]"));
        assertPosition(tidyUpRobot1, room1, 2, 0);

        service.executeCommand(tidyUpRobot1, new Command("[no,4]"));
        assertPosition(tidyUpRobot1, room1, 2, 4);

        service.executeCommand(tidyUpRobot1, new Command("[we,5]"));
        assertPosition(tidyUpRobot1, room1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllRoomsTest() throws Exception {
        executeCommands(service, tidyUpRobot1, new Command[]{
                new Command("[en," + room1 + "]"),
                new Command("[no,1]"),
                new Command("[ea,1]"),
                new Command("[tr," + room2 + "]"),
                new Command("[so,2]"),
                new Command("[ea,1]"),
                new Command("[tr," + room3 + "]"),
                new Command("[we,5]"),
                new Command("[ea,2]"),
                new Command("[tr," + room2 + "]"),
                new Command("[no,4]"),
                new Command("[ea,3]")
        });

        assertPosition(tidyUpRobot1, room2, 4, 1);
    }
}
