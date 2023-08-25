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
        assertTrue(service.executeCommand(tidyUpRobot1, new Order("[en," + room1 + "]")));
        assertFalse(service.executeCommand(tidyUpRobot2, new Order("[en," + room1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherRoomAndBackTest() throws Exception {
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + room3 + "]")
        });

        assertPosition(tidyUpRobot1, room3, 2, 2);

        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[tr," + room2 + "]")
        });

        assertPosition(tidyUpRobot1, room2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherRoomOnWrongPositionTest() throws Exception {
        service.executeCommand(tidyUpRobot1, new Order("[en," + room1 + "]"));
        assertFalse(service.executeCommand(tidyUpRobot1, new Order("[tr," + room2 + "]")));

        assertPosition(tidyUpRobot1, room1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoBarrierTest() throws Exception {
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room2 + "]"),
                new Order("[ea,2]"),
                new Order("[no,3]"),
                new Order("[we,1]"),
        });

        assertPosition(tidyUpRobot1, room2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoTidyUpRobotsAtOnceTest() throws Exception {
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,1]"),
                new Order("[no,4]")
        });

        executeOrders(service, tidyUpRobot2, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[ea,2]"),
                new Order("[no,5]")
        });

        assertPosition(tidyUpRobot1, room1, 1, 2);
        assertPosition(tidyUpRobot2, room1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room3 + "]"),
                new Order("[no,5]"),
                new Order("[ea,5]"),
                new Order("[so,5]"),
                new Order("[we,5]"),
                new Order("[no,1]")
        });

        assertPosition(tidyUpRobot1, room3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneRoomTest() throws Exception {
        service.executeCommand(tidyUpRobot1, new Order("[en," + room1 + "]"));
        assertPosition(tidyUpRobot1, room1, 0, 0);

        service.executeCommand(tidyUpRobot1, new Order("[ea,2]"));
        assertPosition(tidyUpRobot1, room1, 2, 0);

        service.executeCommand(tidyUpRobot1, new Order("[no,4]"));
        assertPosition(tidyUpRobot1, room1, 2, 4);

        service.executeCommand(tidyUpRobot1, new Order("[we,5]"));
        assertPosition(tidyUpRobot1, room1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllRoomsTest() throws Exception {
        executeOrders(service, tidyUpRobot1, new Order[]{
                new Order("[en," + room1 + "]"),
                new Order("[no,1]"),
                new Order("[ea,1]"),
                new Order("[tr," + room2 + "]"),
                new Order("[so,2]"),
                new Order("[ea,1]"),
                new Order("[tr," + room3 + "]"),
                new Order("[we,5]"),
                new Order("[ea,2]"),
                new Order("[tr," + room2 + "]"),
                new Order("[no,4]"),
                new Order("[ea,3]")
        });

        assertPosition(tidyUpRobot1, room2, 4, 1);
    }
}
