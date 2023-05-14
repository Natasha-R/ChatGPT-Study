package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class E1MovementTests extends MovementTests {

    @Test
    public void cleaningDeviceSpawnOnSamePositionTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        assertTrue(service.executeCommand(cleaningDevice1, new Order("[en," + space1 + "]")));
        assertFalse(service.executeCommand(cleaningDevice2, new Order("[en," + space1 + "]")));
    }

    @Test
    public void moveToAnotherSpaceAndBackTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + space3 + "]")
        });

        assertPosition(service, cleaningDevice1, space3, 2, 2);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[tr," + space2 + "]")
        });

        assertPosition(service, cleaningDevice1, space2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceOnWrongPositionTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        service.executeCommand(cleaningDevice1, new Order("[en," + space1 + "]"));
        assertFalse(service.executeCommand(cleaningDevice1, new Order("[tr," + space2 + "]")));

        assertPosition(service, cleaningDevice1, space1, 0, 0);
    }

    @Test
    public void bumpIntoObstacleTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space2 + "]"),
                new Order("[ea,2]"),
                new Order("[no,3]"),
                new Order("[we,1]"),
        });

        assertPosition(service, cleaningDevice1, space2, 1, 1);
    }

    @Test
    public void moveTwoCleaningDevicesAtOnceTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space1 + "]"),
                new Order("[ea,1]"),
                new Order("[no,4]")
        });

        executeOrders(service, cleaningDevice2, new Order[]{
                new Order("[en," + space1 + "]"),
                new Order("[ea,2]"),
                new Order("[no,5]")
        });

        assertPosition(service, cleaningDevice1, space1, 1, 2);
        assertPosition(service, cleaningDevice2, space1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space3 + "]"),
                new Order("[no,5]"),
                new Order("[ea,5]"),
                new Order("[so,5]"),
                new Order("[we,5]"),
                new Order("[no,1]")
        });

        assertPosition(service, cleaningDevice1, space3, 0, 1);
    }

    @Test
    public void traverseAllSpacesTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space1 + "]"),
                new Order("[no,1]"),
                new Order("[ea,1]"),
                new Order("[tr," + space2 + "]"),
                new Order("[so,2]"),
                new Order("[ea,1]"),
                new Order("[tr," + space3 + "]"),
                new Order("[we,5]"),
                new Order("[ea,2]"),
                new Order("[tr," + space2 + "]"),
                new Order("[no,4]"),
                new Order("[ea,3]")
        });

        assertPosition(service, cleaningDevice1, space2, 4, 1);
    }
}
