package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.application.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1MovementTests extends MovementTests {

    private CleaningDeviceService cleaningDeviceService;
    private SpaceService spaceService;
    private TransportTechnologyService transportTechnologyService;


    @Autowired
    public E1MovementTests(WebApplicationContext appContext,
                           CleaningDeviceService cleaningDeviceService,
                           SpaceService spaceService,
                           TransportTechnologyService transportTechnologyService) {
        super(appContext);

        this.cleaningDeviceService = cleaningDeviceService;
        this.spaceService = spaceService;
        this.transportTechnologyService = transportTechnologyService;
    }

    @BeforeEach
    public void init() {
        createWorld(cleaningDeviceService, spaceService, transportTechnologyService);
    }

    @Test
    @Transactional
    public void cleaningDevicesSpawnOnSamePositionTest() {
        assertTrue(cleaningDeviceService.executeCommand(cleaningDevice1, Order.fromString("[en," + space1 + "]")));
        assertFalse(cleaningDeviceService.executeCommand(cleaningDevice2, Order.fromString("[en," + space1 + "]")));
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceAndBackTest() throws Exception {
        executeOrders(cleaningDeviceService, cleaningDevice1, new Order[]{
                Order.fromString("[en," + space2 + "]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[tr," + space3 + "]")
        });

        assertPosition(cleaningDevice1, space3, 2, 2);

        executeOrders(cleaningDeviceService, cleaningDevice1, new Order[]{
                Order.fromString("[tr," + space2 + "]")
        });

        assertPosition(cleaningDevice1, space2, 1, 0);
    }

    @Test
    @Transactional
    public void moveToAnotherSpaceOnWrongPositionTest() throws Exception {
        cleaningDeviceService.executeCommand(cleaningDevice1, Order.fromString("[en," + space1 + "]"));
        assertFalse(cleaningDeviceService.executeCommand(cleaningDevice1, Order.fromString("[tr," + space2 + "]")));

        assertPosition(cleaningDevice1, space1, 0, 0);
    }

    @Test
    @Transactional
    public void bumpIntoObstacleTest() throws Exception {
        executeOrders(cleaningDeviceService, cleaningDevice1, new Order[]{
                Order.fromString("[en," + space2 + "]"),
                Order.fromString("[ea,2]"),
                Order.fromString("[no,3]"),
                Order.fromString("[we,1]"),
        });

        assertPosition(cleaningDevice1, space2, 1, 1);
    }

    @Test
    @Transactional
    public void moveTwoCleaningDevicesAtOnceTest() throws Exception {
        executeOrders(cleaningDeviceService, cleaningDevice1, new Order[]{
                Order.fromString("[en," + space1 + "]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[no,4]")
        });

        executeOrders(cleaningDeviceService, cleaningDevice2, new Order[]{
                Order.fromString("[en," + space1 + "]"),
                Order.fromString("[ea,2]"),
                Order.fromString("[no,5]")
        });

        assertPosition(cleaningDevice1, space1, 1, 2);
        assertPosition(cleaningDevice2, space1, 2, 5);
    }

    @Test
    @Transactional
    public void moveOutOfBoundariesTest() throws Exception {
        executeOrders(cleaningDeviceService, cleaningDevice1, new Order[]{
                Order.fromString("[en," + space3 + "]"),
                Order.fromString("[no,5]"),
                Order.fromString("[ea,5]"),
                Order.fromString("[so,5]"),
                Order.fromString("[we,5]"),
                Order.fromString("[no,1]")
        });

        assertPosition(cleaningDevice1, space3, 0, 1);
    }

    @Test
    @Transactional
    public void traverseOnOneSpaceTest() throws Exception {
        cleaningDeviceService.executeCommand(cleaningDevice1, Order.fromString("[en," + space1 + "]"));
        assertPosition(cleaningDevice1, space1, 0, 0);

        cleaningDeviceService.executeCommand(cleaningDevice1, Order.fromString("[ea,2]"));
        assertPosition(cleaningDevice1, space1, 2, 0);

        cleaningDeviceService.executeCommand(cleaningDevice1, Order.fromString("[no,4]"));
        assertPosition(cleaningDevice1, space1, 2, 4);

        cleaningDeviceService.executeCommand(cleaningDevice1, Order.fromString("[we,5]"));
        assertPosition(cleaningDevice1, space1, 0, 4);
    }

    @Test
    @Transactional
    public void traverseAllSpacesTest() throws Exception {
        executeOrders(cleaningDeviceService, cleaningDevice1, new Order[]{
                Order.fromString("[en," + space1 + "]"),
                Order.fromString("[no,1]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[tr," + space2 + "]"),
                Order.fromString("[so,2]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[tr," + space3 + "]"),
                Order.fromString("[we,5]"),
                Order.fromString("[ea,2]"),
                Order.fromString("[tr," + space2 + "]"),
                Order.fromString("[no,4]"),
                Order.fromString("[ea,3]")
        });

        assertPosition(cleaningDevice1, space2, 4, 1);
    }
}
