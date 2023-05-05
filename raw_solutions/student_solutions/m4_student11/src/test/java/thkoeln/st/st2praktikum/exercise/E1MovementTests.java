package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private TidyUpRobotService tidyUpRobotService;
    private RoomService roomService;
    private TransportTechnologyService transportTechnologyService;


    @Autowired
    public E1MovementTests(WebApplicationContext appContext,
                           TidyUpRobotService tidyUpRobotService,
                           RoomService roomService,
                           TransportTechnologyService transportTechnologyService) {
        super(appContext);

        this.tidyUpRobotService = tidyUpRobotService;
        this.roomService = roomService;
        this.transportTechnologyService = transportTechnologyService;
    }

    @BeforeEach
    public void init() {
        createWorld(tidyUpRobotService, roomService, transportTechnologyService);
    }

    @Test
    @Transactional
    public void tidyUpRobotsSpawnOnSamePositionTest() {
        assertTrue(tidyUpRobotService.executeCommand(tidyUpRobot1, Task.fromString("[en," + room1 + "]")));
        assertFalse(tidyUpRobotService.executeCommand(tidyUpRobot2, Task.fromString("[en," + room1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherRoomAndBackTest() throws Exception {
        executeTasks(tidyUpRobotService, tidyUpRobot1, new Task[]{
                Task.fromString("[en," + room2 + "]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + room3 + "]")
        });

        assertPosition(tidyUpRobot1, room3, 2, 2);

        executeTasks(tidyUpRobotService, tidyUpRobot1, new Task[]{
                Task.fromString("[tr," + room2 + "]")
        });

        assertPosition(tidyUpRobot1, room2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherRoomOnWrongPositionTest() throws Exception {
        tidyUpRobotService.executeCommand(tidyUpRobot1, Task.fromString("[en," + room1 + "]"));
        assertFalse(tidyUpRobotService.executeCommand(tidyUpRobot1, Task.fromString("[tr," + room2 + "]")));

        assertPosition(tidyUpRobot1, room1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoObstacleTest() throws Exception {
        executeTasks(tidyUpRobotService, tidyUpRobot1, new Task[]{
                Task.fromString("[en," + room2 + "]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[no,3]"),
                Task.fromString("[we,1]"),
        });

        assertPosition(tidyUpRobot1, room2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoTidyUpRobotsAtOnceTest() throws Exception {
        executeTasks(tidyUpRobotService, tidyUpRobot1, new Task[]{
                Task.fromString("[en," + room1 + "]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[no,4]")
        });

        executeTasks(tidyUpRobotService, tidyUpRobot2, new Task[]{
                Task.fromString("[en," + room1 + "]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[no,5]")
        });

        assertPosition(tidyUpRobot1, room1, 1, 2);
        assertPosition(tidyUpRobot2, room1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeTasks(tidyUpRobotService, tidyUpRobot1, new Task[]{
                Task.fromString("[en," + room3 + "]"),
                Task.fromString("[no,5]"),
                Task.fromString("[ea,5]"),
                Task.fromString("[so,5]"),
                Task.fromString("[we,5]"),
                Task.fromString("[no,1]")
        });

        assertPosition(tidyUpRobot1, room3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneRoomTest() throws Exception {
        tidyUpRobotService.executeCommand(tidyUpRobot1, Task.fromString("[en," + room1 + "]"));
        assertPosition(tidyUpRobot1, room1, 0, 0);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Task.fromString("[ea,2]"));
        assertPosition(tidyUpRobot1, room1, 2, 0);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Task.fromString("[no,4]"));
        assertPosition(tidyUpRobot1, room1, 2, 4);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Task.fromString("[we,5]"));
        assertPosition(tidyUpRobot1, room1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllRoomsTest() throws Exception {
        executeTasks(tidyUpRobotService, tidyUpRobot1, new Task[]{
                Task.fromString("[en," + room1 + "]"),
                Task.fromString("[no,1]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + room2 + "]"),
                Task.fromString("[so,2]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[tr," + room3 + "]"),
                Task.fromString("[we,5]"),
                Task.fromString("[ea,2]"),
                Task.fromString("[tr," + room2 + "]"),
                Task.fromString("[no,4]"),
                Task.fromString("[ea,3]")
        });

        assertPosition(tidyUpRobot1, room2, 4, 1);
    }
}
