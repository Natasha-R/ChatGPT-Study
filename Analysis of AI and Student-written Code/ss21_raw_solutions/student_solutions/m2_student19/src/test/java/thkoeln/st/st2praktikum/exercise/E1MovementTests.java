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

        assertTrue(service.executeCommand(cleaningDevice1, new Task("[en," + space1 + "]")));
        assertFalse(service.executeCommand(cleaningDevice2, new Task("[en," + space1 + "]")));
    }

    @Test
    public void moveToAnotherSpaceAndBackTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + space2 + "]"),
                new Task("[ea,1]"),
                new Task("[tr," + space3 + "]")
        });

        assertPosition(service, cleaningDevice1, space3, 2, 2);

        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[tr," + space2 + "]")
        });

        assertPosition(service, cleaningDevice1, space2, 1, 0);
    }

    @Test
    public void moveToAnotherSpaceOnWrongPositionTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        service.executeCommand(cleaningDevice1, new Task("[en," + space1 + "]"));
        assertFalse(service.executeCommand(cleaningDevice1, new Task("[tr," + space2 + "]")));

        assertPosition(service, cleaningDevice1, space1, 0, 0);
    }

    @Test
    public void bumpIntoWallTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + space2 + "]"),
                new Task("[ea,2]"),
                new Task("[no,3]"),
                new Task("[we,1]"),
        });

        assertPosition(service, cleaningDevice1, space2, 1, 1);
    }

    @Test
    public void moveTwoCleaningDevicesAtOnceTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + space1 + "]"),
                new Task("[ea,1]"),
                new Task("[no,4]")
        });

        executeTasks(service, cleaningDevice2, new Task[]{
                new Task("[en," + space1 + "]"),
                new Task("[ea,2]"),
                new Task("[no,5]")
        });

        assertPosition(service, cleaningDevice1, space1, 1, 2);
        assertPosition(service, cleaningDevice2, space1, 2, 5);
    }

    @Test
    public void moveOutOfBoundariesTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + space3 + "]"),
                new Task("[no,5]"),
                new Task("[ea,5]"),
                new Task("[so,5]"),
                new Task("[we,5]"),
                new Task("[no,1]")
        });

        assertPosition(service, cleaningDevice1, space3, 0, 1);
    }

    @Test
    public void traverseAllSpacesTest() {
        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeTasks(service, cleaningDevice1, new Task[]{
                new Task("[en," + space1 + "]"),
                new Task("[no,1]"),
                new Task("[ea,1]"),
                new Task("[tr," + space2 + "]"),
                new Task("[so,2]"),
                new Task("[ea,1]"),
                new Task("[tr," + space3 + "]"),
                new Task("[we,5]"),
                new Task("[ea,2]"),
                new Task("[tr," + space2 + "]"),
                new Task("[no,4]"),
                new Task("[ea,3]")
        });

        assertPosition(service, cleaningDevice1, space2, 4, 1);
    }
}
