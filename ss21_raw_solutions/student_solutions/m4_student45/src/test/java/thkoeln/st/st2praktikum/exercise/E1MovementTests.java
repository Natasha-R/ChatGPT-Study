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
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

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
        assertTrue(tidyUpRobotService.executeCommand(tidyUpRobot1, Order.fromString("[en," + room1 + "]")));
        assertFalse(tidyUpRobotService.executeCommand(tidyUpRobot2, Order.fromString("[en," + room1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherRoomAndBackTest() throws Exception {
        executeOrders(tidyUpRobotService, tidyUpRobot1, new Order[]{
                Order.fromString("[en," + room2 + "]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[tr," + room3 + "]")
        });

        assertPosition(tidyUpRobot1, room3, 2, 2);

        executeOrders(tidyUpRobotService, tidyUpRobot1, new Order[]{
                Order.fromString("[tr," + room2 + "]")
        });

        assertPosition(tidyUpRobot1, room2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherRoomOnWrongPositionTest() throws Exception {
        tidyUpRobotService.executeCommand(tidyUpRobot1, Order.fromString("[en," + room1 + "]"));
        assertFalse(tidyUpRobotService.executeCommand(tidyUpRobot1, Order.fromString("[tr," + room2 + "]")));

        assertPosition(tidyUpRobot1, room1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoWallTest() throws Exception {
        executeOrders(tidyUpRobotService, tidyUpRobot1, new Order[]{
                Order.fromString("[en," + room2 + "]"),
                Order.fromString("[ea,2]"),
                Order.fromString("[no,3]"),
                Order.fromString("[we,1]"),
        });

        assertPosition(tidyUpRobot1, room2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoTidyUpRobotsAtOnceTest() throws Exception {
        executeOrders(tidyUpRobotService, tidyUpRobot1, new Order[]{
                Order.fromString("[en," + room1 + "]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[no,4]")
        });

        executeOrders(tidyUpRobotService, tidyUpRobot2, new Order[]{
                Order.fromString("[en," + room1 + "]"),
                Order.fromString("[ea,2]"),
                Order.fromString("[no,5]")
        });

        assertPosition(tidyUpRobot1, room1, 1, 2);
        assertPosition(tidyUpRobot2, room1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeOrders(tidyUpRobotService, tidyUpRobot1, new Order[]{
                Order.fromString("[en," + room3 + "]"),
                Order.fromString("[no,5]"),
                Order.fromString("[ea,5]"),
                Order.fromString("[so,5]"),
                Order.fromString("[we,5]"),
                Order.fromString("[no,1]")
        });

        assertPosition(tidyUpRobot1, room3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneRoomTest() throws Exception {
        tidyUpRobotService.executeCommand(tidyUpRobot1, Order.fromString("[en," + room1 + "]"));
        assertPosition(tidyUpRobot1, room1, 0, 0);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Order.fromString("[ea,2]"));
        assertPosition(tidyUpRobot1, room1, 2, 0);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Order.fromString("[no,4]"));
        assertPosition(tidyUpRobot1, room1, 2, 4);

        tidyUpRobotService.executeCommand(tidyUpRobot1, Order.fromString("[we,5]"));
        assertPosition(tidyUpRobot1, room1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllRoomsTest() throws Exception {
        executeOrders(tidyUpRobotService, tidyUpRobot1, new Order[]{
                Order.fromString("[en," + room1 + "]"),
                Order.fromString("[no,1]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[tr," + room2 + "]"),
                Order.fromString("[so,2]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[tr," + room3 + "]"),
                Order.fromString("[we,5]"),
                Order.fromString("[ea,2]"),
                Order.fromString("[tr," + room2 + "]"),
                Order.fromString("[no,4]"),
                Order.fromString("[ea,3]")
        });

        assertPosition(tidyUpRobot1, room2, 4, 1);
    }
}
