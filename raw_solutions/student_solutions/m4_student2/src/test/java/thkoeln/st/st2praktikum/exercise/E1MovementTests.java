package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystemService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private TidyUpRobotService tidyUpRobotService;
    private RoomService roomService;
    private TransportSystemService transportSystemService;


    @Autowired
    public E1MovementTests(WebApplicationContext appContext,
                           TidyUpRobotService tidyUpRobotService,
                           RoomService roomService,
                           TransportSystemService transportSystemService) {
        super(appContext);

        this.tidyUpRobotService = tidyUpRobotService;
        this.roomService = roomService;
        this.transportSystemService = transportSystemService;
    }

    @BeforeEach
    public void init() {
        createWorld(tidyUpRobotService, roomService, transportSystemService);
    }

    @Test
    @Transactional
    public void tidyUpRobotsSpawnOnSamePositionTest() {
        assertTrue(tidyUpRobotService.executeCommand(tidyUpRobot1, Command.fromString("[en," + room1 + "]")));
        assertFalse(tidyUpRobotService.executeCommand(tidyUpRobot2, Command.fromString("[en," + room1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherRoomAndBackTest() throws Exception {
        executeCommands(tidyUpRobotService, tidyUpRobot1, new Command[]{
                Command.fromString("[en," + room2 + "]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + room3 + "]")
        });

        assertPosition(tidyUpRobot1, room3, 2, 2);

        executeCommands(tidyUpRobotService, tidyUpRobot1, new Command[]{
                Command.fromString("[tr," + room2 + "]")
        });

        assertPosition(tidyUpRobot1, room2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherRoomOnWrongPositionTest() throws Exception {
        tidyUpRobotService.executeCommand(tidyUpRobot1, Command.fromString("[en," + room1 + "]"));
        assertFalse(tidyUpRobotService.executeCommand(tidyUpRobot1, Command.fromString("[tr," + room2 + "]")));

        assertPosition(tidyUpRobot1, room1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoObstacleTest() throws Exception {
        executeCommands(tidyUpRobotService, tidyUpRobot1, new Command[]{
                Command.fromString("[en," + room2 + "]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[no,3]"),
                Command.fromString("[we,1]"),
        });

        assertPosition(tidyUpRobot1, room2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoTidyUpRobotsAtOnceTest() throws Exception {
        executeCommands(tidyUpRobotService, tidyUpRobot1, new Command[]{
                Command.fromString("[en," + room1 + "]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[no,4]")
        });

        executeCommands(tidyUpRobotService, tidyUpRobot2, new Command[]{
                Command.fromString("[en," + room1 + "]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[no,5]")
        });

        assertPosition(tidyUpRobot1, room1, 1, 2);
        assertPosition(tidyUpRobot2, room1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeCommands(tidyUpRobotService, tidyUpRobot1, new Command[]{
                Command.fromString("[en," + room3 + "]"),
                Command.fromString("[no,5]"),
                Command.fromString("[ea,5]"),
                Command.fromString("[so,5]"),
                Command.fromString("[we,5]"),
                Command.fromString("[no,1]")
        });

        assertPosition(tidyUpRobot1, room3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneRoomTest() throws Exception {
        tidyUpRobotService.executeCommand(tidyUpRobot1, Command.fromString("[en," + room1 + "]"));
        assertPosition(tidyUpRobot1, room1, 0, 0);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Command.fromString("[ea,2]"));
        assertPosition(tidyUpRobot1, room1, 2, 0);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Command.fromString("[no,4]"));
        assertPosition(tidyUpRobot1, room1, 2, 4);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Command.fromString("[we,5]"));
        assertPosition(tidyUpRobot1, room1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllRoomsTest() throws Exception {
        executeCommands(tidyUpRobotService, tidyUpRobot1, new Command[]{
                Command.fromString("[en," + room1 + "]"),
                Command.fromString("[no,1]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + room2 + "]"),
                Command.fromString("[so,2]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[tr," + room3 + "]"),
                Command.fromString("[we,5]"),
                Command.fromString("[ea,2]"),
                Command.fromString("[tr," + room2 + "]"),
                Command.fromString("[no,4]"),
                Command.fromString("[ea,3]")
        });

        assertPosition(tidyUpRobot1, room2, 4, 1);
    }
}
